package com.example
package core.color

import core.color.types.{ColorHSLA, ColorHSVA, ColorRGBA}

import org.scalatest.flatspec.AnyFlatSpec

class ColorHSVASuite extends AnyFlatSpec {
  "ColorHSVA" can "be converted to ColorRGBA" in {
    val colorHSVA: ColorHSVA = ColorHSVA(190f, 0.75f, 0.6f, 0.2f)
    val expectedColorRGBA = ColorRGBA.apply(38, 134, 153, 51)

    assert(colorHSVA.asColorRGBA == expectedColorRGBA)
  }
}
