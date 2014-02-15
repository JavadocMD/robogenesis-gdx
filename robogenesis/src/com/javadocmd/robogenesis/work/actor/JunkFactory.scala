package com.javadocmd.robogenesis.work.actor

import com.badlogic.gdx.scenes.scene2d.Actor
import com.javadocmd.robogenesis.Assets
import scala.util.Random
import com.javadocmd.robogenesis.work.model.Part
import com.javadocmd.engine2d.libgdx.scene2d.ActorContainer
import com.javadocmd.robogenesis.work.model.Parts
import com.javadocmd.robogenesis.work.model.Model
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class JunkFactory(vW: Float, levels: Array[Float], destination: ActorContainer) extends Actor {
	
	val random = new Random()
	var frequency = Model.get.partFrequency
	var timeSinceLast = 100f
	
	val partChance = 0.6f
	val partList = Array(Parts.Treds(), Parts.Head(), Parts.Body())
	
	var pause = false
	
	override def act(delta: Float): Unit = {
		if (pause)
			return
		
		timeSinceLast += delta
		if (timeSinceLast > frequency) {
			// Create junk!
			createJunk()			
			timeSinceLast = 0f
		}
	}
	
	private def createJunk() {
		val y = levels(random.nextInt(3))
		val yJitter = random.nextInt(40) - 20
		
		var contents: Option[Part] = None
		if (random.nextFloat < partChance) {
			contents = Option(partList(random.nextInt(partList.length)))
		}
		
		val junk = new Junk(contents, Assets.get.junk1)
		junk.setPosition(vW, y + yJitter)
		destination.addActor(junk)
	}
	
	override def draw(batch: SpriteBatch, parentAlpha: Float) = {}
}