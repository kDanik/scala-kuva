package com.example
package core.color.types

import core.color.types.{ColorHsla, ColorRgba}
import core.support.{FloatWithAlmostEquals, Precision}

import org.scalatest.flatspec.AnyFlatSpec
import spire.math.UByte

class ColorHslaSpec extends AnyFlatSpec {
  "ColorHsla" can "be converted to ColorRgba" in {
    val colorHsla: ColorHsla = ColorHsla(190f, 0.75f, 0.6f, UByte(50))
    val expectedColorRgba = ColorRgba(77, 204, 230, 50)

    assert(colorHsla.asColorRgba == expectedColorRgba)
  }

  "ColorHsla" can "be converted to ColorHsva" in {
    implicit val precision: Precision = Precision(0.0099f)

    val colorHsla: ColorHsla = ColorHsla(190f, 0.6f, 0.375f, UByte(125))
    val colorHsva = colorHsla.asColorHsva

    assert(
      colorHsva.hue ~= 190f,
      "Conversion failed: expected hue %s, received %s" format (190f, colorHsva.hue))
    assert(
      colorHsva.value ~= 0.6f,
      "Conversion failed: expected lightness %s, received %s" format (0.6f, colorHsva.value))
    assert(
      colorHsva.saturation ~= 0.75f,
      "Conversion failed: expected saturation %s, received %s" format (0.75f, colorHsva.saturation))
    assert(
      colorHsva.alpha == UByte(125),
      "Conversion failed: expected alpha %s, received %s" format (UByte(125), colorHsva.alpha))
  }

  "ColorHsla" should "normalize value, if created with invalid values" in {
    val colorHsla: ColorHsla = ColorHsla(380f, 1.4f, -1f, UByte(50))

    assert(colorHsla.hue == 360f)
    assert(colorHsla.saturation == 1f)
    assert(colorHsla.lightness == 0f)
  }

  "ColorHsla" should "normalize value, if created with copy with invalid values" in {
    val colorHsla: ColorHsla = ColorHsla(250f, 0.5f, 0.2f, UByte(50))
    val copyColorHsla = colorHsla.copy(hue = 380f, saturation = 1.4f, lightness = -1f)

    assert(copyColorHsla.hue == 360f)
    assert(copyColorHsla.saturation == 1f)
    assert(copyColorHsla.lightness == 0f)
  }
}
