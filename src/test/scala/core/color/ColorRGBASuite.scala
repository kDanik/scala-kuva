package com.example
package core.color

import core.color.ColorRGBA

import org.scalatest.flatspec.AnyFlatSpec
import spire.math.UByte

class ColorRGBASuite extends AnyFlatSpec {
  "ColorRGBA" can "be converted to AWT Color" in {
    val colorRGBA = ColorRGBA.apply(100, 100, 100, 55)
    val expectedAwtColor = java.awt.Color(100, 100, 100, 55)

    assert(colorRGBA.asAWTColor.equals(expectedAwtColor))
  }

  "ColorRGBA" can "be created from AWT Color" in {
    val awtColor = java.awt.Color(100, 100, 100, 6)
    val expectedColorRGBA = ColorRGBA.apply(100, 100, 100, 6)

    assert(ColorRGBA.apply(awtColor).equals(expectedColorRGBA))
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
}
