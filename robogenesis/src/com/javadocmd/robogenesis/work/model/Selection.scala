package com.javadocmd.robogenesis.work.model

import com.javadocmd.robogenesis.work.actor.Junk
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.math.Interpolation
import com.javadocmd.engine2d.libgdx.scene2d.quickActorAction
import com.javadocmd.engine2d.libgdx.scene2d.ActionUtil
import com.javadocmd.robogenesis.Assets

class Selection {

	private var selected1: Option[Junk] = None
	private var selected2: Option[Junk] = None
	private var deselect: Option[Action] = None
	
	// Return true if selection was valid.
	def select(junk: Junk): Boolean = {
		// Locked if we already have two selected.
		if (selected1.isDefined && selected2.isDefined)
			return false
		
		// Can't select the same thing twice.
		for (s <- selected1) { if (selected1.get == junk) return false }
		for (s <- selected2) { if (selected2.get == junk) return false }
		
		// Now fill an empty selection spot.
		if (selected1.isEmpty) {
			selected1 = Option(junk)
			junk.setSelected(true)
		} else if (selected2.isEmpty) {
			selected2 = Option(junk)
			junk.setSelected(true)
		}
		
		// If we have two things selected, check for a match.
		for (s1 <- selected1; s2 <- selected2) {
			if (s1.contents == s2.contents && s1.contents != None) {
				// Match! :D
				s1.addAction(matchAction(true))
				s2.addAction(matchAction(false))
			} else {
				// No Match. :,(
				val noMatch = noMatchAction()
				Model.get.addActions(noMatch)
				deselect = Option(noMatch)
			}
		}
		
		return true
	}
	
	def clearSelection() = {
		for (s <- selected1) { s.setSelected(false); selected1 = None }
		for (s <- selected2) { s.setSelected(false); selected2 = None }
	}
	
	private def matchAction(main: Boolean): Action = {
		val seq = Actions.sequence()
		seq.addAction(Actions.delay(0.5f))
		seq.addAction((j: Junk) => { j.conveyed = false })
		if (main)
			seq.addAction(ActionUtil(() => { Assets.get.capture.play() }))
		seq.addAction(Actions.moveBy(0, 1000, 0.5f, Interpolation.pow4In))
		if (main)
			seq.addAction((j: Junk) => { Model.get.givePart(j.contents.get) })
		seq.addAction(Actions.removeActor())
		return seq
	}
	
	private def noMatchAction() = Actions.sequence(
		Actions.delay(Model.get.rechargeTime),
		ActionUtil(() => { clearSelection(); deselect = None })
	)
		
	def wasRemoved(actor: Actor) = actor match {
		case j: Junk => {
			for (s <- selected1) { if (j == s) selected1 = None	}
			for (s <- selected2) { if (j == s) selected2 = None }
		}
		case _ =>
	}
}