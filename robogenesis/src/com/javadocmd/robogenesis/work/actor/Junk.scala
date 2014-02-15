package com.javadocmd.robogenesis.work.actor

import com.javadocmd.engine2d.libgdx.scene2d.BasicActor
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.javadocmd.robogenesis.work.model.Part
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.javadocmd.robogenesis.Assets
import com.javadocmd.robogenesis.work.model.Model

class Junk(val contents: Option[Part], texture: TextureRegion) extends BasicActor(texture) {

	addListener(new ClickListener() {
		override def clicked(event: InputEvent, x: Float, y: Float) = {
			Model.get.select(event.getTarget().asInstanceOf[Junk])
		}
	})
	
	private var selected = false
	def setSelected(selected: Boolean) = {
		this.selected = selected
		val a = if (selected) 0.35f else 1f
		setAlpha(a)
	}
	
	override def draw(batch: SpriteBatch, parentAlpha: Float) = {
		for (c <- contents) {
			batch.setColor(1f, 1f, 1f, 1f)
			batch.draw(c.texture, getX() + 25f, getY() + 25f, 50f, 50f)
		}
		super.draw(batch, parentAlpha)
	}
	
	var conveyed = true
	override def act(delta: Float) = {
		super.act(delta)
		
		// Move the junk as if by conveyor belt, and if it moves off screen remove it.
		if (conveyed) {
			setX(getX() + Model.get.conveyorSpeed * delta)
			
			if (getX() < -100f)
				this.remove()
		}
	}
}