package com.example
package benchmarks

import core.color.operations.grayscale.{GrayscaleColorConversion, GrayscaleConversionAlgorithm}
import core.color.types.ColorRgba
import core.image.operations.binarization.OtsuBinarization
import core.image.{ImmutableBufferedImage, Pixel, Position}
import examples.util.ImageExampleFileUtil

import org.openjdk.jmh.annotations.*
import spire.math.UByte

import java.util.concurrent.TimeUnit

/**
 * Run using: sbt "jmh:run benchmarks.BinarizationBenchmark"
 */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Thread)
class BinarizationBenchmark {
  val sourceImage: ImmutableBufferedImage =
    ImageExampleFileUtil
      .loadImage("src/main/resources/source/sheep.png")
      .mapPixelColors(color =>
        GrayscaleColorConversion.applyGrayscale(color, GrayscaleConversionAlgorithm.LUMA_BT601))

  @Benchmark def otsuBinarizationWithHighNumberOfBins(): ImmutableBufferedImage = {
    OtsuBinarization.binarizeImage(sourceImage)
  }

  @Benchmark def otsuBinarizationWithLowNumberOfBins(): ImmutableBufferedImage = {
    OtsuBinarization.binarizeImage(sourceImage, 20)
  }
}
