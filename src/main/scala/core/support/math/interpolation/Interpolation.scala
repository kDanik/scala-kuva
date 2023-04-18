package com.example
package core.support.math.interpolation

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
   *
   * @param x
   *   position of x between point q1 and q2 (value from 0 to 1)
   * @param q0
   *   value of point x0
   * @param q1
   *   value of point x1
   * @param q2
   *   value of point x2
   * @param q3
   *   value of point x3
   * @return
   *   calculated value for position x
   */
  def cubicInterpolationOpenCV(x: Float, q0: Float, q1: Float, q2: Float, q3: Float): Float = {
    val w0 =
      ((cubicInterpolationOpenCvConstant * (x + 1) - 5 * cubicInterpolationOpenCvConstant) * (x + 1) + 8 * cubicInterpolationOpenCvConstant) * (x + 1) - 4 * cubicInterpolationOpenCvConstant
    val w1 =
      ((cubicInterpolationOpenCvConstant + 2) * x - (cubicInterpolationOpenCvConstant + 3)) * x * x + 1
    val w2 =
      ((cubicInterpolationOpenCvConstant + 2) * (1 - x) - (cubicInterpolationOpenCvConstant + 3)) * (1 - x) * (1 - x) + 1
    val w3 = 1f - w0 - w1 - w2

    q0 * w0 + q1 * w1 + q2 * w2 + q3 * w3
  }

  /**
   * Alternative to OpenCV way of calculating value using cubic interpolation. Doesn't make
   * assumptions about positions of points, instead calculates weights based on them. It does
   * slightly more calculations, so OpenCV weights are better option for image scaling.
   */
  def cubicInterpolationAlternative(
      inputX: Float,
      x0: Float,
      q0: Float,
      x1: Float,
      q1: Float,
      x2: Float,
      q2: Float,
      x3: Float,
      q3: Float): Float = {
    val w0 = ((inputX - x1) * (inputX - x2) * (inputX - x3)) / ((x0 - x1) * (x0 - x2) * (x0 - x3))
    val w1 = ((inputX - x0) * (inputX - x2) * (inputX - x3)) / ((x1 - x0) * (x1 - x2) * (x1 - x3))
    val w2 = ((inputX - x0) * (inputX - x1) * (inputX - x3)) / ((x2 - x0) * (x2 - x1) * (x2 - x3))
    val w3 = 1f - w0 - w1 - w2

    q0 * w0 + q1 * w1 + q2 * w2 + q3 * w3
  }

  def bicubicInterpolation(
      inputX: Float,
      inputY: Float,
      q00: Float,
      q01: Float,
      q02: Float,
      q03: Float,
      q10: Float,
      q11: Float,
      q12: Float,
      q13: Float,
      q20: Float,
      q21: Float,
      q22: Float,
      q23: Float,
      q30: Float,
      q31: Float,
      q32: Float,
      q33: Float): Float = {
    // calculate cubic interpolation for each row of surrounding pixels
    val cubicInterpolationRow0 = cubicInterpolationOpenCV(inputX, q00, q01, q02, q03)
    val cubicInterpolationRow1 = cubicInterpolationOpenCV(inputX, q10, q11, q12, q13)
    val cubicInterpolationRow2 = cubicInterpolationOpenCV(inputX, q20, q21, q22, q23)
    val cubicInterpolationRow3 = cubicInterpolationOpenCV(inputX, q30, q31, q32, q33)

    // calculate final cubic interpolation using inputY and results of cubic interpolations for rows
    cubicInterpolationOpenCV(
      inputY,
      cubicInterpolationRow0,
      cubicInterpolationRow1,
      cubicInterpolationRow2,
      cubicInterpolationRow3)
  }
}
