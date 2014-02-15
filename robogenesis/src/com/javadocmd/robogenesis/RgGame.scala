package com.javadocmd.robogenesis

import com.javadocmd.engine2d.libgdx.SmartGame
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.javadocmd.robogenesis.work.WorkScreen

class RgGame extends SmartGame {

	var workScreen: WorkScreen = _
	var batch: Option[SpriteBatch] = None
	
	override def create() = {
		workScreen = new WorkScreen(this)
		batch = Option(new SpriteBatch())
		Assets.load()
		
		registerAll(batch.get, Assets.get)
		
		startGame()
	}
	
	def startGame() = {
		workScreen.newGame()
		setScreen(workScreen)
	}
}