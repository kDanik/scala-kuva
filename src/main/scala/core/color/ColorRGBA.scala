package com.example
package core.color

// instead of int other type should be used (unsigned byte would be perfect)
case class ColorRGBA(red: Int, green: Int, blue: Int, alpha: Int = 255) extends Color {

  // is having this as lazy val instead more effective?
  override def asAWTColor: java.awt.Color = new java.awt.Color(red, green, blue, alpha)

  override def asColorRGBA: ColorRGBA = this
}

object ColorRGBA {
  def apply(colorAWT : java.awt.Color): ColorRGBA = {
    new ColorRGBA(colorAWT.getRed, colorAWT.getGreen, colorAWT.getBlue, colorAWT.getAlpha)
  }
}