package com.example
package core.image.operations.blur

import core.color.types.ColorRgba
import core.image.{ImmutableBufferedImage, Pixel, Position}
import core.support.math.interpolation.MedianSeq

/**
 * A box blur is a filter in which each pixel in the resulting image has a value equal to the
 * median value of its neighboring pixels (box) in the input image.
 */
object MedianBlur {

  /**
   * Applies median blur to image. Median blur works by calculating value for each pixel, based on
   * median value of surrounding pixels (in box, controlled by radius param).
   * @param image
   *   input image that should be blurred
   * @param radius
   *   Radius controls size of the box of pixels, based on which value will be calculated. Radius
   *   of 1 will use 3x3 box, 2 - 5x5, 3 - 7x7. Higher radius results in more blurred image and
   *   much slower execution.
   * @return
   *   blurred image, or initial image if radius is not valid
   */
  def blur(image: ImmutableBufferedImage, radius: Int = 1): ImmutableBufferedImage = {
    if (radius <= 0) image
    else {
      image.mapPixels(pixel => blurPixel(image, pixel, radius))
    }
  }

  def blurPixel(image: ImmutableBufferedImage, pixel: Pixel, radius: Int = 1): ColorRgba = {
    val pixelsInBox = (
      for {
        x <- pixel.position.xInt - radius to pixel.position.xInt + radius
        y <- pixel.position.yInt - radius to pixel.position.yInt + radius
      } yield image.getPixel(Position(x, y))
    ).flatten

    val medianRed =
      pixelsInBox.map(_.color.asColorRgba.redAsFloat).median.getOrElse(0.0).floatValue
    val medianGreen =
      pixelsInBox.map(_.color.asColorRgba.greenAsFloat).median.getOrElse(0.0).floatValue
    val medianBlue =
      pixelsInBox.map(_.color.asColorRgba.blueAsFloat).median.getOrElse(0.0).floatValue
    val medianAlpha =
      pixelsInBox.map(_.color.asColorRgba.alphaAsFloat).median.getOrElse(0.0).floatValue

    ColorRgba(medianRed, medianGreen, medianBlue, medianAlpha)
  }
}
