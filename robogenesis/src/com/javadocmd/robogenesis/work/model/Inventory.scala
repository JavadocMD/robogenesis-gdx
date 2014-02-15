package com.javadocmd.robogenesis.work.model

import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.math.Interpolation
import com.javadocmd.robogenesis.Assets
import com.javadocmd.robogenesis.work.actor.PartActor
import com.javadocmd.engine2d.libgdx.scene2d.ActionUtil

class Inventory {

	val parts = ListBuffer[Part]()
	val missing = ListBuffer[Part](Parts.Head(), Parts.Body(), Parts.Treds(), Parts.Treds())
	
	def givePart(part: Part) = {
		Model.get.ui.inventoryStrip.addAction(newPartAction(part))
	}
	
	private val animationSpeed = 0.4f
	private val stripSpacing = 125f
	
	private def newPartAction(part: Part) = {
		val strip = Model.get.ui.inventoryStrip
		
		// Temporary part to animate it coming onto screen.
		val tempPartActor = new PartActor(part)
		tempPartActor.setPosition(strip.getX(), strip.getY() - stripSpacing)
		Model.get.addActors(tempPartActor)
		
		// Animation for temporary part.
		val subSeq = Actions.sequence(
			Actions.moveTo(strip.getX(), strip.getY(), animationSpeed, Interpolation.bounceOut),
			ActionUtil(() => {
				parts.append(part)
				
				val i = missing.indexOf(part)
				if (i > -1)
					missing.remove(i)
				
				strip.setX(strip.getX() - stripSpacing)
				strip.update()
			}),
			Actions.removeActor()
		)
		
		// Animation to run on the inventory strip.
		Actions.sequence(
			Actions.moveBy(stripSpacing, 0f, animationSpeed, Interpolation.bounceOut),
			Actions.addAction(subSeq, tempPartActor)
		)
	}
}