package com.example
package core.image

final case class Position(x: Float, y: Float) {
  lazy val xInt: Int = x.intValue
  lazy val yInt: Int = y.intValue

  def isNotNegative: Boolean = {
    x >= 0 && y >= 0
  }
}
