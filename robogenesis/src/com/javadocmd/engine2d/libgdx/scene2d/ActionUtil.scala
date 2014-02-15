package com.javadocmd.engine2d.libgdx.scene2d

import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.scenes.scene2d.actions.Actions

object ActionUtil {
	
	def apply(action: ()=>Unit): Action = new RunnableAction() {
		override def run() = action()
	}
	
	def apply[T <: Group](action: (T)=>Unit): Action = new RunnableAction() {
		override def run() = action(actor.asInstanceOf[T])
	}
	
	def waitUntilClicked(target: Actor) = new Action() {
		private var init = false
		private var done = false
		
		private var clickListener = new ClickListener() {
			override def clicked(event: InputEvent, x: Float, y: Float) = {
				done = true
			}
		}
		
		override def act(delta: Float): Boolean = {
			if (!init) {
				// First time through act, set up the click listener.
				getActor().addListener(clickListener)
				init = true
			}
			return done
		}
		
		override def setActor(actor: Actor) = {
			if (init) {
				// If we're already init'd, remove listener from old actor first.
				getActor().removeListener(clickListener)
				init = false
			}
			super.setActor(actor)
		}
		
		override def restart() = {
			done = false
		}
		
		override def reset() = {
			super.reset()
			init = false
			done = false
		}
	}
	
	def playSound(sound: Sound) = ActionUtil(() => { sound.play() })
	
	def playSound(sound: Sound, delay: Float) = Actions.sequence(
		ActionUtil(() => { sound.play() }),
		Actions.delay(delay)
	)
}