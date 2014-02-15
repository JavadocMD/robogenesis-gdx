package com.javadocmd.robogenesis.work
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.Input
import com.badlogic.gdx.Gdx
import com.javadocmd.robogenesis.RgGame

class WorkController(screen: WorkScreen) extends InputAdapter {

	override def keyDown(keycode: Int) = keycode match {
		case Input.Keys.ESCAPE => true
		//case Input.Keys.P => true
		case _ => false
	}
	
	override def keyUp(keycode: Int) = keycode match {
		case Input.Keys.ESCAPE => Gdx.app.exit(); true
		//case Input.Keys.P => screen.togglePause(); true
		case _ => false
	}
}