package com.javadocmd.robogenesis

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle

object Assets {
	private var value: Assets = _
	def get: Assets = value;
	
	def load() = { value = new Assets() }
}

class Assets() {
	private val atlas = new TextureAtlas("data/robogenesis.pack")
	
	val background = atlas.findRegion("background-stripe")
	val endscene = atlas.findRegion("endscene")
	val lime = atlas.findRegion("lime")
	val black = atlas.findRegion("black")
	val white = atlas.findRegion("white")
	
	val tutorial = atlas.findRegion("tutorial")
	val radio = atlas.findRegion("radio")
	val robo = atlas.findRegion("robo")
	val partBox = atlas.findRegion("partbox")
	val junk1 = atlas.findRegion("junk-pile-1")
	val belt = atlas.findRegion("belt")
	val gears = atlas.findRegion("gears")
	
	val treds = atlas.findRegion("treds")
	val body = atlas.findRegion("body")
	val head = atlas.findRegion("head")
	
	val scanner = Gdx.audio.newSound(Gdx.files.internal("data/scanner.wav"))
	val capture = Gdx.audio.newSound(Gdx.files.internal("data/capture.wav"))
	val static = Gdx.audio.newSound(Gdx.files.internal("data/static.wav"))
	val thrum = Gdx.audio.newSound(Gdx.files.internal("data/thrum.wav"))
	val machine = Gdx.audio.newSound(Gdx.files.internal("data/machine.wav"))
	val roboBleep = Gdx.audio.newSound(Gdx.files.internal("data/robo.wav"))
	
	val bpdDiamond = new BitmapFont(Gdx.files.internal("data/bpDotsDiamond.fnt"), atlas.findRegion("bpDotsDiamond"))
	val bpdMinus = new BitmapFont(Gdx.files.internal("data/bpDotsMinus.fnt"), atlas.findRegion("bpDotsMinus"))
	val bpdVertical = new BitmapFont(Gdx.files.internal("data/bpDotsVertical.fnt"), atlas.findRegion("bpDotsVertical"))
	
	val speechBubbleStyle = new LabelStyle(bpdMinus, new Color(1f, 1f, 1f, 1f))
	val partsNeededStyle = new LabelStyle(bpdVertical, new Color(1f, 1f, 1f, 1f))
	
	def dispose() = {
		atlas.dispose()
		scanner.dispose()
		capture.dispose()
		static.dispose()
		thrum.dispose()
		machine.dispose()
		roboBleep.dispose()
	}
}