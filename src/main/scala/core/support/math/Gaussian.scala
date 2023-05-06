package com.example
package core.support.math

/**
 * Gaussian distribution (or normal distribution) refers to a mathematical distribution that is
 * symmetrical and bell-shaped, with the majority of the data clustering around the mean value.
 */
object Gaussian {

  /**
   * Calculates value for inputX using Gaussian distribution.
   * @param inputX
   *   input value
   * @param mean
   *   In a Gaussian distribution, the mean represents the central value around which the data is
   *   clustered, and it is also the point of maximum probability
   * @param standardDeviation
   *   The standard deviation represents the spread or dispersion of the data from the mean, and
   *   it is a measure of how much the individual data points deviate from the central value. A
   *   higher standard deviation indicates that the data points are more spread out from the mean.
   * @return
   *   value for inputX in Gaussian curve, which (curve) is defined by mean and standardDeviation.
   */
  def calculate(inputX: Double, mean: Double, standardDeviation: Double): Double = {
    val exponent = -(Math.pow(inputX - mean, 2) / (2 * Math.pow(standardDeviation, 2)))

    (1 / (standardDeviation * Math.sqrt(2 * Math.PI))) * Math.exp(exponent)
  }
}
