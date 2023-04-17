package com.example
package core.support.math.interpolation

object Interpolation {

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
   * Calculates value for input point (2D), using bilinear interpolation.
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
}
