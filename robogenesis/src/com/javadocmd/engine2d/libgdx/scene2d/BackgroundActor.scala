package com.javadocmd.engine2d.libgdx.scene2d

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.javadocmd.engine2d.util.Vector2f
import com.badlogic.gdx.graphics.g2d.TextureRegion

class BackgroundActor(texture: TextureRegion, val startPosition: Vector2f, val startSize: Vector2f, startAlpha: Float) extends Actor {
	setX(startPosition.x)
	setY(startPosition.y)
	setWidth(startSize.x)
	setHeight(startSize.y)
	setColor(1f, 1f, 1f, startAlpha)
	
	val drawable: TiledDrawable = new TiledDrawable(texture)
	
	override def draw(batch: SpriteBatch, parentAlpha: Float) = {
		val c = getColor()
		batch.setColor(c.r, c.g, c.b, c.a * parentAlpha)
		drawable.draw(batch, getX(), getY(), getWidth(), getHeight())
	}
	
	override def getTouchable() = Touchable.disabled
}