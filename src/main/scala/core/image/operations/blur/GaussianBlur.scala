package com.example
package core.image.operations.blur

import core.color.types.ColorRgba
import core.image.{ImmutableBufferedImage, Pixel, Position}
import core.support.math.Gaussian

object GaussianBlur {

  /**
   * Applies Gaussian blur to image, using kernel of specified size. Bigger kernel will result in
   * more blurred image (and will impact performance).
   * @param image
   *   initial image, that should be blurred.
   * @param kernelHeight
   *   height of the kernel, used for Gaussian blur. Must be odd and above 0.
   * @param kernelWidth
   *   width of the kernel, used for Gaussian blur. Must be odd and above 0.
   * @return
   *   new image, applying Gaussian blur.
   */
  def blur(
      image: ImmutableBufferedImage,
      kernelHeight: Int = 5,
      kernelWidth: Int = 5): ImmutableBufferedImage = {
    if (kernelHeight <= 0 || kernelWidth <= 0 || kernelWidth % 2 == 0 || kernelHeight % 2 == 0)
      image
    else {
      val standardDeviation = calculateStandardDeviation(kernelHeight, kernelWidth)
      val gaussianMatrix = createGaussianMatrix(kernelHeight, kernelWidth, standardDeviation)

      image.mapPixels(pixel => blurPixel(image, pixel, kernelHeight, kernelWidth, gaussianMatrix))
    }
  }

  /**
   * Creates Gaussian matrix with weights, calculated by applying Gaussian function for X and Y.
   * The matrix size is defined by input height and width. The center of matrix is mean for both
   * Gaussian functions(x, y).
   */
  def createGaussianMatrix(
      height: Int,
      width: Int,
      standardDeviation: Double): Vector[Vector[Double]] = {
    Vector.tabulate(width, height)((x, y) =>
      Gaussian.calculate(x - (width / 2), 0, standardDeviation) * Gaussian.calculate(
        y - (height / 2),
        0,
        standardDeviation))
  }

  /**
   * Calculates color for pixel on image, by applying Gaussian blur, using provided kernel size
   * and gaussianMatrix.
   * @param image
   *   Image, on which pixel that should be blurred is.
   * @param pixelToBlur
   *   Which pixel on image should be blurred.
   * @param kernelHeight
   *   Height of kernel, used for Gaussian blur.
   * @param kernelWidth
   *   Width of kernel, used for Gaussian blur.
   * @param gaussianMatrix
   *   Gaussian matrix with weights, defined by Gaussian function. Must be of
   *   kernelHeight-kernelWidth size.
   * @return
   *   New color for pixelToBlur, calculated by applying Gaussian blur.
   */
  def blurPixel(
      image: ImmutableBufferedImage,
      pixelToBlur: Pixel,
      kernelHeight: Int,
      kernelWidth: Int,
      gaussianMatrix: Vector[Vector[Double]]): ColorRgba = {
    val pixelsInBox =
      (
        for {
          x <-
            pixelToBlur.position.xInt - kernelWidth / 2 to pixelToBlur.position.xInt + kernelWidth / 2
          y <-
            pixelToBlur.position.yInt - kernelHeight / 2 to pixelToBlur.position.yInt + kernelHeight / 2
        } yield image.getPixel(Position(x, y))
      ).flatten

    val (red, green, blue, alpha, totalWeight) = pixelsInBox.foldLeft((0.0, 0.0, 0.0, 0.0, 0.0)) {
      case ((red, green, blue, alpha, totalWeight), pixel) =>
        val colorRgba = pixel.color.asColorRgba
        val weight =
          getGaussianMatrixMultiplier(pixelToBlur.position, pixel.position, gaussianMatrix)

        (
          red + colorRgba.red.intValue * weight,
          green + colorRgba.green.intValue * weight,
          blue + colorRgba.blue.intValue * weight,
          alpha + colorRgba.alpha.intValue * weight,
          totalWeight + weight)
    }

    ColorRgba(
      (red / totalWeight).intValue,
      (green / totalWeight).intValue,
      (blue / totalWeight).intValue,
      (alpha / totalWeight).intValue)
  }

  private def getGaussianMatrixMultiplier(
      positionOfInitialPixel: Position,
      positionOfPixelOfMultiplier: Position,
      gaussianMatrix: Vector[Vector[Double]]) = {
    val x =
      positionOfInitialPixel.xInt - positionOfPixelOfMultiplier.xInt + gaussianMatrix.length / 2
    val y =
      positionOfInitialPixel.yInt - positionOfPixelOfMultiplier.yInt + gaussianMatrix(
        0).length / 2

    gaussianMatrix(x)(y)
  }

  /**
   * Same formula for approximate value of standard deviation used, as in GNU Image Manipulation
   * Program.
   */
  private def calculateStandardDeviation(kernelHeight: Double, kernelWidth: Double): Double = {
    math.sqrt(-(kernelHeight * kernelWidth) / (2 * math.log10(1 / 255.0)))
  }
}
