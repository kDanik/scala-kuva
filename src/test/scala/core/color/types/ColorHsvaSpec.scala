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
}
