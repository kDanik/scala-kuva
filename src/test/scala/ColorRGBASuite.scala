package com.example

import com.example.core.color.ColorRGBA
import org.scalatest.flatspec.AnyFlatSpec

class ColorRGBASuite extends AnyFlatSpec {
  "ColorRGBA" can "be converted to AWT Color" in {
    val colorRGBA = new ColorRGBA(100, 100, 100, 55)
    val expectedAwtColor = new java.awt.Color(100, 100, 100, 55)

    assert(colorRGBA.asAWTColor.equals(expectedAwtColor))
  }

  "ColorRGBA" can "be created from AWT Color" in {
    val awtColor = new java.awt.Color(100, 100, 100, 6)
    val expectedColorRGBA = new ColorRGBA(100, 100, 100, 6)

    assert(ColorRGBA.apply(awtColor).equals(expectedColorRGBA))
  }
}
