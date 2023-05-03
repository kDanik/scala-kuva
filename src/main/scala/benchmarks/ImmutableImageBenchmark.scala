package com.example
package benchmarks

import core.color.types.ColorRgba
import core.image.{ImmutableBufferedImage, Pixel, Position}

import org.openjdk.jmh.annotations.*
import spire.math.UByte

import java.util.concurrent.TimeUnit

/**
 * Run using: sbt "jmh:run benchmarks.ImmutableImageBenchmark"
 */
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Thread)
class ImmutableImageBenchmark {

  val image: ImmutableBufferedImage = ImmutableBufferedImage(4096, 2048) match {
    case Right(image) => image
  }

  val pixels = List(
    Pixel(Position(500, 300), ColorRgba(255, 255, 255, 255)),
    Pixel(Position(10, 10), ColorRgba(255, 255, 255, 255)),
    Pixel(Position(0, 250), ColorRgba(255, 255, 255, 255)),
    Pixel(Position(4000, 100), ColorRgba(255, 255, 255, 255)),
    Pixel(Position(1000, 2000), ColorRgba(255, 255, 255, 255)),
    Pixel(Position(400, 200), ColorRgba(255, 255, 255, 255)))

  @Benchmark def imageSetPixel(): Unit = {
    for (_ <- 1 to 100) image.setPixel(Pixel(Position(500, 300), ColorRgba(255, 255, 255, 255)))
  }

  @Benchmark def imageSetPixels(): Unit = {
    for (_ <- 1 to 100) image.setPixels(pixels)
  }

  @Benchmark def imageMapPixels(): Unit = {
    image.mapPixels(_ => ColorRgba(100, 100, 100, 255))
  }

  @Benchmark def imageMapColors(): Unit = {
    for (_ <- 1 to 10) image.mapPixelColors(color => color.asColorRgba.copy(red = UByte(0)))
  }

  @Benchmark def imageGetPixels(): Unit = {
    for (_ <- 1 to 10) image.getPixels(Position(100, 100), Position(2000, 2000))
  }

  @Benchmark def imageGetPixel(): Unit = {
    for (_ <- 1 to 100) image.getPixel(Position(500, 100))
  }

  @Benchmark def imageAllPixelsAsSeq(): Unit = {
    for (_ <- 1 to 10) image.allPixelsAsSeq
  }
}
