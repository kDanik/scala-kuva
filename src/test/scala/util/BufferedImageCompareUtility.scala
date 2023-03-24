package com.example
package util

import java.awt.image.BufferedImage

object BufferedImageCompareUtility {

  /**
   * Compares 2 image (pixel by pixel).
   *
   * @return true if images are of same size and have same pixels. Otherwise false
   */
  def compareImages(imageA: BufferedImage, imageB: BufferedImage): Boolean = {
    if (areImagesOfSameSize(imageA, imageB)) {
      // compare all pixels of images
      for (y <- 0 until imageA.getHeight) {
        for (x <- 0 until imageA.getWidth) {
          if (imageA.getRGB(x, y) != imageB.getRGB(x, y)) {
            return false
          }
        }
      }

      // if all pixels of images are equal, images are also equal
      true
    } else false
  }

  private def areImagesOfSameSize(imageA: BufferedImage, imageB: BufferedImage): Boolean = {
    imageA.getWidth() == imageB.getWidth() && imageA.getHeight() == imageB.getHeight()
  }
}
