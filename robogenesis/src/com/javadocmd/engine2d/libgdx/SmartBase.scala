package com.javadocmd.engine2d.libgdx

import scala.collection.mutable.Queue
import com.badlogic.gdx.Game
import com.badlogic.gdx.Screen

trait SmartBase {

	val disposables = Queue[Disposable]()
	
	def register[T](item: T): T = {
		if (item.isInstanceOf[Disposable])
			disposables += item.asInstanceOf[Disposable]
		return item;
	}
	
	def registerAll[T](items: T*) = items foreach { register(_) }
}

abstract class SmartGame extends Game with SmartBase {
	
	override def dispose() = disposables foreach { _.dispose() }
}

abstract class SmartScreen extends Screen with SmartBase {
	
	override def hide() = {}
	override def pause() = {}
	override def resume() = {}
	override def dispose() = disposables foreach { _.dispose() }
}