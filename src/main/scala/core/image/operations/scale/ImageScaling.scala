package com.example
package core.image.operations.scale

import core.color.types.ColorRgba
import core.image.{ImmutableBufferedImage, Pixel}
import core.support.math.interpolation.Interpolation
import core.support.{FloatWithAlmostEquals, Precision}

object ImageScaling {
  private implicit val floatCompartmentPrecision: Precision = Precision(0.001f)

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

  /**
   * Scales image to desired proportions, using bilinear interpolation algorithm. Bilinear
   * interpolation uses 4 nearest neighbors to determine the output.
   *
   * It is not recommended to use bilinear interpolation for downscaling.
   * @param sourceImage
   * @param targetWidth
   * @param targetHeight
   * @return
   */
  def scaleWithBilinearInterpolation(
      sourceImage: ImmutableBufferedImage,
      targetWidth: Int,
      targetHeight: Int): Either[String, ImmutableBufferedImage] = {
    val scaledImage = ImmutableBufferedImage(targetHeight, targetWidth, sourceImage.imageType)

    scaledImage match {
      case Right(image) =>
        Right(scaleWithBilinearInterpolation(sourceImage, image, targetWidth, targetHeight))
      case Left(error) => Left(error)
    }
  }

  private def scaleWithBilinearInterpolation(
      sourceImage: ImmutableBufferedImage,
      targetImage: ImmutableBufferedImage,
      targetWidth: Int,
      targetHeight: Int): ImmutableBufferedImage = {

    val sourceWidth = sourceImage.Width
    val sourceHeight = sourceImage.Height

    // map each pixel of target image to pixel of source image
    targetImage.mapPixels((pixel: Pixel) => {
      // calculate position of this pixel on source image
      val sourceX = pixel.x.floatValue() / targetWidth * sourceWidth
      val sourceY = pixel.y.floatValue() / targetHeight * sourceHeight

      // convert sourceX and sourceY to position (int value) of closest pixel (bottom left pixel in bilinear interpolation)
      val normalizedSourceX =
        if (sourceX.intValue >= sourceWidth - 1) sourceWidth - 2 else sourceX.intValue
      val normalizedSourceY =
        if (sourceY.intValue >= sourceHeight - 1) sourceHeight - 2 else sourceY.intValue

      // Get values of surrounding pixels (top left, top right, bottom left, bottom right)
      val q11 = sourceImage.getPixel(normalizedSourceX, normalizedSourceY).get.color.asColorRgba
      val q12 =
        sourceImage.getPixel(normalizedSourceX, normalizedSourceY + 1).get.color.asColorRgba
      val q21 =
        sourceImage.getPixel(normalizedSourceX + 1, normalizedSourceY).get.color.asColorRgba
      val q22 =
        sourceImage.getPixel(normalizedSourceX + 1, normalizedSourceY + 1).get.color.asColorRgba

      // calculate value for each channel using bilinear interpolation
      val red = Interpolation.bilinearInterpolation(
        sourceX,
        sourceY,
        normalizedSourceX,
        normalizedSourceY,
        normalizedSourceX + 1,
        normalizedSourceY + 1,
        q11.redAsFloat,
        q12.redAsFloat,
        q21.redAsFloat,
        q22.redAsFloat)
      val green = Interpolation.bilinearInterpolation(
        sourceX,
        sourceY,
        normalizedSourceX,
        normalizedSourceY,
        normalizedSourceX + 1,
        normalizedSourceY + 1,
        q11.greenAsFloat,
        q12.greenAsFloat,
        q21.greenAsFloat,
        q22.greenAsFloat)
      val blue = Interpolation.bilinearInterpolation(
        sourceX,
        sourceY,
        normalizedSourceX,
        normalizedSourceY,
        normalizedSourceX + 1,
        normalizedSourceY + 1,
        q11.blueAsFloat,
        q12.blueAsFloat,
        q21.blueAsFloat,
        q22.blueAsFloat)
      val alpha = Interpolation.bilinearInterpolation(
        sourceX,
        sourceY,
        normalizedSourceX,
        normalizedSourceY,
        normalizedSourceX + 1,
        normalizedSourceY + 1,
        q11.alphaAsFloat,
        q12.alphaAsFloat,
        q21.alphaAsFloat,
        q22.alphaAsFloat)

      // assign to this pixel resulting color
      pixel.copy(color = ColorRgba(red, green, blue, alpha))
    })
  }
}
