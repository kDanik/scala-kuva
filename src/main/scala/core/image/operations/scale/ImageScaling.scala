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

  def scaleWithBicubicInterpolation(
      sourceImage: ImmutableBufferedImage,
      targetWidth: Int,
      targetHeight: Int): Either[String, ImmutableBufferedImage] = {
    val scaledImage = ImmutableBufferedImage(targetHeight, targetWidth, sourceImage.imageType)

    scaledImage match {
      case Right(image) =>
        Right(scaleWithBicubicInterpolation(sourceImage, image, targetWidth, targetHeight))
      case Left(error) => Left(error)
    }
  }

  private def scaleWithBicubicInterpolation(
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

      // TODO for bicubic interpolation either border should be created, or border pixels completely ignored

      if (sourceX.intValue >= sourceWidth - 2 || sourceY.intValue >= sourceHeight - 2 || sourceY.intValue == 0 || sourceX.intValue == 0) {
        pixel.copy(color = ColorRgba(0, 0, 0, 255))
      } else {

        val normalizedSourceX = sourceX.intValue
        val normalizedSourceY = sourceY.intValue

        // TODO simplify value assignment. (maybe with 2d array and map / fill / tabulate?)
        // Get values of surrounding all 16 pixels
        val q00 =
          sourceImage.getPixel(normalizedSourceX - 1, normalizedSourceY - 1).get.color.asColorRgba
        val q01 =
          sourceImage.getPixel(normalizedSourceX, normalizedSourceY - 1).get.color.asColorRgba
        val q02 =
          sourceImage.getPixel(normalizedSourceX + 1, normalizedSourceY - 1).get.color.asColorRgba
        val q03 =
          sourceImage.getPixel(normalizedSourceX + 2, normalizedSourceY - 1).get.color.asColorRgba

        val q10 =
          sourceImage.getPixel(normalizedSourceX - 1, normalizedSourceY).get.color.asColorRgba
        val q11 =
          sourceImage.getPixel(normalizedSourceX, normalizedSourceY).get.color.asColorRgba
        val q12 =
          sourceImage.getPixel(normalizedSourceX + 1, normalizedSourceY).get.color.asColorRgba
        val q13 =
          sourceImage.getPixel(normalizedSourceX + 2, normalizedSourceY).get.color.asColorRgba

        val q20 =
          sourceImage.getPixel(normalizedSourceX - 1, normalizedSourceY + 1).get.color.asColorRgba
        val q21 =
          sourceImage.getPixel(normalizedSourceX, normalizedSourceY + 1).get.color.asColorRgba
        val q22 =
          sourceImage.getPixel(normalizedSourceX + 1, normalizedSourceY + 1).get.color.asColorRgba
        val q23 =
          sourceImage.getPixel(normalizedSourceX + 2, normalizedSourceY + 1).get.color.asColorRgba

        val q30 =
          sourceImage.getPixel(normalizedSourceX - 1, normalizedSourceY + 2).get.color.asColorRgba
        val q31 =
          sourceImage.getPixel(normalizedSourceX, normalizedSourceY + 2).get.color.asColorRgba
        val q32 =
          sourceImage.getPixel(normalizedSourceX + 1, normalizedSourceY + 2).get.color.asColorRgba
        val q33 =
          sourceImage.getPixel(normalizedSourceX + 2, normalizedSourceY + 2).get.color.asColorRgba

        // calculate value for each channel using bilinear interpolation
        val red = Interpolation.bicubicInterpolation(
          sourceX - normalizedSourceX,
          sourceY - normalizedSourceY,
          q00.redAsFloat,
          q01.redAsFloat,
          q02.redAsFloat,
          q03.redAsFloat,
          q10.redAsFloat,
          q11.redAsFloat,
          q12.redAsFloat,
          q13.redAsFloat,
          q20.redAsFloat,
          q21.redAsFloat,
          q22.redAsFloat,
          q23.redAsFloat,
          q30.redAsFloat,
          q31.redAsFloat,
          q32.redAsFloat,
          q33.redAsFloat)
        val green = Interpolation.bicubicInterpolation(
          sourceX - normalizedSourceX,
          sourceY - normalizedSourceY,
          q00.greenAsFloat,
          q01.greenAsFloat,
          q02.greenAsFloat,
          q03.greenAsFloat,
          q10.greenAsFloat,
          q11.greenAsFloat,
          q12.greenAsFloat,
          q13.greenAsFloat,
          q20.greenAsFloat,
          q21.greenAsFloat,
          q22.greenAsFloat,
          q23.greenAsFloat,
          q30.greenAsFloat,
          q31.greenAsFloat,
          q32.greenAsFloat,
          q33.greenAsFloat)
        val blue = Interpolation.bicubicInterpolation(
          sourceX - normalizedSourceX,
          sourceY - normalizedSourceY,
          q00.blueAsFloat,
          q01.blueAsFloat,
          q02.blueAsFloat,
          q03.blueAsFloat,
          q10.blueAsFloat,
          q11.blueAsFloat,
          q12.blueAsFloat,
          q13.blueAsFloat,
          q20.blueAsFloat,
          q21.blueAsFloat,
          q22.blueAsFloat,
          q23.blueAsFloat,
          q30.blueAsFloat,
          q31.blueAsFloat,
          q32.blueAsFloat,
          q33.blueAsFloat)
        val alpha = Interpolation.bicubicInterpolation(
          sourceX - normalizedSourceX,
          sourceY - normalizedSourceY,
          q00.alphaAsFloat,
          q01.alphaAsFloat,
          q02.alphaAsFloat,
          q03.alphaAsFloat,
          q10.alphaAsFloat,
          q11.alphaAsFloat,
          q12.alphaAsFloat,
          q13.alphaAsFloat,
          q20.alphaAsFloat,
          q21.alphaAsFloat,
          q22.alphaAsFloat,
          q23.alphaAsFloat,
          q30.alphaAsFloat,
          q31.alphaAsFloat,
          q32.alphaAsFloat,
          q33.alphaAsFloat)

        // assign to this pixel resulting color
        pixel.copy(color = ColorRgba(red, green, blue, alpha))
      }
    })
  }
}
