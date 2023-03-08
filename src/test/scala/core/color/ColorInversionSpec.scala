package com.example
package core.color

import core.color.operations.grayscale.{GrayscaleColorConversion, GrayscaleConversionAlgorithm}
import core.color.operations.inversion.ColorInversion
import core.color.types.{ColorHSVA, ColorRGBA}

import org.scalatest.flatspec.AnyFlatSpec
import spire.math.UByte

class ColorInversionSpec extends AnyFlatSpec {
  "Color inversion" can "should work properly for ColorRGBA (excluding alpha)" in {
    val initialColor = ColorRGBA.apply(199, 130, 30, 110)
    val expectedInvertedColor = ColorRGBA.apply(56, 125, 225, 110)

    assert(ColorInversion.invertColor(initialColor) == expectedInvertedColor)
  }

  "Color inversion" can "should work properly for ColorRGBA (including alpha)" in {
    val initialColor = ColorRGBA.apply(199, 130, 30, 110)
    val expectedInvertedColor = ColorRGBA.apply(56, 125, 225, 145)

    assert(ColorInversion.invertColor(initialColor, true) == expectedInvertedColor)
  }

  "Color inversion" can "should work properly for not ColorRGBA format (for example ColorHSVA)" in {
    val initialColorHSVA = ColorHSVA(300f, 0.39f, 0.7f, UByte(110))
    val expectedInvertedColor = ColorRGBA.apply(76, 146, 76, 110)

    assert(ColorInversion.invertColor(initialColorHSVA) == expectedInvertedColor)
  }
}
