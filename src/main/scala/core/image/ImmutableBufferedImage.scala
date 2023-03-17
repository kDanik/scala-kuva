package com.example
package core.image

import core.color.types.{Color, ColorRgba}
import core.image
import core.image.{ImmutableBufferedImage, Pixel}

import java.awt.image.BufferedImage

/**
 * This class is a primitive implementation of immutable image.
 * Its idea is to replace java mutable BufferedImage in code for image processing,
 * and later convert this class back to BufferedImage (if needed) for saving
 *
 * @param imageRaster 2D matrix with pixels for this image
 */
final case class ImmutableBufferedImage(imageRaster: Vector[Vector[Pixel]]) {
  /**
   * height of this image in pixels
   */
  lazy val Height = imageRaster.length;

  /**
   * width of this image in pixels
   */
  lazy val Width = imageRaster.headOption match {
    case Some(value) => value.length
    case None => 0
  }

  /**
   * Sets / Replaces one pixel in ImmutableBufferedImage
   *
   * @param pixel pixel that should be replaced in ImmutableBufferedImage
   * @return new ImmutableBufferedImage after changes
   */
  def setPixel(pixel: Pixel): ImmutableBufferedImage = {
    if (isPositionInBounds(pixel.x, pixel.y)) {
      this.copy(imageRaster.updated(pixel.y, imageRaster(pixel.y).updated(pixel.x, pixel)))
    } else this
  }

  /**
   * Return Option with pixel for given coordinates.
   * If coordinates are out of bound empty Option will be returned.
   */
  def getPixel(x: Int, y: Int): Option[Pixel] = {
    if (isPositionInBounds(x, y)) {
      Option.apply(imageRaster(y)(x))
    } else Option.empty
  }

  /**
   * Creates BufferedImage (java awt) using data of this object
   */
  def asBufferedImage: BufferedImage = {
    if (Height == 0 || Width == 0) {
      BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB)
    } else {
      imageRasterContentAsBufferedImage
    }
  }

  /**
   * @return true if position is in bounds of this ImmutableBufferedImage, otherwise false
   */
  def isPositionInBounds(x: Int, y: Int): Boolean = {
    ImmutableBufferedImage.isPositionNonNegative(x, y) && (x < Width && y < Height)
  }

  private def imageRasterContentAsBufferedImage: BufferedImage = {
    val bufferedImage = BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB)

    // this is a bit ugly, can it be done differently?
    imageRaster.flatten.foreach((pixel: Pixel) => bufferedImage.setRGB(pixel.x, pixel.y, pixel.color.asColorRgba.RgbaInt))
    bufferedImage
  }
}

object ImmutableBufferedImage {
  /**
   * Creates ImmutableBufferedImage with given size (filled with (0,0,0,0) color)
   */
  def apply(height: Int, width: Int): Option[ImmutableBufferedImage] = {
    if (isPositionNonNegative(width, height)) {
      Option.apply(ImmutableBufferedImage(Vector.tabulate(height, width)((y, x) => image.Pixel(x, y, ColorRgba.apply(0, 0, 0, 0)))))
    } else Option.empty
  }

  /**
   * CreatesImmutableBufferedImage using data (size, pixel data) of BufferedImage (java awt)
   */
  def apply(bufferedImage: BufferedImage): ImmutableBufferedImage = {
    def pixelFromBufferedImagePosition(x: Int, y: Int, bufferedImage: BufferedImage): Pixel = image.Pixel(x, y, ColorRgba.fromRgbaInt(bufferedImage.getRGB(x, y)))

    ImmutableBufferedImage(Vector.tabulate(bufferedImage.getHeight, bufferedImage.getWidth)((y, x) => pixelFromBufferedImagePosition(x, y, bufferedImage)))
  }

  def isPositionNonNegative(x: Int, y: Int): Boolean = {
    x >= 0 && y >= 0
  }
}