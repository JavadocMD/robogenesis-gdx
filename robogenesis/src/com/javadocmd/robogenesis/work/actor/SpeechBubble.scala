package com.javadocmd.robogenesis.work.actor

import com.javadocmd.engine2d.util.Vector2f
import com.badlogic.gdx.scenes.scene2d.Group
import com.javadocmd.engine2d.libgdx.scene2d.BackgroundActor
import com.javadocmd.robogenesis.Assets
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.javadocmd.engine2d.libgdx.scene2d.BasicGroup

class SpeechBubble(val position: Vector2f, val size: Vector2f, padding: Vector2f) extends BasicGroup {
	
	private val innerPos = position + padding
	private val innerSize = size - (padding * 2)
	
	val bg = new BackgroundActor(Assets.get.lime, position, size, 0.35f)
	val label = new Label("", Assets.get.speechBubbleStyle)
	label.setPosition(innerPos.x, innerPos.y)
	label.setSize(innerSize.x, innerSize.y)
	label.setWrap(true)
	
	this.addActor(bg)
	this.addActor(label)
}