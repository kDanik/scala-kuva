package com.example
package core.image

import core.color.types.ColorRgba
import util.BufferedImageCompareUtility

import org.scalatest.flatspec.AnyFlatSpec

import java.awt.image.BufferedImage

class ImmutableBufferedImageSpec extends AnyFlatSpec {
  "ImmutableBufferedImage" can "be created from BufferedImage" in {
    ImmutableBufferedImage.apply(BufferedImage(100, 250, BufferedImage.TYPE_INT_RGB))
  }


  "ImmutableBufferedImage" should "have correct height and width" in {
    val immutableBufferedImage: ImmutableBufferedImage = ImmutableBufferedImage.apply(BufferedImage(100, 250, BufferedImage.TYPE_INT_RGB))

    assert(immutableBufferedImage.Height == 250)
    assert(immutableBufferedImage.Width == 100)
  }

  "Awt BufferedImage" can "be created from ImmutableBufferedImage" in {
    val sourceBufferedImage: BufferedImage = BufferedImage(100, 250, BufferedImage.TYPE_INT_RGB)
    val immutableBufferedImage: ImmutableBufferedImage = ImmutableBufferedImage.apply(sourceBufferedImage)

    assert(BufferedImageCompareUtility.compareImages(sourceBufferedImage, immutableBufferedImage.asBufferedImage))
  }

  "ImmutableBufferedImage getPixel" should "return color for valid coordinates" in {
    val immutableBufferedImage: ImmutableBufferedImage = ImmutableBufferedImage.apply(BufferedImage(100, 250, BufferedImage.TYPE_INT_RGB))

    assert(immutableBufferedImage.getPixel(10, 40).isDefined)
    assert(immutableBufferedImage.getPixel(10, 40).get.x == 10)
    assert(immutableBufferedImage.getPixel(10, 40).get.y == 40)
    assert(immutableBufferedImage.getPixel(10, 40).get.color == ColorRgba.apply(0, 0, 0, 255))
  }

  "ImmutableBufferedImage getPixel" should "return empty option for invalid coordinates" in {
    val immutableBufferedImage: ImmutableBufferedImage = ImmutableBufferedImage.apply(BufferedImage(100, 250, BufferedImage.TYPE_INT_RGB))

    assert(immutableBufferedImage.getPixel(-1, 20).isEmpty)
    assert(immutableBufferedImage.getPixel(20, -1).isEmpty)
    assert(immutableBufferedImage.getPixel(2000, 10).isEmpty)
    assert(immutableBufferedImage.getPixel(10, 2000).isEmpty)
  }

  "ImmutableBufferedImage setPixel" should "should create new ImmutableBufferedImage by changing one pixel" in {
    val immutableBufferedImage: ImmutableBufferedImage = ImmutableBufferedImage.apply(BufferedImage(100, 250, BufferedImage.TYPE_INT_RGB))

    val immutableBufferedImageAfterSetPixel: ImmutableBufferedImage = immutableBufferedImage.setPixel(Pixel(50, 22, ColorRgba.apply(255, 0, 0, 255)))

    assert(immutableBufferedImage != immutableBufferedImageAfterSetPixel)
    assert(immutableBufferedImageAfterSetPixel.getPixel(50, 22).get.color == ColorRgba.apply(255, 0, 0, 255))
  }

  "ImmutableBufferedImage setPixels" should "should create new ImmutableBufferedImage by changing multiple pixels" in {
    val immutableBufferedImage: ImmutableBufferedImage = ImmutableBufferedImage.apply(BufferedImage(100, 250, BufferedImage.TYPE_INT_RGB))
    val pixels: List[Pixel] = List(Pixel(50, 22, ColorRgba.apply(255, 0, 0, 255)), Pixel(50, 23, ColorRgba.apply(255, 0, 255, 255)), Pixel(34, 22, ColorRgba.apply(255, 255, 0, 255)))

    val immutableBufferedImageAfterSetPixels: ImmutableBufferedImage = immutableBufferedImage.setPixels(pixels)

    assert(immutableBufferedImage != immutableBufferedImageAfterSetPixels)

    assert(immutableBufferedImageAfterSetPixels.getPixel(50, 22).get.color == ColorRgba.apply(255, 0, 0, 255))
    assert(immutableBufferedImageAfterSetPixels.getPixel(50, 23).get.color == ColorRgba.apply(255, 0, 255, 255))
    assert(immutableBufferedImageAfterSetPixels.getPixel(34, 22).get.color == ColorRgba.apply(255, 255, 0, 255))
  }
}
