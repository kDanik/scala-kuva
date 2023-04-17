package com.example
package core.support

import core.support.math.interpolation.Interpolation

import org.scalatest.flatspec.AnyFlatSpec

class InterpolationSpec extends AnyFlatSpec {
  "linear interpolation" should "produce correct results" in {
    assert(Interpolation.linearInterpolation(3, 1, 10, 6, 20) == 14)
  }

  "bilinear interpolation" should "produce correct results" in {
    // precision is not really needed here, but most examples of bilinear interpolation do some sort of rounding up
    implicit val precision = Precision(0.001f)

    val x1 = 14
    val x2 = 15
    val y1 = 20
    val y2 = 21
    val q11 = 91
    val q21 = 210
    val q12 = 162
    val q22 = 95

    assert(
      Interpolation.bilinearInterpolation(14.5f, 20.2f, x1, y1, x2, y2, q11, q12, q21, q22) ~=
        146.1f)
  }
}
