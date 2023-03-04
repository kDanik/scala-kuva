package com.example
package core.color

import java.awt.*

trait Color {
  def asAWTColor : java.awt.Color
  def asColorRGBA : ColorRGBA
}
