package com.example
package core.color.types

import core.color.types.ColorRgba
import core.support.{FloatWithAlmostEquals, Precision}

import org.scalatest.flatspec.AnyFlatSpec
import spire.math.UByte

class ColorRgbaSpec extends AnyFlatSpec {
  "ColorRgba" can "be converted to AWT Color" in {
    val colorRgba = ColorRgba(100, 100, 100, 55)
    val expectedAwtColor = java.awt.Color(100, 100, 100, 55)

    assert(colorRgba.asAwtColor == expectedAwtColor)
  }

  "ColorRgba" can "be created from AWT Color" in {
    val awtColor = java.awt.Color(100, 100, 100, 6)
    val expectedColorRgba = ColorRgba(100, 100, 100, 6)

    assert(ColorRgba(awtColor) == expectedColorRgba)
  }

  "ColorRgba" can "be created with color values higher than 255,  but will normalize to valid value" in {
    val colorRgba = ColorRgba(259, 255, 255, 255)

    assert(colorRgba.red.intValue === 255)
  }

  "ColorRgba" can "be created with color values below 0, but will normalize to valid value" in {
    val colorRgba = ColorRgba(-1, 255, 255, 255)

    assert(colorRgba.red.intValue === 0)
  }

  "ColorRgba" can "be converted to RGB Int, that represents its value" in {
    val colorRgba = ColorRgba(200, 150, 10, 155)

    assert(colorRgba.rgbInt === 13145610)
  }

  "ColorRgba" can "be converted to RGBA Int, that represents its value" in {
    val colorRgba = ColorRgba(200, 150, 10, 155)

    assert(colorRgba.rgbaInt === -1681353206)
  }

  "ColorRgba" can "be created from RGB Int, that represents its value" in {
    val rgbInt = 13145610;
    val expectedColor = ColorRgba(200, 150, 10, 255)

    assert(ColorRgba.fromRgbInt(rgbInt) === expectedColor)
  }

  "ColorRgba" can "be created from RGBA Int, that represents its value" in {
    val rgbaInt = -1681353206;
    val expectedColor = ColorRgba(200, 150, 10, 155)

    assert(ColorRgba.fromRgbaInt(rgbaInt) === expectedColor)
  }

  "ColorRgba" can "be converted to ColorHsla" in {
    implicit val precision: Precision = Precision(0.0099f)

    val colorRgba = ColorRgba(200, 150, 10, 125)
    val colorHsla = colorRgba.asColorHsla

    assert(
      colorHsla.hue ~= 44.21f,
      "Conversion failed: expected hue %s, received %s" format (44.21f, colorHsla.hue))
    assert(
      colorHsla.lightness ~= 0.411f,
      "Conversion failed: expected lightness %s, received %s" format (0.411f, colorHsla.lightness))
    assert(
      colorHsla.saturation ~= 0.9f,
      "Conversion failed: expected saturation %s, received %s" format (0.9f, colorHsla.saturation))
    assert(
      colorHsla.alpha == UByte(125),
      "Conversion failed: expected alpha %s, received %s" format (UByte(125), colorHsla.alpha))
  }

  "ColorRgba" can "be converted to ColorHsla and back to ColorRgba, keeping same values" in {
    val colorRgba = ColorRgba(183, 159, 10, 125)
    val colorHsla = colorRgba.asColorHsla

    assert(colorRgba == colorHsla.asColorRgba)
  }

  "ColorRgba" can "be converted to ColorHsva" in {
    implicit val precision: Precision = Precision(0.0099f)

    val colorRgba = ColorRgba(200, 150, 10, 125)
    val colorHsva = colorRgba.asColorHsva

    assert(
      colorHsva.hue ~= 44.21f,
      "Conversion failed: expected hue %s, received %s" format (44.21f, colorHsva.hue))
    assert(
      colorHsva.value ~= 0.78f,
      "Conversion failed: expected lightness %s, received %s" format (0.78f, colorHsva.value))
    assert(
      colorHsva.saturation ~= 0.95f,
      "Conversion failed: expected saturation %s, received %s" format (0.95f, colorHsva.saturation))
    assert(
      colorHsva.alpha == UByte(125),
      "Conversion failed: expected alpha %s, received %s" format (UByte(125), colorHsva.alpha))
  }

  "ColorRgba" can "be converted to ColorHsva and back to ColorRgba, keeping same values" in {
    val colorRgba = ColorRgba(183, 159, 10, 125)
    val colorHsva = colorRgba.asColorHsva

    assert(colorRgba == colorHsva.asColorRgba)
  }

  "ColorRgba values" can "be converted to float separately (in range from 0 to 1f)" in {
    implicit val precision: Precision = Precision(0.0099f)

    val colorRgba = ColorRgba(200, 150, 10, 125)

    assert(
      colorRgba.redAsFloat ~= 0.784f,
      "expected red %s, received %s" format (0.784f, colorRgba.redAsFloat))
    assert(
      colorRgba.greenAsFloat ~= 0.588f,
      "expected green %s, received %s" format (0.588f, colorRgba.greenAsFloat))
    assert(
      colorRgba.blueAsFloat ~= 0.039f,
      "expected blue %s, received %s" format (0.039f, colorRgba.blueAsFloat))
    assert(
      colorRgba.alphaAsFloat ~= 0.5f,
      "expected alpha %s, received %s" format (0.5f, colorRgba.alphaAsFloat))
  }
}
