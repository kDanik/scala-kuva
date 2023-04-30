package com.example
package core.image

import core.color.types.{Color, ColorRgba}
import core.image
import core.image.{ImmutableBufferedImage, Pixel, Position}

import java.awt.image.BufferedImage

/**
 * This class is a primitive implementation of immutable image. Its idea is to replace java
 * mutable BufferedImage in code for image processing, and later convert this class back to
 * BufferedImage (if needed) for saving.
 *
 * Same as with java buffered image, image width and height is a bit different from pixel
 * coordinate on image. First pixel coordinate will be (0, 0) and last, corner, coordinate (Width
 * \- 1, Height - 1). In normal images first pixel coordinate will be (1, 1) and last, corner,
 * coordinate (Width, Height).
 *
 * That means that for image of size 250x250, pixel with coordinate (250, 250) doesn't exist. This
 * should considered for get / set Pixel and other function.
 *
 * @param imageRaster
 *   2D matrix with pixels for this image
 * @param imageType
 *   image type, identical to BufferedImage types, see TYPE_INT_RGB, TYPE_INT_ARGB, ...
 */
final case class ImmutableBufferedImage(
    private val imageRaster: Vector[Vector[Pixel]],
    imageType: Int) {

  /**
   * Height of this image in pixels.
   */
  lazy val Height: Int = imageRaster.length

  /**
   * Width of this image in pixels.
   */
  lazy val Width: Int = imageRaster.headOption.fold(0)(_.length)

  /**
   * Creates new Immutable image by replacing one pixel of this immutable image.
   *
   * @param pixel
   *   pixel that should be replaced in ImmutableBufferedImage. Pixel.position will control which
   *   pixel will be replaced.
   * @return
   *   new ImmutableBufferedImage after changes
   */
  def setPixel(pixel: Pixel): ImmutableBufferedImage = {
    if (isPositionInBounds(pixel.position)) {
      this.copy(
        imageRaster.updated(
          pixel.position.yInt,
          imageRaster(pixel.position.yInt).updated(pixel.position.xInt, pixel)))
    } else this
  }

  /**
   * Sets / Replaces multiple pixels in ImmutableBufferedImage. This method is faster than using
   * setPixel for each element of collection.
   *
   * @param pixels
   *   sequence of pixels that should be replaced with
   * @return
   *   new ImmutableBufferedImage after changes
   */
  def setPixels(pixels: Seq[Pixel]): ImmutableBufferedImage = {
    val validPixels = pixels.filter(pixel => isPositionInBounds(pixel.position))

    // this can be slightly optimized in the future, if needed
    val updatedImageRaster =
      validPixels.foldLeft(imageRaster)((updatedImageRaster, pixel) =>
        updatedImageRaster.updated(
          pixel.position.yInt,
          updatedImageRaster(pixel.position.yInt).updated(pixel.position.xInt, pixel)))

    this.copy(updatedImageRaster)
  }

  /**
   * Return Option with pixel for given coordinates. If coordinates are out of bound empty Option
   * will be returned.
   */
  def getPixel(position: Position): Option[Pixel] = {
    if (isPositionInBounds(position)) {
      Option(imageRaster(position.yInt)(position.xInt))
    } else Option.empty
  }

  /**
   * Returns sequence of pixels from image, using specified range (box).
   *
   * @param from
   *   get from (inclusive) which position on the image. Must be non negative.
   * @param to
   *   get until (inclusive) which position on the image. Must be higher than from position.
   * @return
   *   list of pixels in specified range or empty sequence for invalid input
   */
  def getPixels(from: Position, to: Position): Seq[Pixel] = {
    if (isPositionInBounds(from) && isPositionInBounds(to)
      && (from.xInt <= to.xInt) && (from.yInt <= to.yInt)) {
      imageRaster
        .slice(from.yInt, to.yInt + 1)
        .flatMap((pixelRow: Vector[Pixel]) => pixelRow.slice(from.xInt, to.xInt + 1))
    } else
      Seq.empty
  }

  /**
   * @return
   *   Sequence that contains all Pixel-s of this image
   */
  def allPixelsAsSeq: Seq[Pixel] = {
    imageRaster.flatten
  }

  /**
   * @return
   *   Returns representation of this image as Vector of rows (each row is Vector of pixels with
   *   same Y-position).
   */
  def rows: Vector[Vector[Pixel]] = imageRaster

  /**
   * @return
   *   Returns representation of this image as Vector of columns (each column is Vector of pixels
   *   with same X-position).
   */
  def cols: Vector[Vector[Pixel]] = imageRaster.transpose

  /**
   * Creates new ImmutableBufferedImage by applying operation to color of each pixel
   * @param operation
   *   operation to apply to each pixel color
   * @return
   *   new ImmutableBufferedImage resulting by applying operation to color of each pixel
   */
  def mapPixels(operation: Pixel => Pixel): ImmutableBufferedImage = {
    this.copy(imageRaster.map((row: Vector[Pixel]) => row.map(operation)))
  }

  /**
   * Creates new ImmutableBufferedImage by applying operation to color of each pixel
   * @param operation
   *   operation to apply to each pixel color
   * @return
   *   new ImmutableBufferedImage resulting by applying operation to color of each pixel
   */
  def mapPixelColors(operation: Color => Color): ImmutableBufferedImage = {
    mapPixels((pixel: Pixel) => pixel.copy(color = operation(pixel.color)))
  }

  /**
   * @return
   *   BufferedImage created using the type and the content of the raster from this immutable
   *   image.
   */
  def asBufferedImage: BufferedImage = {
    if (Height == 0 || Width == 0) {
      BufferedImage(Width, Height, imageType)
    } else {
      imageContentAsBufferedImage
    }
  }

  /**
   * @return
   *   true if position is in bounds of this ImmutableBufferedImage, otherwise false.
   */
  def isPositionInBounds(position: Position): Boolean = {
    position.isNotNegative && (position.xInt < Width && position.yInt < Height)
  }

  /**
   * @param orderingMethod
   *   defines how pixels will be compared
   * @return
   *   Pixel with maximum value (for provided orderingMethod)
   */
  def max(orderingMethod: Ordering[Pixel]): Pixel = {
    this.imageRaster.flatten.max(orderingMethod)
  }

  /**
   * @param orderingMethod
   *   defines how pixels will be compared
   * @return
   *   Pixel with minimum value (for provided orderingMethod)
   */
  def min(orderingMethod: Ordering[Pixel]): Pixel = {
    this.imageRaster.flatten.min(orderingMethod)
  }

  /**
   * Create new immutable image by cropping this image. If input coordinates are invalid, error
   * message will be returned instead.
   * @param positionStart
   *   start position for cropping.
   * @param positionEnd
   *   end position for cropping. Must be above startY.
   * @return
   *   New cropped image. If input coordinates are invalid, error message will be returned
   *   instead.
   */
  def crop(
      positionStart: Position,
      positionEnd: Position): Either[String, ImmutableBufferedImage] = {
    if (isPositionInBounds(positionStart) && isPositionInBounds(positionEnd)) {
      if (positionEnd.x > positionStart.x && positionEnd.y > positionStart.y) {
        val subImageWithIncorrectPixelCoordinates =
          imageRaster
            .slice(positionStart.yInt, positionEnd.yInt)
            .map(_.slice(positionStart.xInt, positionEnd.xInt))

        val subImageWithCorrectedPixelCoordinates =
          subImageWithIncorrectPixelCoordinates.map(
            _.map((oldPixel: Pixel) =>
              oldPixel.copy(position = Position(
                oldPixel.position.x - positionStart.x,
                oldPixel.position.y - positionStart.y))))

        Right(ImmutableBufferedImage(subImageWithCorrectedPixelCoordinates, imageType))

      } else Left("Start coordinate must be less than end coordinate!")
    } else Left("Coordinates are not in bounds of the image!")
  }

  private def imageContentAsBufferedImage: BufferedImage = {
    imageRaster.flatten
      .foldLeft(BufferedImage(Width, Height, imageType))((bufferedImage, pixel) => {
        val colorInt = pixel.color.asColorRgba.rgbaInt
        bufferedImage.setRGB(pixel.position.xInt, pixel.position.yInt, colorInt)

        bufferedImage
      })
  }
}

object ImmutableBufferedImage {

  /**
   * Creates ImmutableBufferedImage with given size (filled with pixels with (0,0,0,255) color).
   */
  def apply(
      height: Int,
      width: Int,
      imageType: Int = BufferedImage.TYPE_INT_RGB): Either[String, ImmutableBufferedImage] = {
    if (height >= 0 && width >= 0) {
      Right(
        ImmutableBufferedImage(
          Vector.tabulate(height, width)((y, x) =>
            Pixel(Position(x, y), ColorRgba(0, 0, 0, 255))),
          imageType))
    } else Left("Image size is invalid!")
  }

  /**
   * CreatesImmutableBufferedImage using data (size, pixel data) of BufferedImage (java awt)
   */
  def apply(bufferedImage: BufferedImage): ImmutableBufferedImage = {
    def pixelFromBufferedImagePosition(x: Int, y: Int, bufferedImage: BufferedImage): Pixel =
      Pixel(Position(x, y), ColorRgba.fromRgbaInt(bufferedImage.getRGB(x, y)))

    ImmutableBufferedImage(
      Vector.tabulate(bufferedImage.getHeight, bufferedImage.getWidth)((y, x) =>
        pixelFromBufferedImagePosition(x, y, bufferedImage)),
      bufferedImage.getType)
  }
}
