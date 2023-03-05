package com.example
package core.color

import core.color.types.{ColorHSLA, ColorRGBA}

import org.scalatest.flatspec.AnyFlatSpec

class ColorHSLASuite extends AnyFlatSpec {
  // TODO all color conversion functions could be separate test suite
  "ColorHSLA" can "be converted to ColorRGBA" in {
    val colorHSLA: ColorHSLA = ColorHSLA(190f, 0.75f, 0.6f, 0.2f)
    val expectedColorRGBA = ColorRGBA.apply(77, 204, 230, 51)

    assert(colorHSLA.asColorRGBA == expectedColorRGBA)
  }
}
