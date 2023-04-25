package com.example
package core.color.types

import core.color.types.{ColorHsla, ColorHsva, ColorRgba}

import org.scalatest.flatspec.AnyFlatSpec
import spire.math.UByte

class ColorHsvaSpec extends AnyFlatSpec {
  "ColorHsva" can "be converted to ColorRgba" in {
    val colorHsva: ColorHsva = ColorHsva(190f, 0.75f, 0.6f, UByte(50))
    val expectedColorRgba = ColorRgba(38, 134, 153, 50)

    assert(colorHsva.asColorRgba == expectedColorRgba)
  }

  "ColorHsva" can "be converted to ColorHsla" in {
    val colorHsva: ColorHsva = ColorHsva(190f, 0.75f, 0.6f, UByte(50))
    val expectedColorHsla = ColorHsla(190f, 0.60f, 0.375f, UByte(50))

    assert(colorHsva.asColorHsla.almostEquals(expectedColorHsla))
  }

  "ColorHsva" should "normalize value, if created with invalid values" in {
    val colorHsva: ColorHsva = ColorHsva(380f, 1.4f, -1f, UByte(50))

    assert(colorHsva.hue == 360f)
    assert(colorHsva.saturation == 1f)
    assert(colorHsva.value == 0f)
  }

  "ColorHsva" should "normalize value, if created with copy with invalid values" in {
    val colorHsva: ColorHsva = ColorHsva(250f, 0.5f, 0.2f, UByte(50))
    val copyColorHsva = colorHsva.copy(hue = 380f, saturation = 1.4f, value = -1f)

    assert(copyColorHsva.hue == 360f)
    assert(copyColorHsva.saturation == 1f)
    assert(copyColorHsva.value == 0f)
  }
}
