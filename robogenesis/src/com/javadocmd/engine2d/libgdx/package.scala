package com.javadocmd.engine2d

package object libgdx {
	type Updatable = { def update(delta: Float) }
	type Disposable = { def dispose() }
}