package com.example
package core.support

import com.example.core.support.math.MedianSeq
import org.scalatest.flatspec.AnyFlatSpec

class MedianSpec extends AnyFlatSpec {
  "Median" should "correctly calculate median for array with odd number of elements" in {
    val seq = Seq(1f, 2.3f, 4f, 3f, 4.5f)

    assert(seq.median.get == 3.0f)
  }

  "Median" should "correctly calculate median for array with even number of elements" in {
    val seq = Seq(1f, 2.3f, 3f, 4.5f)

    assert(seq.median.get == 2.65f)
  }

  "Median" should "correctly calculate median for array with 2 elements" in {
    val seq = Seq(1f, 4.5f)

    assert(seq.median.get == 2.75f)
  }

  "Median" should "correctly calculate median for array with 1 element" in {
    val seq = Seq(1f)

    assert(seq.median.get == 1f)
  }

  "Median" should "return empty option for empty array" in {
    val seq: Seq[Double] = Seq.empty

    assert(seq.median.isEmpty)
  }
}
