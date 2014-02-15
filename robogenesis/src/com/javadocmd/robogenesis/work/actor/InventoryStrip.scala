package com.javadocmd.robogenesis.work.actor
import com.javadocmd.robogenesis.Assets
import com.javadocmd.engine2d.util.Vector2f
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.javadocmd.robogenesis.work.model.Model
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Actor
import scala.collection.JavaConversions._
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.Align
import com.javadocmd.robogenesis.work.scene.WonEvent

class InventoryStrip(position: Vector2f) extends Group {
	setPosition(position.x, position.y)
	
	val maxParts = 5
	val spacing = 125f
	
	val partsNeededLabel = new Label("", Assets.get.partsNeededStyle)
	partsNeededLabel.setPosition(25, 25)
	partsNeededLabel.setWidth(500)
	partsNeededLabel.setHeight(75)
	partsNeededLabel.setAlignment(Align.top)
	partsNeededLabel.setWrap(true)
	
	setPartsNeededText()
	private def setPartsNeededText() = {
		val m = Model.get.inventory.missing
		val text = "Parts needed: " + m.map(p => p.toString().dropRight(2)).mkString(", ")
		partsNeededLabel.setText(text)
		
		// Oh man this class is so full of hacks right now...
		if (m.length == 0)
			this.fire(new WonEvent())
	}
	
	def update() {
		var last: Actor = null
		var count = 0
		for (a <- getChildren().iterator()) {
			a.setX(a.getX() + spacing)
			last = a
			count += 1
		}
		if (count == maxParts)
			last.remove()
		
		val p = Model.get.inventory.parts.last
		addActorAt(0, new PartActor(p))
		
		setPartsNeededText()
	}
	
	override def draw(batch: SpriteBatch, parentAlpha: Float) = {
		partsNeededLabel.draw(batch, parentAlpha)
		for (i <- 0 until maxParts) {
			batch.draw(Assets.get.partBox, position.x - 5f + spacing * i, position.y - 5f)
		}
		super.draw(batch, parentAlpha)
	}
}