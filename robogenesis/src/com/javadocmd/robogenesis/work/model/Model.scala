package com.javadocmd.robogenesis.work.model
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Action
import com.javadocmd.robogenesis.Assets
import scala.util.Random
import com.javadocmd.robogenesis.work.actor.Junk
import com.javadocmd.robogenesis.work.scene.WorkUI

object Model {
	private var value: Model = DummyModel
	def get: Model = value;
	
	def load(stage: Stage, ui: WorkUI) = { value = new Model(stage, ui); value }
}

class Model(val stage: Stage, val ui: WorkUI) {
	val random = new Random()
	
	object Defaults {
		val conveyorSpeed = -100f
		val rechargeTime = 1f
		val partFrequency = 1f
	}
	
	var conveyorSpeed = Defaults.conveyorSpeed
	var rechargeTime = Defaults.rechargeTime
	var partFrequency = Defaults.partFrequency
	
	val inventory: Inventory = new Inventory()
	def givePart(part: Part): Unit = inventory.givePart(part)
	
	val selection = new Selection()
	def select(junk: Junk): Unit = {
		if (selection.select(junk)) {
			val soundId = Assets.get.scanner.play()
			val pitchJitter = 1.05f - random.nextFloat() / 10f
			Assets.get.scanner.setPitch(soundId, pitchJitter)
		}
	}
	
	def addActors(actors: Actor*) = actors foreach { stage.addActor(_) }
	def addActions(actions: Action*) = actions foreach { stage.addAction(_) }
	
	def wasRemoved(actor: Actor) = {
		selection.wasRemoved(actor)
	}
}

// Helps make sure LibGDX doesn't do anything stupid while we're still initializing.
private object DummyModel extends Model(null, null) {
	override def wasRemoved(actor: Actor) = {}
}