package com.example
package core.image.operations.blending

import core.color.types.ColorRgba
import core.color.operations.blend.{BlendMode, ColorBlending}
import core.image.{ImmutableBufferedImage, Pixel, Position}

object ImageBlending {
  def blend(
      background: ImmutableBufferedImage,
      foreground: ImmutableBufferedImage,
      foregroundPositionRelativeToBackground: Position,
      blendMode: BlendMode): ImmutableBufferedImage = {
    background.mapPixels(backgroundPixel => {
      val foregroundPixelPosition = calculateCorrespondingForegroundPixelPosition(
        backgroundPixel.position,
        foregroundPositionRelativeToBackground)

      foreground.getPixel(foregroundPixelPosition) match {
        case Some(foregroundPixel) =>
          blendTwoPixels(backgroundPixel, foregroundPixel, blendMode)
        case None => backgroundPixel.color
      }
    })
  }

  private def blendTwoPixels(
      backgroundPixel: Pixel,
      foregroundPixel: Pixel,
      blendMode: BlendMode): ColorRgba = {
    ColorBlending.blend(backgroundPixel.color, foregroundPixel.color, blendMode)
  }

  private def calculateCorrespondingForegroundPixelPosition(
      backgroundPixelPosition: Position,
      foregroundPositionRelativeToBackground: Position): Position = {
    Position(
      backgroundPixelPosition.x - foregroundPositionRelativeToBackground.x,
      backgroundPixelPosition.y - foregroundPositionRelativeToBackground.y)
  }
}
