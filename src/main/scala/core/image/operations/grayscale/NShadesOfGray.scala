package com.example
package core.image.operations.grayscale

import core.color.types.{Color, ColorRgba}
import core.image.ImmutableBufferedImage
import core.image.operations.binarization.GrayscalePixelOrdering

/**
 * N-shades of gray can be applied to grayscale images, to limit number of shades of gray present
 * on image. This will create stylized image, and also reduce image size.
 *
 * N-shades of gray can be also used to binarize image (if only 2 shades are used), but for proper
 * results other binarization algorithms are much better.
 */
object NShadesOfGray {

  /**
   * Converts grayscale image to n-shades of gray grayscale image
   * @param image
   *   Initial grayscale image
   * @param numberOfShades
   *   How many shades should resulting image have (maximum)
   * @param useImagesMaxAndMinValues
   *   Should image min and max values used for threshold calculation (min will become white, max
   *   will become black). If false 0 and 255 will be used as min and max
   * @return
   *   modified image
   */
  def applyNShadesOfGray(
      image: ImmutableBufferedImage,
      numberOfShades: Int,
      useImagesMaxAndMinValues: Boolean): ImmutableBufferedImage = {
    val multiplierPerThreshold = 255f / numberOfShades

    val minimumValue =
      if (useImagesMaxAndMinValues)
        image.min(GrayscalePixelOrdering).color.asColorRgba.red.intValue
      else 0
    val maximumValue =
      if (useImagesMaxAndMinValues)
        image.max(GrayscalePixelOrdering).color.asColorRgba.red.intValue
      else 255

    val threshold = ((maximumValue - minimumValue) / (numberOfShades.floatValue - 1)).intValue

    if (threshold == 0) {
      // TODO improve error handling for threshold == 0 (for too big numberOfShades)
      image
    } else {
      image.mapPixelColors(
        applyNShadesOfGray(_, multiplierPerThreshold, threshold, numberOfShades, minimumValue))
    }
  }

  private def applyNShadesOfGray(
      color: Color,
      multiplierPerThreshold: Float,
      threshold: Int,
      numberOfShades: Int,
      minimumValue: Int): Color = {
    val colorRgba = color.asColorRgba

    val thresholdSteps =
      ((colorRgba.green.intValue - minimumValue) / threshold)

    val newGrayscaleValue =
      if (thresholdSteps == 0) 0
      else if (thresholdSteps == numberOfShades) 255
      else (thresholdSteps * multiplierPerThreshold).intValue

    ColorRgba(newGrayscaleValue, newGrayscaleValue, newGrayscaleValue, colorRgba.alpha)
  }
}
