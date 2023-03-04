package com.example
package core.color

import spire.math.UByte

case class ColorRGBA(red: UByte, green: UByte, blue: UByte, alpha: UByte = UByte(255)) extends Color {

  // is having this as lazy val instead more effective?
  override def asAWTColor: java.awt.Color = java.awt.Color(red.intValue, green.intValue, blue.intValue, alpha.intValue)

  override def asColorRGBA: ColorRGBA = this

  lazy val RGBInt: Int = (red.intValue << 16) | (green.intValue << 8) | blue.intValue

  lazy val RGBAInt: Int = (alpha.intValue << 24) | (red.intValue << 16) | (green.intValue << 8) | blue.intValue
}

object ColorRGBA {
  def apply(colorAWT: java.awt.Color): ColorRGBA = {
    apply(colorAWT.getRed, colorAWT.getGreen, colorAWT.getBlue, colorAWT.getAlpha)
  }

  def apply(red: Int, green: Int, blue: Int, alpha: Int): ColorRGBA = {
    ColorRGBA(UByte(red), UByte(green), UByte(blue), UByte(alpha))
  }
}