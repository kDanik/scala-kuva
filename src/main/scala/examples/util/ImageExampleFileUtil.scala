package com.example
package examples.util

import core.image.ImmutableBufferedImage

import java.io.File
import javax.imageio.ImageIO

object ImageExampleFileUtil {

  /**
   * Loads image as ImmutableBufferedImage. This function doesn't have any error handling. The
   * images must be present and this function only used for generating and reading example images
   */
  def loadImage(path: String): ImmutableBufferedImage = {
    ImmutableBufferedImage(ImageIO.read(File(path)))
  }

  /**
   * Writes ImmutableBufferedImage to specified path. This function doesn't have any error
   * handling. The path must be present and this function only used for generating and reading
   * example images
   */
  def writeImage(path: String, immutableBufferedImage: ImmutableBufferedImage) = {
    ImageIO.write(immutableBufferedImage.asBufferedImage, "png", File(path))
  }
}
