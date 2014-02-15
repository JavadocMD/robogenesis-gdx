package com.javadocmd.engine2d.libgdx

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.javadocmd.engine2d.component.Position
import com.javadocmd.engine2d.component.Volume
import com.javadocmd.engine2d.entity.Entity
import com.javadocmd.engine2d.system.System
import com.javadocmd.engine2d.component.Shape
import com.javadocmd.engine2d.util.Vector2f
import com.badlogic.gdx.graphics.g2d.TextureRegion

object LibGdxRender {
	
	type LibGdxRenderable = {
		def shape: Shape
		def position: Vector2f
		def rotation: Float
		def texture: TextureRegion
	}
	
	def process(batch: SpriteBatch, entities: List[Entity]) = {
		batch.setColor(1, 1, 1, 1)
		entities filterNot(_.dead) foreach {
			case e: LibGdxRenderable => 
				val box = e.shape.getBoundingBox()
				batch.draw(e.texture, e.position.x - box.x / 2, e.position.y - box.y / 2, box.x / 2, box.y / 2, box.x, box.y, 1, 1, e.rotation)
		}
	}
}

