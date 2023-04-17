package com.example
package core.image.operations.scale

import core.image.{ImmutableBufferedImage, Pixel}

object ImageScaling {

  /**
   * Scales image to desired proportions, using nearest neighbor algorithm.
   * @param sourceImage
   *   Source image that should be scaled
   * @param targetWidth
   *   width of the target image
   * @param targetHeight
   *   height of the target image
   * @return
   *   new image, after scaling source image
   */
  def scaleWithNearestNeighborAlgorithm(
      sourceImage: ImmutableBufferedImage,
      targetWidth: Int,
      targetHeight: Int): Either[String, ImmutableBufferedImage] = {
    val scaledImage = ImmutableBufferedImage(targetHeight, targetWidth, sourceImage.imageType)

    scaledImage match {
      case Right(image) =>
        Right(scaleWithNearestNeighborAlgorithm(sourceImage, image, targetWidth, targetHeight))
      case Left(error) => Left(error)
    }
  }

  private def scaleWithNearestNeighborAlgorithm(
      sourceImage: ImmutableBufferedImage,
      targetImage: ImmutableBufferedImage,
      targetWidth: Int,
      targetHeight: Int): ImmutableBufferedImage = {

    val sourceWidth = sourceImage.Width
    val sourceHeight = sourceImage.Height

    // map each pixel of target image to pixel of source image
    targetImage.mapPixels((pixel: Pixel) => {
      // calculate which pixel of source image should be used as nearest neighbor for target pixel
      val sourceX =
        (pixel.x.floatValue() / targetWidth * sourceWidth).round.intValue.min(sourceWidth - 1)
      val sourceY =
        (pixel.y.floatValue() / targetHeight * sourceHeight).round.intValue.min(sourceHeight - 1)

      pixel.copy(color = sourceImage.getPixel(sourceX, sourceY).get.color)
    })
  }
}
