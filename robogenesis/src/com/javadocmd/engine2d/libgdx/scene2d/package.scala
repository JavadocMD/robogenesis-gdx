package com.javadocmd.engine2d.libgdx

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import com.badlogic.gdx.scenes.scene2d.Actor

package object scene2d {
	
	implicit def quickActorAction[T <: Actor](action: (T)=>Unit): Action = {
		new RunnableAction() {
			override def run() = action(actor.asInstanceOf[T])
		}
	}
}