package com.example
package core.support.math.interpolation

import scala.collection.immutable.ArraySeq

object Interpolation {

  private val cubicInterpolationOpenCvConstant = -0.75f

  /**
   * Calculates value for input point (1D), using liner interpolation.
   * @param inputX
   *   Input coordinate
   * @param x0
   *   Coordinate of point 1
   * @param q0
   *   value of point 1
   * @param x1
   *   Coordinate of point 2
   * @param q1
   *   value of point 2
   * @return
   *   calculated value
   */
  def linearInterpolation(inputX: Float, x0: Float, q0: Float, x1: Float, q1: Float): Float = {
    q0 + ((q1 - q0) / (x1 - x0)) * (inputX - x0)
  }

  /**
   * Calculates value for input point (2D), using bilinear interpolation (values of 4 nearest
   * points)
   * @param inputX
   *   X of input point
   * @param inputY
   *   Y of input point
   * @param x0
   *   X of left points
   * @param y0
   *   Y of top points
   * @param x1
   *   X of right points
   * @param y1
   *   Y of bottom points
   * @param q00
   *   value of closest left top point (x0, y0)
   * @param q01
   *   value of closest left bottom point (x0, y1)
   * @param q10
   *   value of closest right top point (x0, y0)
   * @param q11
   *   value of closest right bottom point (x0, y1)
   * @return
   *   calculated value
   */
  def bilinearInterpolation(
      inputX: Float,
      inputY: Float,
      x0: Float,
      y0: Float,
      x1: Float,
      y1: Float,
      q00: Float,
      q01: Float,
      q10: Float,
      q11: Float): Float = {
    // this linear interpolation uses 2 points with same y (y0) and different x
    val linearInterpolationX1 = linearInterpolation(inputX, x0, q00, x1, q10)

    // this linear interpolation uses 2 points with same y (y1) and different x
    val linearInterpolationX2 = linearInterpolation(inputX, x0, q01, x1, q11)

    // this linear interpolation uses results of x0 and x0 interpolations and performs linear interpolation using y0, y1 coordinates
    linearInterpolation(inputY, y0, linearInterpolationX1, y1, linearInterpolationX2)
  }

  /**
   * This implementation of cubic Interpolation using weight distribution from OpenCV. <br>It is
   * assumed that: <br>1. Distance between adjacent points with weights (q0, q0, q1, q3) is always
   * 1 (means distance between q1 and q3 is 1). <br>2. Input x position is in range 0 to 1, where
   * 0 is position of point with q0 value and 1 is position of point with q1 value.
   */
  def cubicInterpolationOpenCV(x: Float, values: ArraySeq[Float]): Float = {
    val weight0 =
      ((cubicInterpolationOpenCvConstant * (x + 1) - 5 * cubicInterpolationOpenCvConstant) * (x + 1) +
        8 * cubicInterpolationOpenCvConstant) * (x + 1) - 4 * cubicInterpolationOpenCvConstant
    val weight1 =
      ((cubicInterpolationOpenCvConstant + 2) * x - (cubicInterpolationOpenCvConstant + 3)) * x * x + 1
    val weight2 =
      ((cubicInterpolationOpenCvConstant + 2) * (1 - x) - (cubicInterpolationOpenCvConstant + 3)) * (1 - x) * (1 - x) + 1
    val weight3 = 1f - weight0 - weight1 - weight2

    values(0) * weight0 + values(1) * weight1 + values(2) * weight2 + values(3) * weight3
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
    val weight0 =
      ((inputX - positions(1)) * (inputX - positions(2)) * (inputX - positions(3))) / ((positions(
        0) - positions(1)) * (positions(0) - positions(2)) * (positions(0) - positions(3)))
    val weight1 =
      ((inputX - positions(0)) * (inputX - positions(2)) * (inputX - positions(3))) / ((positions(
        1) - positions(0)) * (positions(1) - positions(2)) * (positions(1) - positions(3)))
    val weight2 =
      ((inputX - positions(0)) * (inputX - positions(1)) * (inputX - positions(3))) / ((positions(
        2) - positions(0)) * (positions(2) - positions(1)) * (positions(2) - positions(3)))
    val weight3 = 1f - weight0 - weight1 - weight2

    values(0) * weight0 + values(1) * weight1 + values(2) * weight2 + values(3) * weight3
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
