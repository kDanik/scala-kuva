package com.example
package core.color

import core.color.types.{ColorHSLA, ColorRGBA}
import core.support.{FloatWithAlmostEquals, Precision}

import org.scalatest.flatspec.AnyFlatSpec
import spire.math.UByte

class ColorHSLASpec extends AnyFlatSpec {
  "ColorHSLA" can "be converted to ColorRGBA" in {
    val colorHSLA: ColorHSLA = ColorHSLA(190f, 0.75f, 0.6f, UByte(50))
    val expectedColorRGBA = ColorRGBA.apply(77, 204, 230, 50)

    assert(colorHSLA.asColorRGBA == expectedColorRGBA)
  }

  "ColorHSLA" can "be converted to ColorHSVA" in {
    implicit val precision: Precision = Precision(0.0099f)

    val colorHSLA: ColorHSLA = ColorHSLA(190f, 0.6f, 0.375f, UByte(125))
    val colorHSVA = colorHSLA.asColorHSVA

    assert(colorHSVA.hue ~= 190f, "Conversion failed: expected hue %s, received %s" format(190f, colorHSVA.hue))
    assert(colorHSVA.value ~= 0.6f, "Conversion failed: expected lightness %s, received %s" format(0.6f, colorHSVA.value))
    assert(colorHSVA.saturation ~= 0.75f, "Conversion failed: expected saturation %s, received %s" format(0.75f, colorHSVA.saturation))
    assert(colorHSVA.alpha == UByte(125), "Conversion failed: expected alpha %s, received %s" format(UByte(125), colorHSVA.alpha))
  }
}
