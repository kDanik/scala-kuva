package com.example
package core.image.temp

import core.color.types.{Color, ColorRgba}

import java.awt.image.BufferedImage

final case class ImmutableBufferedImage(imageRaster: Vector[Vector[Pixel]]) {
  lazy val Height = imageRaster.length;

  lazy val Width = imageRaster.headOption match {
    case Some(value) => value.length
    case None => 0
  }

  def setPixel(pixel: Pixel): ImmutableBufferedImage = {
    if (isPositionInBounds(pixel.x, pixel.y)) {
      this.copy(imageRaster.updated(pixel.y, imageRaster(pixel.y).updated(pixel.x, pixel)))
    } else this
  }

  def getPixel(x: Int, y: Int): Option[Pixel] = {
    if (isPositionInBounds(x, y)) {
      Option.apply(imageRaster(y)(x))
    } else Option.empty
  }

  def asBufferedImage: BufferedImage = {
    if (Height == 0 || Width == 0) {
      BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB)
    } else {
      imageRasterContentAsBufferedImage
    }
  }

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
  def apply(height: Int, width: Int): Option[ImmutableBufferedImage] = {
    if (isPositionNonNegative(width, height)) {
      Option.apply(ImmutableBufferedImage(Vector.tabulate(height, width)((y, x) => Pixel(x, y, ColorRgba.apply(0, 0, 0, 0)))))
    } else Option.empty
  }


  def apply(bufferedImage: BufferedImage): ImmutableBufferedImage = {
    def pixelFromBufferedImagePosition(x: Int, y: Int, bufferedImage: BufferedImage): Pixel = Pixel(x, y, ColorRgba.fromRgbaInt(bufferedImage.getRGB(x, y)))

    ImmutableBufferedImage(Vector.tabulate(bufferedImage.getHeight, bufferedImage.getWidth)((y, x) => pixelFromBufferedImagePosition(x, y, bufferedImage)))
  }

  def isPositionNonNegative(x: Int, y: Int): Boolean = {
    x >= 0 && y >= 0
  }
}