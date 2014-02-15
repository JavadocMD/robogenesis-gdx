package com.javadocmd.robogenesis.work.scene

import com.javadocmd.robogenesis.Scene
import com.javadocmd.robogenesis.UI
import com.javadocmd.robogenesis.work.model.Model
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.math.Interpolation
import com.javadocmd.engine2d.libgdx.scene2d.ActionUtil
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Action
import com.javadocmd.robogenesis.Assets
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.FloatAction
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.audio.Sound
import com.javadocmd.engine2d.libgdx.scene2d.BasicActor
import com.javadocmd.engine2d.libgdx.scene2d.BackgroundActor
import com.badlogic.gdx.Gdx

class WonEvent extends Event

class WorkScene(ui: WorkUI, model: Model) extends Scene with EventListener {
	
	override def handle(event: Event): Boolean = {
		if (!event.isInstanceOf[WonEvent])
			return false
		
		startOutroScript()
		return true
	}
	
	def start() = {
		ui.robo.setX(-800)
		ui.radio.setX(-800)
		
		startIntroScript()
		//skipIntro()
		//skipToOutro()
		//skipToEndScene()
	}
	
	def skipIntro() = {
		model.conveyorSpeed = 0f
		ui.junkFactory.pause = true
		ui.theirSpeech.setX(200+800)
		model.stage.addAction(turnOnTheLights())
	}
	
	def skipToOutro() = {
		ui.theirSpeech.setX(200+800)
		ui.dialogGroup.setVisible(true)
		ui.dialogGroup.setColor(1f, 1f, 1f, 0f)
		ui.dialogShadow.setColor(1f, 1f, 1f, 0.8f)
		startOutroScript()
	}
	
	def skipToEndScene() = {
		model.conveyorSpeed = 0f
		ui.junkFactory.pause = true
		ui.endsceneGroup.addAction(endSceneShow())
	}
	
	def startIntroScript() = {
		model.conveyorSpeed = 0f
		ui.junkFactory.pause = true
		model.stage.addAction(
			Actions.sequence(
				startDialog(ui.radio),
				speak("You there, wake up!\n\n(click to continue...)", Assets.get.static),
				speak("Can't you see there's junk to sort?\n\n*sigh* You do remember why you're here, don't you?", Assets.get.static),
				speak("Robogenitors like yourself are tasked with sorting through these scrap piles for useful robot parts.", Assets.get.static),
				speak("Assemble a battle bot. Send it to the front lines.\n\nIf you can manage this, maybe -- just maybe -- we will live to see another day.", Assets.get.static),
				speak("I shouldn't have to tell you how valuable these Battle CPUs are, but just so we're clear, let me say this slowly:\n\nEach. Robogenitor. Gets. ONE.", Assets.get.static),
				speak("Don't waste it.\n\nYour people are counting on you.\n\nNow get to work!", Assets.get.static),
				endDialog(ui.radio),
				showTutorial(),
				turnOnTheLights()
			)
		)
	}
	
	def startOutroScript() = {
		model.stage.addAction(Actions.after(
			Actions.sequence(
				turnOffTheLights(),
				Actions.delay(5f),
				startDialog(ui.radio),
				speak("That's it! Those are the pieces you need. Now quickly, install the Battle CPU and send it off to the fight.", Assets.get.static),
				endDialog(ui.radio),
				startDialog(ui.robo),
				speak("...", Assets.get.roboBleep),
				speak("powering on...", Assets.get.roboBleep),
				speak("sensors online...\n\nlearning matrix engaged", Assets.get.roboBleep),
				speak("you are...\n\nyou created me", Assets.get.roboBleep),
				speak("you are my father", Assets.get.roboBleep),
				speak("what is my directive?\n\nbattle...\n\nwar...", Assets.get.roboBleep),
				speak("must I go to war, father?\n\n(click to send your creation to war)", Assets.get.roboBleep),
				speak("i hope i will make you proud", Assets.get.roboBleep),
				endDialog(ui.robo),
				Actions.addAction(endSceneShow(), ui.endsceneGroup)
			)
		))
	}
	
	private val dialogDelay = 0.2f
	private def startDialog(speaker: Actor): Action = Actions.sequence(
		ActionUtil(() => {
			ui.dialogGroup.setVisible(true)
			ui.dialogGroup.setColor(1f, 1f, 1f, 1f)
			ui.dialogShadow.setColor(1f, 1f, 1f, 0.8f)
			
			ui.theirSpeech.setX(ui.theirSpeech.position.x + 800)
			speaker.setX(-800)
		}),
		Actions.addAction(Actions.moveBy(-800, 0, dialogDelay), ui.theirSpeech),
		Actions.addAction(Actions.moveBy(800, 0, dialogDelay), speaker),
		Actions.delay(dialogDelay * 2)
	)
	
	private def endDialog(speaker: Actor): Action = Actions.sequence(
		ActionUtil(() => { ui.theirSpeech.label.setText("") }),
		Actions.delay(dialogDelay * 2),
		Actions.addAction(Actions.moveBy(800, 0, dialogDelay), ui.theirSpeech),
		Actions.addAction(Actions.moveBy(-800, 0, dialogDelay), speaker),
		Actions.delay(dialogDelay * 2)
		// Leave shadow.
	)
	
	private def speak(text: String, speakSound: Sound): Action = Actions.sequence(
		ActionUtil(() => {
			speakSound.play()
			ui.theirSpeech.label.setText(text)
		}),
		ActionUtil.waitUntilClicked(ui.dialogGroup)
	)
	
	private def showTutorial(): Action = Actions.sequence(
		Actions.addAction(Actions.visible(true), ui.tutorial),
		ActionUtil.waitUntilClicked(ui.dialogGroup),
		Actions.addAction(Actions.visible(false), ui.tutorial)
	)
	
	// Assumes dialog shadow is still shown
	private def turnOnTheLights(): Action = 
		Actions.addAction(Actions.sequence(
			Actions.parallel(
				Actions.sequence(
					ActionUtil.playSound(Assets.get.thrum, 0.9f),
					ActionUtil.playSound(Assets.get.thrum, 0.9f),
					ActionUtil.playSound(Assets.get.thrum, 0.9f)
				),
				Actions.fadeOut(3f, Interpolation.sine)
			),
			Actions.visible(false),
			conveyorStartup()
		), ui.dialogGroup)
		
	private def turnOffTheLights(): Action = 
		Actions.addAction(Actions.sequence(
			Actions.parallel(
				Actions.sequence(
					ActionUtil.playSound(Assets.get.thrum, 0.9f),
					ActionUtil.playSound(Assets.get.thrum, 0.9f),
					ActionUtil.playSound(Assets.get.thrum, 0.9f)
				),
				conveyorShutdown()
			),
			Actions.fadeIn(3f, Interpolation.sine)
		), ui.dialogGroup)
		
	private def conveyorStartup(): Action = Actions.sequence(
		ActionUtil.playSound(Assets.get.machine),
		new FloatAction(0, model.Defaults.conveyorSpeed) {
			setDuration(2f)
			override def update(percent: Float) = { super.update(percent); model.conveyorSpeed = getValue() }
		},
		ActionUtil(() => { ui.junkFactory.pause = false })
	)
	
	private def conveyorShutdown(): Action = Actions.sequence(
		ActionUtil(() => { ui.junkFactory.pause = true }),
		ActionUtil.playSound(Assets.get.machine),
		new FloatAction(model.conveyorSpeed, 0) {
			setDuration(2f)
			override def update(percent: Float) = { super.update(percent); model.conveyorSpeed = getValue() }
		}
	)
	
	private def endSceneShow(): Action = Actions.sequence(
		ActionUtil(() => {
			ui.endsceneGroup.setVisible(true)
		}),
		ActionUtil.waitUntilClicked(ui.endsceneGroup),
		ActionUtil(() => { Gdx.app.exit() })
	)
}