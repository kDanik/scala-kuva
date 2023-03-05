package com.example
package core.support

import scala.annotation.targetName

implicit class FloatWithAlmostEquals(val f1: Float) extends AnyVal {
  implicit def ~=(f2: Float)(implicit precision: Precision): Boolean = (f1 - f2).abs < precision.p
}

