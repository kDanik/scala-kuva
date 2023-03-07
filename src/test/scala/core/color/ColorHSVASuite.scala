package com.example
package core.color

import core.color.types.{ColorHSLA, ColorHSVA, ColorRGBA}

import org.scalatest.flatspec.AnyFlatSpec
import spire.math.UByte

class ColorHSVASuite extends AnyFlatSpec {
  "ColorHSVA" can "be converted to ColorRGBA" in {
    val colorHSVA: ColorHSVA = ColorHSVA(190f, 0.75f, 0.6f, UByte(50))
    val expectedColorRGBA = ColorRGBA.apply(38, 134, 153, 50)

    assert(colorHSVA.asColorRGBA == expectedColorRGBA)
  }

  "ColorHSVA" can "be converted to ColorHSLA" in {
    val colorHSVA: ColorHSVA = ColorHSVA(190f, 0.75f, 0.6f, UByte(50))
    val expectedColorHSLA = ColorHSLA(190f, 0.60f, 0.375f, UByte(50))

    assert(colorHSVA.asColorHSLA.almostEquals(expectedColorHSLA))
  }
}
