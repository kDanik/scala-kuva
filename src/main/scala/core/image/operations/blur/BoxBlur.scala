package com.example
package core.image.operations.blur

import core.color.types.ColorRgba
import core.image.{ImmutableBufferedImage, Pixel, Position}

/**
 * A box blur is a filter in which each pixel in the resulting image has a value equal to the
 * average value of its neighboring pixels (box) in the input image.
 */
object BoxBlur {

  /**
   * Applies box blur to image. Box blur works by simply calculating value for each pixel, based
   * on average value of surrounding pixels (in box, controlled by radius param).
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

  private def blurPixel(image: ImmutableBufferedImage, pixel: Pixel, radius: Int): ColorRgba = {
    val pixelsInBox = (
      for {
        x <- pixel.position.xInt - radius to pixel.position.xInt + radius
        y <- pixel.position.yInt - radius to pixel.position.yInt + radius
      } yield image.getPixel(Position(x, y))
    ).flatten

    val numberOfPixels = pixelsInBox.length
    val summedColorValueTuple = pixelsInBox.foldLeft((0, 0, 0, 0)) {
      case ((red, green, blue, alpha), pixel) =>
        val colorRgba = pixel.color.asColorRgba
        (
          red + colorRgba.red.intValue,
          green + colorRgba.green.intValue,
          blue + colorRgba.blue.intValue,
          alpha + colorRgba.alpha.intValue)
    }

    ColorRgba(
      summedColorValueTuple._1 / numberOfPixels,
      summedColorValueTuple._2 / numberOfPixels,
      summedColorValueTuple._3 / numberOfPixels,
      summedColorValueTuple._4 / numberOfPixels)
  }
}
