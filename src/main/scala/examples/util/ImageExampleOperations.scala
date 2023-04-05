package com.example
package examples.util

import core.color.types.Color
import core.image.{ImmutableBufferedImage, Pixel}

/**
 * Operations from this object will be eventually replaced with better and more functional way.
 * Currently it is used only to generate example images
 */
object ImageExampleOperations {
  def combineTwoImageInOne(
      sourceBackgroundImage: ImmutableBufferedImage,
      sourceForegroundImage: ImmutableBufferedImage,
      colorOperation: (Color, Color) => Color): ImmutableBufferedImage = {
    // this test method assumes that 2 images have same size. For real life blending also coordinates of images should be used
    // (to combine images with different size
    val width = sourceBackgroundImage.Width
    val height = sourceBackgroundImage.Height

    // it also assumes images have same type
    var resultedImage =
      ImmutableBufferedImage(height, width, sourceBackgroundImage.imageType).get;

    for (x <- 0 until width; y <- 0 until height) {
      resultedImage = resultedImage.setPixel(
        Pixel(
          x,
          y,
          colorOperation(
            sourceBackgroundImage.getPixel(x, y).get.color.asColorRgba,
            sourceForegroundImage.getPixel(x, y).get.color.asColorRgba)))
    }

    resultedImage
  }
}