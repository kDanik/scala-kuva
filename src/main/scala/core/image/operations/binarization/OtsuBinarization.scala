package com.example
package core.image.operations.binarization

import core.color.types.{Color, ColorRgba}
import core.image.*

import scala.annotation.tailrec
import scala.collection.immutable.NumericRange

object OtsuBinarization {

  /**
   * Binarizes grayscale image using Otsu's method. The algorithm returns a finds intensity
   * threshold that separate pixels into two classes, foreground and background, and applies that
   * to image.
   * @param sourceGrayscaleImage
   *   source image (this must be a grayscale image!)
   * @param step
   *   step (0 to 1) for threshold values. Lower step results into better precision of the
   *   algorithm, but significantly impacts performance
   * @return
   *   binarized image
   */
  def binarizeImage(
      sourceGrayscaleImage: ImmutableBufferedImage,
      step: BigDecimal = BigDecimal(0.1)): ImmutableBufferedImage = {

    val threshold = findLeastVarianceThreshold(sourceGrayscaleImage, step)

    sourceGrayscaleImage.mapPixelColors((color: Color) =>
      if (color.asColorRgba.redAsFloat > threshold) ColorRgba(255, 255, 255, 255)
      else ColorRgba(0, 0, 0, 255))
  }

  /**
   * Calculates color threshold for image binarization using Otsu's method.
   * @param sourceGrayscaleImage
   *   source image (this must be a grayscale image!)
   * @param step
   *   step (0 to 1) for threshold values. Lower step results into better precision of the
   *   algorithm, but significantly impacts performance
   * @return
   *   least variance (Otsu's) threshold (value from 0f to 1f). This threshold splits image into
   *   background and foreground by their color value.
   */
  def findLeastVarianceThreshold(
      sourceGrayscaleImage: ImmutableBufferedImage,
      step: BigDecimal): Float = {
    val allPixels = sourceGrayscaleImage.allPixelsAsSeq()

    val totalWeight = allPixels.length
    val colorThresholds = calculateColorThresholdsRange(sourceGrayscaleImage, step)

    findLeastVarianceThreshold(
      Float.MaxValue,
      BigDecimal(0),
      allPixels,
      totalWeight,
      colorThresholds,
      0)
  }

  /**
   * Recursive function that iterates through all thresholds, looking for threshold with least
   * variance for background and foreground.
   */
  @tailrec
  private def findLeastVarianceThreshold(
      leastVariance: Float,
      leastVarianceThreshold: BigDecimal,
      allPixels: Seq[Pixel],
      totalWeight: Float,
      thresholds: NumericRange.Inclusive[BigDecimal],
      currentThresholdIndex: Int): Float = {

    val currentThreshold = thresholds(currentThresholdIndex)

    // split all pixels into background and foreground pixels using currentThreshold
    val (backgroundPixels, foregroundPixels) =
      allPixels.partition(_.color.asColorRgba.redAsFloat < currentThreshold.floatValue)

    // calculate total weights of background and foreground for this threshold
    val weightForeground = foregroundPixels.length / totalWeight
    val weightBackground = backgroundPixels.length / totalWeight

    // calculate final color variance for current threshold
    val varianceBackground = calculatePixelValueVariance(backgroundPixels)
    val varianceForeground = calculatePixelValueVariance(foregroundPixels)
    val currentThresholdVariance =
      weightForeground * varianceForeground + weightBackground * varianceBackground

    // if current variance is less than current least variance, current threshold is now least variance threshold
    val (nextLeastVariance, nextLeastVarianceThreshold) =
      if (leastVariance > currentThresholdVariance) (currentThresholdVariance, currentThreshold)
      else (leastVariance, leastVarianceThreshold)

    if (currentThresholdIndex < thresholds.length - 1) {
      findLeastVarianceThreshold(
        nextLeastVariance,
        nextLeastVarianceThreshold,
        allPixels,
        totalWeight,
        thresholds,
        currentThresholdIndex + 1)
    } else nextLeastVarianceThreshold.floatValue
  }

  /**
   * Calculates variance of pixel color for sequence of pixels (grayscale)
   */
  private def calculatePixelValueVariance(pixels: Seq[Pixel]): Float = {
    val pixelValues = pixels.map(_.color.asColorRgba.redAsFloat)
    val mean = pixelValues.sum / pixelValues.length

    val varianceSum: Float =
      pixelValues
        .fold[Float](0)((varianceSum: Float, pixelValue: Float) =>
          (varianceSum + math.pow(pixelValue - mean, 2)).floatValue)

    math.sqrt(varianceSum / pixelValues.length).floatValue
  }

  /**
   * Creates range of color thresholds from given image and step.
   */
  private def calculateColorThresholdsRange(
      sourceGrayscaleImage: ImmutableBufferedImage,
      step: BigDecimal): NumericRange.Inclusive[BigDecimal] = {
    BigDecimal(minimumColorValue(sourceGrayscaleImage)) + step to BigDecimal(
      maximumColorValue(sourceGrayscaleImage)) - step by step
  }

  private def minimumColorValue(grayscaleImage: ImmutableBufferedImage): Float = {
    grayscaleImage.min(GrayscalePixelOrdering).color.asColorRgba.redAsFloat
  }

  private def maximumColorValue(grayscaleImage: ImmutableBufferedImage): Float = {
    grayscaleImage.max(GrayscalePixelOrdering).color.asColorRgba.redAsFloat
  }
}
