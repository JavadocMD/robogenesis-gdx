package com.javadocmd.robogenesis.work

import com.javadocmd.engine2d.libgdx.SmartScreen
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL10
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.InputMultiplexer
import com.javadocmd.engine2d.libgdx.scene2d.BackgroundActor
import com.javadocmd.robogenesis.work.actor.JunkFactory
import com.javadocmd.robogenesis.Assets
import com.javadocmd.robogenesis.work.actor.Belt
import com.javadocmd.robogenesis.work.actor.InventoryStrip
import com.badlogic.gdx.graphics.FPSLogger
import com.badlogic.gdx.scenes.scene2d.Group
import com.javadocmd.engine2d.libgdx.scene2d.ActorContainer
import com.javadocmd.engine2d.util.tupleToVector2f
import com.javadocmd.robogenesis.RgGame
import com.javadocmd.robogenesis.work.model.Model
import com.javadocmd.robogenesis.UI
import com.javadocmd.robogenesis.work.scene.WorkScene
import com.javadocmd.robogenesis.work.scene.WorkUI

class WorkScreen(val app: RgGame) extends SmartScreen {

	var vW = Gdx.graphics.getWidth().toFloat
	var vH = Gdx.graphics.getHeight().toFloat
	
	var stage: Stage = register(new Stage(vW, vH, true))
	val fpsLog = new FPSLogger()
		
	def newGame() = {
		val ui = new WorkUI(vW, vH, stage)
		ui.setup(stage)
		val model = Model.load(stage, ui)
		val scene = new WorkScene(ui, Model.get)
		stage.addListener(scene)
		scene.start()
	}

	override def render(delta: Float) = {
		Gdx.gl.glClearColor(1, 1, 1, 1)
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT)
		
		if (!paused) stage.act(delta)
		stage.draw()
		//fpsLog.log()
	}

	var paused = false
	def togglePause() = { paused = !paused }
	
	override def resize(width: Int, height: Int) = { }
	override def show() = { Gdx.input.setInputProcessor(new InputMultiplexer(stage, new WorkController(this))) }
}