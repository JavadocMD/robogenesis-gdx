package com.javadocmd.robogenesis.work.scene

import com.javadocmd.engine2d.libgdx.scene2d.BackgroundActor
import com.javadocmd.engine2d.libgdx.scene2d.ActorContainer
import com.javadocmd.robogenesis.work.actor.Belt
import com.javadocmd.robogenesis.work.actor.JunkFactory
import com.javadocmd.robogenesis.work.actor.InventoryStrip
import com.badlogic.gdx.scenes.scene2d.Stage
import com.javadocmd.robogenesis.UI
import com.badlogic.gdx.scenes.scene2d.Group
import com.javadocmd.robogenesis.Assets
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.javadocmd.robogenesis.work.actor.SpeechBubble
import com.javadocmd.engine2d.libgdx.scene2d.BasicActor
import com.badlogic.gdx.scenes.scene2d.utils.Align

class WorkUI(vW: Float, vH: Float, stage: Stage) extends UI {
	val dialogGroup = new Group() with ActorContainer
	val gameGroup = new Group() with ActorContainer
	val backgroundGroup = new Group() with ActorContainer
	val endsceneGroup = new Group() with ActorContainer
	
	val bg = new BackgroundActor(Assets.get.background, (0,0), (vW,vH), 1f)
	
	val endscene = new BasicActor(Assets.get.endscene)
	endscene.setPosition((350,300))
	val endsceneText = new Label("ROBOGENESIS\n\nyou only get one\n\n(the end)\n\njavadocmd.com", Assets.get.partsNeededStyle)
	endsceneText.setPosition(600, 0)
	endsceneText.setAlignment(Align.center)
	val endsceneBg = new BackgroundActor(Assets.get.black, (0,0), (vW,vH), 1f)
	
	endsceneGroup.setVisible(false)
	
	val bottomHaze = new BackgroundActor(Assets.get.lime, (0,0), (vW,125), 0.35f)
	val topHaze = new BackgroundActor(Assets.get.lime, (0,725), (vW,75), 0.35f)
	val belt1 = new Belt((0,125), (vW,200))
	val belt2 = new Belt((0,325), (vW,200))
	val belt3 = new Belt((0,525), (vW,200))
	val junkFactory = new JunkFactory(vW, Array(225, 425, 625), gameGroup)
	val inventoryStrip = new InventoryStrip((575, 10))
	
	val dialogShadow = new BackgroundActor(Assets.get.black, (0,0), (vW, vH), 0.8f)
	val tutorial = new BasicActor(Assets.get.tutorial)
	tutorial.setVisible(false)
	
	val radio = new BasicActor(Assets.get.radio)
	radio.setPosition(0, vH - 500)
	val robo = new BasicActor(Assets.get.robo)
	robo.setPosition(0, vH - 500)
	
	val theirSpeech = new SpeechBubble((200,375), (800,350), (100,100))
	
	def setup(stage: Stage) = {
		Seq(dialogShadow, tutorial, theirSpeech, radio, robo) foreach { dialogGroup.addActor(_) }
		
		Seq(topHaze, bottomHaze, belt1, belt2, belt3, junkFactory, inventoryStrip) foreach { gameGroup.addActor(_) }
		
		Seq(bg) foreach { backgroundGroup.addActor(_) }
		
		Seq(endsceneBg, endscene, endsceneText) foreach { endsceneGroup.addActor(_) }
		
		Seq(backgroundGroup, gameGroup, dialogGroup, endsceneGroup) foreach { stage.addActor(_) }
	}
}