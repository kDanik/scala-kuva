package com.example
package core.support

import core.support.math.interpolation.Interpolation

import org.scalatest.flatspec.AnyFlatSpec

import scala.collection.immutable.ArraySeq

class InterpolationSpec extends AnyFlatSpec {
  "linear interpolation" should "produce correct results" in {
    assert(Interpolation.linearInterpolation(3, 1, 10, 6, 20) == 14)
  }

  "bilinear interpolation" should "produce correct results" in {
    // precision is not really needed here, but most examples of bilinear interpolation do some sort of rounding up
    implicit val precision: Precision = Precision(0.001f)

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

  "cubic interpolation (alternative method)" should "produce correct results" in {
    val positions = ArraySeq(1f, 2f, 3f, 4f)
    val values = ArraySeq(10f, 10f, 20f, 20f)
    assert(
      Interpolation
        .cubicInterpolationAlternative(2.25f, values, positions) == 12.34375f)
  }

  "cubic interpolation (OpenCV)" should "produce correct results" in {
    val values = ArraySeq(10f, 10f, 20f, 20f)

    assert(Interpolation.cubicInterpolationOpenCV(0.25f, values) == 12.265625f)
  }

  "bicubic" should "produce correct results" in {
    val values = ArraySeq(
      ArraySeq(10f, 10f, 20f, 20f),
      ArraySeq(10f, 10f, 20f, 20f),
      ArraySeq(10f, 10f, 20f, 20f),
      ArraySeq(30f, 30f, 40f, 40f))

    assert(Interpolation.bicubicInterpolation(0.25f, 0.75f, values) == 10.15625)
  }
}
