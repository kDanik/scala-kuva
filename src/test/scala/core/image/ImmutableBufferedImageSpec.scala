package com.example
package core.image

import core.color.types.{Color, ColorRgba}
import util.BufferedImageCompareUtility

import org.scalatest.flatspec.AnyFlatSpec

import java.awt.image.BufferedImage

class ImmutableBufferedImageSpec extends AnyFlatSpec {
  "ImmutableBufferedImage" can "be created from BufferedImage" in {
    ImmutableBufferedImage(BufferedImage(100, 250, BufferedImage.TYPE_INT_RGB))
  }

  "ImmutableBufferedImage" should "have correct height and width" in {
    val immutableBufferedImage: ImmutableBufferedImage =
      ImmutableBufferedImage(BufferedImage(100, 250, BufferedImage.TYPE_INT_RGB))

    assert(immutableBufferedImage.Height == 250)
    assert(immutableBufferedImage.Width == 100)
  }

  "Awt BufferedImage" can "be created from ImmutableBufferedImage" in {
    val sourceBufferedImage: BufferedImage =
      BufferedImage(100, 250, BufferedImage.TYPE_INT_RGB)
    val immutableBufferedImage: ImmutableBufferedImage =
      ImmutableBufferedImage(sourceBufferedImage)

    assert(
      BufferedImageCompareUtility
        .compareImages(sourceBufferedImage, immutableBufferedImage.asBufferedImage))
  }

  "ImmutableBufferedImage getPixel" should "return pixel for valid coordinates" in {
    val immutableBufferedImage: ImmutableBufferedImage =
      ImmutableBufferedImage(BufferedImage(100, 250, BufferedImage.TYPE_INT_RGB))

    assert(immutableBufferedImage.getPixel(Position(10, 40)).isDefined)
    assert(immutableBufferedImage.getPixel(Position(10, 40)).get.position.x == 10)
    assert(immutableBufferedImage.getPixel(Position(10, 40)).get.position.y == 40)
    assert(immutableBufferedImage.getPixel(Position(10, 40)).get.color == ColorRgba(0, 0, 0, 255))
  }

  "ImmutableBufferedImage getPixel" should "return empty option for invalid coordinates" in {
    val immutableBufferedImage: ImmutableBufferedImage =
      ImmutableBufferedImage(BufferedImage(100, 250, BufferedImage.TYPE_INT_RGB))

    assert(immutableBufferedImage.getPixel(Position(-1, 20)).isEmpty)
    assert(immutableBufferedImage.getPixel(Position(20, -1)).isEmpty)
    assert(immutableBufferedImage.getPixel(Position(2000, 10)).isEmpty)
    assert(immutableBufferedImage.getPixel(Position(10, 2000)).isEmpty)
  }

  "ImmutableBufferedImage getPixels" should "return pixels for valid coordinate range" in {
    val immutableBufferedImage: ImmutableBufferedImage =
      ImmutableBufferedImage(BufferedImage(100, 250, BufferedImage.TYPE_INT_RGB))

    assert(immutableBufferedImage.getPixels(Position(10, 10), Position(10, 10)).length == 1)
    assert(
      immutableBufferedImage.getPixels(Position(10, 10), Position(10, 10)).head.position.x == 10)
    assert(
      immutableBufferedImage.getPixels(Position(10, 10), Position(10, 10)).head.position.y == 10)

    assert(immutableBufferedImage.getPixels(Position(1, 4), Position(50, 130)).length == 6350)
    assert(immutableBufferedImage.getPixels(Position(0, 0), Position(99, 249)).length == 25000)
  }

  "ImmutableBufferedImage getPixels" should "return empty Seq for invalid coordinates" in {
    val immutableBufferedImage: ImmutableBufferedImage =
      ImmutableBufferedImage(BufferedImage(100, 250, BufferedImage.TYPE_INT_RGB))

    assert(immutableBufferedImage.getPixels(Position(-1, -1), Position(0, 0)).isEmpty)
    assert(immutableBufferedImage.getPixels(Position(0, 0), Position(10, 251)).isEmpty)
    assert(immutableBufferedImage.getPixels(Position(0, 0), Position(101, 10)).isEmpty)
    assert(immutableBufferedImage.getPixels(Position(10, 0), Position(9, 10)).isEmpty)
    assert(immutableBufferedImage.getPixels(Position(0, 10), Position(10, 9)).isEmpty)
  }

  "ImmutableBufferedImage allPixelsAsSeq" should "return Seq with all pixels of this image" in {
    val immutableBufferedImage: ImmutableBufferedImage =
      ImmutableBufferedImage(BufferedImage(100, 250, BufferedImage.TYPE_INT_RGB))

    assert(immutableBufferedImage.allPixelsAsSeq.length == 25000)
  }

  "ImmutableBufferedImage setPixel" should "should create new ImmutableBufferedImage by changing one pixel" in {
    val immutableBufferedImage: ImmutableBufferedImage =
      ImmutableBufferedImage(BufferedImage(100, 250, BufferedImage.TYPE_INT_RGB))

    val immutableBufferedImageAfterSetPixel: ImmutableBufferedImage =
      immutableBufferedImage.setPixel(Pixel(Position(50, 22), ColorRgba(255, 0, 0, 255)))

    assert(immutableBufferedImage != immutableBufferedImageAfterSetPixel)
    assert(
      immutableBufferedImageAfterSetPixel
        .getPixel(Position(50, 22))
        .get
        .color == ColorRgba(255, 0, 0, 255))
  }

  "ImmutableBufferedImage setPixels" should "create new ImmutableBufferedImage by changing multiple pixels" in {
    val immutableBufferedImage: ImmutableBufferedImage =
      ImmutableBufferedImage(BufferedImage(100, 250, BufferedImage.TYPE_INT_RGB))
    val pixels: List[Pixel] = List(
      Pixel(Position(50, 22), ColorRgba(255, 0, 0, 255)),
      Pixel(Position(50, 23), ColorRgba(255, 0, 255, 255)),
      Pixel(Position(34, 22), ColorRgba(255, 255, 0, 255)))

    val immutableBufferedImageAfterSetPixels: ImmutableBufferedImage =
      immutableBufferedImage.setPixels(pixels)

    assert(immutableBufferedImage != immutableBufferedImageAfterSetPixels)

    assert(
      immutableBufferedImageAfterSetPixels
        .getPixel(Position(50, 22))
        .get
        .color == ColorRgba(255, 0, 0, 255))
    assert(
      immutableBufferedImageAfterSetPixels
        .getPixel(Position(50, 23))
        .get
        .color == ColorRgba(255, 0, 255, 255))
    assert(
      immutableBufferedImageAfterSetPixels
        .getPixel(Position(34, 22))
        .get
        .color == ColorRgba(255, 255, 0, 255))
  }

  "ImmutableBufferedImage mapPixels" should "create new ImmutableBufferedImage by function to all pixels" in {
    val immutableBufferedImage: ImmutableBufferedImage =
      ImmutableBufferedImage(BufferedImage(100, 250, BufferedImage.TYPE_INT_RGB))
    def f(pixel: Pixel): Color = ColorRgba(255, 0, 0, 255)

    val updatedImage: ImmutableBufferedImage = immutableBufferedImage.mapPixels(f)

    assert(
      updatedImage
        .getPixel(Position(0, 0))
        .get
        .color == ColorRgba(255, 0, 0, 255))
    assert(
      updatedImage
        .getPixel(Position(50, 23))
        .get
        .color == ColorRgba(255, 0, 0, 255))
    assert(
      updatedImage
        .getPixel(Position(99, 249))
        .get
        .color == ColorRgba(255, 0, 0, 255))
  }

  "ImmutableBufferedImage mapPixelColors" should "create new ImmutableBufferedImage by applying function to all pixels color" in {
    val immutableBufferedImage: ImmutableBufferedImage =
      ImmutableBufferedImage(BufferedImage(100, 250, BufferedImage.TYPE_INT_RGB))
    def f(color: Color): Color = ColorRgba(0, 0, 255, color.asColorRgba.alpha)

    val updatedImage: ImmutableBufferedImage = immutableBufferedImage.mapPixelColors(f)

    assert(
      updatedImage
        .getPixel(Position(0, 0))
        .get
        .color == ColorRgba(0, 0, 255, 255))
    assert(
      updatedImage
        .getPixel(Position(50, 23))
        .get
        .color == ColorRgba(0, 0, 255, 255))
    assert(
      updatedImage
        .getPixel(Position(99, 249))
        .get
        .color == ColorRgba(0, 0, 255, 255))
  }

  "ImmutableBufferedImage cols and rows" should "return correct representation of image" in {
    val immutableBufferedImage: ImmutableBufferedImage =
      ImmutableBufferedImage(BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB))

    val updatedImage: ImmutableBufferedImage =
      immutableBufferedImage.setPixel(Pixel(Position(1, 0), ColorRgba(100, 100, 100, 100)))

    assert(
      updatedImage.rows == Vector(
        Vector(
          Pixel(Position(0, 0), ColorRgba(0, 0, 0, 255)),
          Pixel(Position(1, 0), ColorRgba(100, 100, 100, 100))),
        Vector(
          Pixel(Position(0, 1), ColorRgba(0, 0, 0, 255)),
          Pixel(Position(1, 1), ColorRgba(0, 0, 0, 255)))))

    assert(
      updatedImage.cols == Vector(
        Vector(
          Pixel(Position(0, 0), ColorRgba(0, 0, 0, 255)),
          Pixel(Position(0, 1), ColorRgba(0, 0, 0, 255))),
        Vector(
          Pixel(Position(1, 0), ColorRgba(100, 100, 100, 100)),
          Pixel(Position(1, 1), ColorRgba(0, 0, 0, 255)))))
  }

  "ImmutableBufferedImage isPositionInBounds" should "should detect valid positions correctly" in {
    val immutableBufferedImage: ImmutableBufferedImage =
      ImmutableBufferedImage(BufferedImage(100, 120, BufferedImage.TYPE_INT_RGB))

    assert(immutableBufferedImage.isPositionInBounds(Position(0, 0)))
    assert(immutableBufferedImage.isPositionInBounds(Position(99, 119)))
    assert(immutableBufferedImage.isPositionInBounds(Position(40, 35)))
  }

  "ImmutableBufferedImage isPositionInBounds" should "should detect invalid positions" in {
    val immutableBufferedImage: ImmutableBufferedImage =
      ImmutableBufferedImage(BufferedImage(100, 120, BufferedImage.TYPE_INT_RGB))

    assert(!immutableBufferedImage.isPositionInBounds(Position(100, 50))) // x outside of bounds
    assert(!immutableBufferedImage.isPositionInBounds(Position(50, 120))) // y outside of bounds

    assert(!immutableBufferedImage.isPositionInBounds(Position(-1, 50))) // x below 0
    assert(!immutableBufferedImage.isPositionInBounds(Position(50, -1))) // y below 0
  }

  "ImmutableBufferedImage crop" should "should return correct cropped image for valid input" in {
    val immutableBufferedImage: ImmutableBufferedImage =
      ImmutableBufferedImage(BufferedImage(100, 120, BufferedImage.TYPE_INT_RGB))
        .setPixel(Pixel(Position(15, 7), ColorRgba(10, 10, 10, 10)))

    val cropResult = immutableBufferedImage.crop(Position(10, 5), Position(20, 10))

    cropResult match {
      case Left(value) =>
        fail("Crop method return error message for valid input! Message: " + value)
      case Right(cropResultImage) => {
        assert(cropResultImage.Width == 20 - 10)
        assert(cropResultImage.Height == 10 - 5)

        // if crop is correct pixel with Position(15, 7) should become pixel with position Position(15 - 10, 7 - 5)
        assert(
          cropResultImage.getPixel(Position(15 - 10, 7 - 5)).get.color.asColorRgba ==
            ColorRgba(10, 10, 10, 10))
      }
    }
  }

  "ImmutableBufferedImage crop" should "should return error message for invalid inputs" in {
    val immutableBufferedImage: ImmutableBufferedImage =
      ImmutableBufferedImage(BufferedImage(100, 120, BufferedImage.TYPE_INT_RGB))

    // start position is after end position
    assert(immutableBufferedImage.crop(Position(6, 6), Position(5, 5)).isLeft)
    // start position is not valid
    assert(immutableBufferedImage.crop(Position(0, -1), Position(5, 5)).isLeft)
    // end position is not valid
    assert(immutableBufferedImage.crop(Position(0, 0), Position(100, 121)).isLeft)
  }
}
