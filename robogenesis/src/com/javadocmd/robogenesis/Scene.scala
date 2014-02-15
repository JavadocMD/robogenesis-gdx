package com.javadocmd.robogenesis

// Basically a 'named' container for important actors.
trait UI

// A scene in the sense that a play has acts and scenes: instances of this 
//  class manipulate the UI and/or Model to set up a particular mode/screen.
trait Scene {
	def start()
}