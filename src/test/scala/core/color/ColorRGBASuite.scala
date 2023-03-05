package com.example
package core.color


import core.color.types.ColorRGBA
import core.support.{FloatWithAlmostEquals, Precision}

import org.scalatest.flatspec.AnyFlatSpec
import spire.math.UByte

class ColorRGBASuite extends AnyFlatSpec {
  "ColorRGBA" can "be converted to AWT Color" in {
    val colorRGBA = ColorRGBA.apply(100, 100, 100, 55)
    val expectedAwtColor = java.awt.Color(100, 100, 100, 55)

    assert(colorRGBA.asAWTColor == expectedAwtColor)
  }

  "ColorRGBA" can "be created from AWT Color" in {
    val awtColor = java.awt.Color(100, 100, 100, 6)
    val expectedColorRGBA = ColorRGBA.apply(100, 100, 100, 6)

    assert(ColorRGBA.apply(awtColor) == expectedColorRGBA)
  }

  "ColorRGBA" can "be created with color values higher than 255, but will cause overflow" in {
    val colorRGBA = ColorRGBA.apply(259, 255, 255, 255)

    assert(colorRGBA.red.intValue === 3)
  }

  "ColorRGBA" can "be converted to RGB Int, that represents its value" in {
    val colorRGBA = ColorRGBA.apply(200, 150, 10, 155)

    assert(colorRGBA.RGBInt === 13145610)
  }

  "ColorRGBA" can "be converted to RGBA Int, that represents its value" in {
    val colorRGBA = ColorRGBA.apply(200, 150, 10, 155)

    assert(colorRGBA.RGBAInt === -1681353206)
  }

  "ColorRGBA" can "be converted to ColorHSLA" in {
    implicit val precision: Precision = Precision(0.0099f)

    val colorRGBA = ColorRGBA.apply(200, 150, 10, 125)
    val colorHSLA = colorRGBA.asColorHSLA

    assert(colorHSLA.hue ~= 44.21f, "Conversion failed: expected hue %s, received %s" format(44.21f, colorHSLA.hue))
    assert(colorHSLA.lightness ~= 0.411f, "Conversion failed: expected lightness %s, received %s" format(0.411f, colorHSLA.lightness))
    assert(colorHSLA.saturation ~= 0.9f, "Conversion failed: expected saturation %s, received %s" format(0.9f, colorHSLA.saturation))
    assert(colorHSLA.alpha ~= 0.5f, "Conversion failed: expected alpha %s, received %s" format(0.5f, colorHSLA.alpha))
  }


  "ColorRGBA" can "be converted to ColorHSLA and back to ColorRGBA, keeping same values" in {
    val colorRGBA = ColorRGBA.apply(183, 159, 10, 125)
    val colorHSLA = colorRGBA.asColorHSLA

    assert(colorRGBA == colorHSLA.asColorRGBA)
  }
}
