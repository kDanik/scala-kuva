package com.example
package core.support.math.interpolation

implicit class MedianSeq[T](seq: Seq[T]) {

  /**
   * Calculates median value of the numeric sequence. For the empty sequence None will be
   * returned.
   */
  def median(implicit num: Numeric[T]): Option[Double] = {
    val sorted = seq.sorted

    sorted.size match {
      case size if size == 0 => None
      case size if size == 1 => Some(num.toDouble(seq.head))
      case size =>
        if (size % 2 != 0) {
          Some(num.toDouble(sorted(size / 2)))
        } else {
          val a = sorted(size / 2 - 1)
          val b = sorted(size / 2)
          Some(num.toDouble(num.plus(a, b)) / 2)
        }
    }
  }
}
