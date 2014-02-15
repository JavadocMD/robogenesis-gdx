package com.javadocmd.robogenesis.work.model

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.javadocmd.robogenesis.Assets

class Part(val texture: TextureRegion)

object Parts {
	case class Treds extends Part(Assets.get.treds)
	case class Head  extends Part(Assets.get.head)
	case class Body  extends Part(Assets.get.body)
}