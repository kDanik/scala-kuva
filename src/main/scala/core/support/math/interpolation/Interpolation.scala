package com.example
package core.support.math.interpolation

import scala.collection.immutable.ArraySeq

object Interpolation {

  private val cubicInterpolationOpenCvConstant = -0.75f

  /**
   * Calculates value for input point (1D), using liner interpolation.
   * @param inputX
   *   Input coordinate
   * @param x1
   *   Coordinate of point 1
   * @param q1
   *   value of point 1
   * @param x2
   *   Coordinate of point 2
   * @param q2
   *   value of point 2
   * @return
   *   calculated value
   */
  def linearInterpolation(inputX: Float, x1: Float, q1: Float, x2: Float, q2: Float): Float = {
    q1 + ((q2 - q1) / (x2 - x1)) * (inputX - x1)
  }

  /**
   * Calculates value for input point (2D), using bilinear interpolation (values of 4 nearest
   * points)
   * @param inputX
   *   X of input point
   * @param inputY
   *   Y of input point
   * @param x1
   *   X of left points
   * @param y1
   *   Y of top points
   * @param x2
   *   X of right points
   * @param y2
   *   Y of bottom points
   * @param q11
   *   value of closest left top point (x1, y1)
   * @param q12
   *   value of closest left bottom point (x1, y2)
   * @param q21
   *   value of closest right top point (x2, y1)
   * @param q22
   *   value of closest right bottom point (x2, y2)
   * @return
   *   calculated value
   */
  def bilinearInterpolation(
      inputX: Float,
      inputY: Float,
      x1: Float,
      y1: Float,
      x2: Float,
      y2: Float,
      q11: Float,
      q12: Float,
      q21: Float,
      q22: Float): Float = {
    // this linear interpolation uses 2 points with same y (y1) and different x
    val linearInterpolationX1 = linearInterpolation(inputX, x1, q11, x2, q21)

    // this linear interpolation uses 2 points with same y (y2) and different x
    val linearInterpolationX2 = linearInterpolation(inputX, x1, q12, x2, q22)

    // this linear interpolation uses results of x1 and x2 interpolations and performs linear interpolation using y1, y2 coordinates
    linearInterpolation(inputY, y1, linearInterpolationX1, y2, linearInterpolationX2)
  }

  /**
   * This implementation of cubic Interpolation using weight distribution from OpenCV. <br>It is
   * assumed that: <br>1. Distance between adjacent points with weights (q0, q1, q2, q3) is always
   * 1 (means distance between q2 and q3 is 1). <br>2. Input x position is in range 0 to 1, where
   * 0 is position of point with q1 value and 1 is position of point with q2 value.
   */
  def cubicInterpolationOpenCV(x: Float, values: ArraySeq[Float]): Float = {
    val w0 =
      ((cubicInterpolationOpenCvConstant * (x + 1) - 5 * cubicInterpolationOpenCvConstant) * (x + 1) + 8 * cubicInterpolationOpenCvConstant) * (x + 1) - 4 * cubicInterpolationOpenCvConstant
    val w1 =
      ((cubicInterpolationOpenCvConstant + 2) * x - (cubicInterpolationOpenCvConstant + 3)) * x * x + 1
    val w2 =
      ((cubicInterpolationOpenCvConstant + 2) * (1 - x) - (cubicInterpolationOpenCvConstant + 3)) * (1 - x) * (1 - x) + 1
    val w3 = 1f - w0 - w1 - w2

    values(0) * w0 + values(1) * w1 + values(2) * w2 + values(3) * w3
  }

  /**
   * Alternative to OpenCV way of calculating value using cubic interpolation. Doesn't make
   * assumptions about positions of points, instead calculates weights based on them. It does
   * slightly more calculations, so OpenCV weights are better option for image scaling.
   */
  def cubicInterpolationAlternative(
      inputX: Float,
      values: ArraySeq[Float],
      positions: ArraySeq[Float]): Float = {
    val w0 =
      ((inputX - positions(1)) * (inputX - positions(2)) * (inputX - positions(3))) / ((positions(
        0) - positions(1)) * (positions(0) - positions(2)) * (positions(0) - positions(3)))
    val w1 =
      ((inputX - positions(0)) * (inputX - positions(2)) * (inputX - positions(3))) / ((positions(
        1) - positions(0)) * (positions(1) - positions(2)) * (positions(1) - positions(3)))
    val w2 =
      ((inputX - positions(0)) * (inputX - positions(1)) * (inputX - positions(3))) / ((positions(
        2) - positions(0)) * (positions(2) - positions(1)) * (positions(2) - positions(3)))
    val w3 = 1f - w0 - w1 - w2

    values(0) * w0 + values(1) * w1 + values(2) * w2 + values(3) * w3
  }

  def bicubicInterpolation(
      inputX: Float,
      inputY: Float,
      values: ArraySeq[ArraySeq[Float]]): Float = {

    // calculate cubic interpolation for each row of surrounding pixels
    val cubicInterpolationRow0 = cubicInterpolationOpenCV(inputX, values(0))
    val cubicInterpolationRow1 = cubicInterpolationOpenCV(inputX, values(1))
    val cubicInterpolationRow2 = cubicInterpolationOpenCV(inputX, values(2))
    val cubicInterpolationRow3 = cubicInterpolationOpenCV(inputX, values(3))

    // calculate final cubic interpolation using inputY and results of cubic interpolations for rows
    cubicInterpolationOpenCV(
      inputY,
      ArraySeq(
        cubicInterpolationRow0,
        cubicInterpolationRow1,
        cubicInterpolationRow2,
        cubicInterpolationRow3))
  }
}
