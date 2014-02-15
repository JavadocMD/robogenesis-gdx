package com.javadocmd.robogenesis.work.actor

import com.javadocmd.engine2d.libgdx.scene2d.BackgroundActor
import com.javadocmd.robogenesis.Assets
import com.javadocmd.engine2d.util.Vector2f
import com.javadocmd.robogenesis.work.model.Model
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import scala.collection.mutable.Queue
import com.badlogic.gdx.scenes.scene2d.Actor
import com.javadocmd.engine2d.util.tupleToVector2f

class Belt(position: Vector2f, size: Vector2f) extends Actor {
	setX(position.x)
	setY(position.y)
	setWidth(size.x)
	setHeight(size.y)
	
	val gears = new BackgroundActor(Assets.get.gears, position, size, 1f)
	val tiles: Queue[BackgroundActor] = new Queue()
	tiles += new BackgroundActor(Assets.get.belt, position, size, 1f)
	tiles += new BackgroundActor(Assets.get.belt, position + (size.x,0), size, 1f)
	
	override def draw(batch: SpriteBatch, parentAlpha: Float) = {
		gears.draw(batch, parentAlpha)
		tiles foreach { _.draw(batch, parentAlpha) }
	}
	
	override def act(delta: Float) = {
		super.act(delta)
		
		for (t <- tiles) { t.setX(t.getX() + Model.get.conveyorSpeed * delta) }
		
		if (tiles.head.getX() <= -size.x) {
			tiles.dequeue
			val newPos = position + ((tiles.last.getX() + size.x),0)
			tiles += new BackgroundActor(Assets.get.belt, newPos, size, 1f)
		}
	}
	
	override def getTouchable() = Touchable.disabled
}