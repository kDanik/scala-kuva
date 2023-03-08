package com.example
package core.support

import scala.annotation.targetName

/**
 * Used for comparison of two floats, with specified precision.
 * This is useful to check if 2 floats are equal to each other, ignoring imprecise way floating point arithmetic is performed
 */
implicit class FloatWithAlmostEquals(val f1: Float) extends AnyVal {
  implicit def ~=(f2: Float)(implicit precision: Precision): Boolean = (f1 - f2).abs < precision.p
}

