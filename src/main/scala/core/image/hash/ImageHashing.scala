package com.example
package core.image.hash

import core.color.operations.grayscale.GrayscaleColorConversion
import core.color.operations.grayscale.GrayscaleConversionAlgorithm
import core.image.operations.scale.ImageScaling
import core.image.ImmutableBufferedImage
import core.color.ordering.{GrayscaleColorOrdering, GrayscalePixelOrdering}

object ImageHashing {

  /**
   * DHash (Difference hash) is a simple and fast algorithm for hashing images. Resulting hash
   * should be not affected by proportions of image or minor changes to brightness and color of
   * pixels.
   * @param image
   *   image that should be hashed (any size, color or grayscale)
   * @param grayscaleAlgorithm
   * @param cols
   *   controls width of image to which input image will be scaled.
   * @param rows
   *   controls height of image to which input image will be scaled.
   * @return
   *   Hash as Long, or error message, if rows or cols are not valid number
   */
  def dHash(
      image: ImmutableBufferedImage,
      grayscaleAlgorithm: GrayscaleConversionAlgorithm = GrayscaleConversionAlgorithm.LUMA_BT601,
      cols: Int = 9,
      rows: Int = 8): Either[String, Long] = {
    val rescaledImage = ImageScaling.scaleWithNearestNeighborAlgorithm(image, rows, cols)

    rescaledImage match {
      case Right(image) =>
        Right(
          dHash(
            image.mapPixelColors(GrayscaleColorConversion.applyGrayscale(_, grayscaleAlgorithm))))
      case Left(error) => Left(error)
    }
  }

  private def dHash(rescaledGrayscaleImage: ImmutableBufferedImage): Long = {
    // convert image to same sized matrix of 1 and 0.
    // 1 for pixels that have higher color value than pixel from the right, otherwise 0.
    val bitMatrix = rescaledGrayscaleImage
      .rows()
      .flatMap(
        _.sliding(2, 1)
          .map(pair => if (GrayscalePixelOrdering.compare(pair(0), pair(1)) > 0) 1 else 0))

    // combine resulting bit matrix into single integer value
    bitMatrix.foldLeft(0L)((result, bit) => result << 1 | bit)
  }
}
