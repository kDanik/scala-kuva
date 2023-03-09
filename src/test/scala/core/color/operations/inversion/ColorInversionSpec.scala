package com.example
package core.color.operations.inversion

import core.color.operations.grayscale.{GrayscaleColorConversion, GrayscaleConversionAlgorithm}
import core.color.operations.inversion.ColorInversion
import core.color.types.{ColorHsva, ColorRgba}

import org.scalatest.flatspec.AnyFlatSpec
import spire.math.UByte

class ColorInversionSpec extends AnyFlatSpec {
  "Color inversion" can "should work properly for ColorRgba (excluding alpha)" in {
    val initialColor = ColorRgba.apply(199, 130, 30, 110)
    val expectedInvertedColor = ColorRgba.apply(56, 125, 225, 110)

    assert(ColorInversion.invertColor(initialColor) == expectedInvertedColor)
  }

  "Color inversion" can "should work properly for ColorRgba (including alpha)" in {
    val initialColor = ColorRgba.apply(199, 130, 30, 110)
    val expectedInvertedColor = ColorRgba.apply(56, 125, 225, 145)

    assert(ColorInversion.invertColor(initialColor, true) == expectedInvertedColor)
  }

  "Color inversion" can "should work properly for not ColorRgba format (for example ColorHsva)" in {
    val initialColorHSVA = ColorHsva(300f, 0.39f, 0.7f, UByte(110))
    val expectedInvertedColor = ColorRgba.apply(76, 146, 76, 110)

    assert(ColorInversion.invertColor(initialColorHSVA) == expectedInvertedColor)
  }
}
