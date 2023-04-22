package com.example
package core.image.hash

import core.color.operations.grayscale.{GrayscaleColorConversion, GrayscaleConversionAlgorithm}
import core.color.ordering.{GrayscaleColorOrdering, GrayscalePixelOrdering}
import core.image.ImmutableBufferedImage
import core.image.operations.scale.ImageScaling

object ImageHashing {

  /**
   * dHash (Difference hash) is a simple and fast algorithm for hashing images. Resulting hash
   * should be not affected by proportions of image or minor changes to brightness and color of
   * pixels.
   * @param image
   *   image that should be hashed (any size, color or grayscale)
   * @param grayscaleAlgorithm
   *   grayscale algorithm that will be applied to image before hashing.
   * @param cols
   *   controls width of image to which input image will be scaled. Recommended / Default is 9.
   *   cols.
   * @param rows
   *   controls height of image to which input image will be scaled. Recommended / Default is 8.
   * @return
   *   Hash as Long (can be later converted to Hex), or error message, if number of rows or cols
   *   is not valid
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

  /**
   * aHash (Average hash) is a simple and fast algorithm for hashing images. It works by scaling
   * image to 8x8 grayscale image and converting colors with value > 0.5 to 1, otherwise 0.
   * @param image
   *   image that should be hashed (any size, color or grayscale)
   * @param grayscaleAlgorithm
   *   grayscale algorithm that will be applied to image before hashing.
   * @return
   *   Hash as Long (can be later converted to Hex)
   */
  def aHash(
      image: ImmutableBufferedImage,
      grayscaleAlgorithm: GrayscaleConversionAlgorithm = GrayscaleConversionAlgorithm.LUMA_BT601)
      : Long = {
    val rescaledImage = ImageScaling.scaleWithNearestNeighborAlgorithm(image, 8, 8)

    rescaledImage match {
      case Right(image) =>
        aHash(
          image.mapPixelColors(GrayscaleColorConversion.applyGrayscale(_, grayscaleAlgorithm)))
      case Left(_) =>
        0 // in this function result of image resize (input is always valid) Either will never be Left.
    }
  }

  private def dHash(rescaledGrayscaleImage: ImmutableBufferedImage): Long = {
    // convert image to same sized matrix of 1 and 0.
    // 1 for pixels that have higher color value than pixel from the right, otherwise 0.
    val bitMatrix = rescaledGrayscaleImage.rows
      .flatMap(
        _.sliding(2, 1)
          .map(pair => if (GrayscalePixelOrdering.compare(pair(0), pair(1)) > 0) 1 else 0))

    // combine resulting bit matrix into single integer value
    bitMatrix.foldLeft(0L)((result, bit) => result << 1 | bit)
  }

  private def aHash(rescaledGrayscaleImage: ImmutableBufferedImage): Long = {
    rescaledGrayscaleImage.rows.flatten
      .foldLeft(0L)((result, pixel) => {
        val bit = if (pixel.color.asColorRgba.redAsFloat > 0.5f) 1 else 0
        result << 1 | bit
      })
  }
}
