package com.javadocmd.engine2d.libgdx.scene2d

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.javadocmd.engine2d.util.Vector2f
import com.javadocmd.robogenesis.work.model.Model
import com.badlogic.gdx.scenes.scene2d.Group

class BasicActor(val texture: TextureRegion) extends Actor {
	setSize(texture.getRegionWidth(), texture.getRegionHeight())
	
	def setPosition(position: Vector2f): Unit = setPosition(position.x, position.y)
	def getPosition() = { Vector2f(getX(), getY()) }
	def setAlpha(alpha: Float) = { val c = getColor(); setColor(c.r, c.g, c.b, alpha) }
	
	override def draw(batch: SpriteBatch, parentAlpha: Float) = {
		val c = getColor()
		batch.setColor(c.r, c.g, c.b, c.a * parentAlpha)
		batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation())
	}
	
	override def remove() = {
		Model.get.wasRemoved(this)
		super.remove()
	}
}

abstract class BasicGroup extends Group {
	def setPosition(position: Vector2f): Unit = setPosition(position.x, position.y)
	def getPosition() = { Vector2f(getX(), getY()) }
	def setAlpha(alpha: Float) = { val c = getColor(); setColor(c.r, c.g, c.b, alpha) }
}