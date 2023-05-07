# Description

**Scala Kuva** is a Scala library that focuses on image and color processing and filtering.

# Functionality

## Color types

The library contains implementations for three color formats: **RGB(A)**, **HSL(A)**, and **HSV(A)**.
These color types are immutable and can be converted between each other. Additionally, they can also be converted to
java.awt.Color and created from it

## Immutable image

For all image operations in this library own implementation of the immutable image is used.
The Immutable image simply wraps two-dimensional Vector of immutable Pixels, offering some useful operations to work
with it in functional way.

It can also be converted to java.awt.image.BufferedImage and created from it.

## Color and image manipulation

This library contains implementation for various color and image operations. For example: most grayscale algorithms,
many blend modes, binarization, resizing with different algorithms, croping and more.

This initial image will be used to demonstrate the effects of the different operations.

<img src="src/main/resources/source/cocktail.png" alt="Original picture of cocktail" width="350">

### Basic image operations

#### Cropping

<img src="src/main/resources/result/crop/cropped_cocktail.png" alt="Cropped picture of cocktail" width="200">

#### Scaling

##### Nearest Neighbor

Example image is downscaled (from 1024x1280 to 400 x 500) using nearest neighbor algorithm.

<img src="src/main/resources/result/scale/downscale_nearest_neighbor_cocktail.png" alt="Downscaled (with nearest neighbor) picture of cocktail" width="200">

Example image is upscaled (from 128x160 to 1024x1280) using nearest neighbor algorithm.

<img src="src/main/resources/result/scale/upscale_nearest_neighbor_cocktail.png" alt="Upscaled (with nearest neighbor) picture of cocktail" width="300">

##### Bilinear interpolation

Example image is upscaled (from 128x160 to 1024x1280) using bilinear interpolation.

<img src="src/main/resources/result/scale/upscale_bilinear_interpolation_cocktail.png" alt="Upscaled (with bilinear interpolation) picture of cocktail" width="300">

##### Bicubic interpolation

Example image is upscaled (from 128x160 to 1024x1280) using bicubic interpolation.

<img src="src/main/resources/result/scale/upscale_bicubic_interpolation_cocktail.png" alt="Upscaled (with bicubic interpolation) picture of cocktail" width="300">

#### Hashing

##### Average hash (aHash)

The average hash algorithm works by converting an image into a grayscale,
downsampling it to a small size (8x8 per default), computing the **average** pixel value, and creating a binary hash by
comparing each pixels value with the average value.

##### Median hash (mHash)

The average hash algorithm works by converting an image into a grayscale,
downsampling it to a small size (8x8 per default), computing the **median** pixel value, and creating a binary hash by
comparing each pixels value with the median value.

##### Difference hash (dHash)

The difference hash algorithm works by converting an image into a grayscale,
downsampling it to a small size (9x8 per default), and by computing the difference in pixel values between adjacent
pixels (compares each pixel with the pixel on the right from it).

### Blur

##### Box blur

<img src="src/main/resources/result/blur/box_blur_cocktail.png" alt="Picture of cocktail after applying box blur with radius 7" width="350">

##### Median blur

<img src="src/main/resources/result/blur/median_blur_cocktail.png" alt="Picture of cocktail after applying median blur with radius 7" width="350">

##### Gaussian blur

<img src="src/main/resources/result/blur/gaussian_blur_cocktail.png" alt="Picture of cocktail after applying gaussian blur with kernel 101x101" width="350">

### Grayscale

##### Averaging

This algorithm simply uses average of all color channels as grayscale color.

<img src="src/main/resources/result/grayscale_averaging_cocktail.png" alt="Picture of cocktail after applying averaging grayscale" width="350">

##### Decomposition (using max channel value)

Calculates **max** value of 3 color channels and uses it as grayscale color.

<img src="src/main/resources/result/grayscale_decomposition_max_cocktail.png" alt="Picture of cocktail after applying decomposition (max) grayscale" width="350">

##### Decomposition (using min channel value)

Calculates **min** value of 3 color channels and uses it as grayscale color.

<img src="src/main/resources/result/grayscale_decomposition_min_cocktail.png" alt="Picture of cocktail after applying decomposition (min) grayscale" width="350">

##### Desaturation

This algorithm first convert color to HSL and then reduces saturation to 0.

<img src="src/main/resources/result/grayscale_desaturation_cocktail.png" alt="Picture of cocktail after applying desaturation grayscale" width="350">

##### Luma (ITU-R BT 601)

Calculate grayscale color using luma (luminance or brightness) formula (from ITU-R BT 601 specification).

<img src="src/main/resources/result/grayscale_luma_bt601_cocktail.png" alt="Picture of cocktail after applying luma (BT 601) grayscale" width="350">

##### Luma (ITU-R BT 709)

Also calculates grayscale color using luma formula, but from newer standard (ITU-R BT 709).
The weighting coefficients used in the formulas are different between the two standards.

<img src="src/main/resources/result/grayscale_luma_bt709_cocktail.png" alt="Picture of cocktail after applying luma (BT 709) grayscale" width="350">

##### Single channel (red)

Uses as grayscale color the value of red color channel

<img src="src/main/resources/result/grayscale_single_color_channel_red_cocktail.png" alt="Picture of cocktail after applying single channel (red) grayscale" width="350">

##### Single channel (green)

Uses as grayscale color the value of green color channel

<img src="src/main/resources/result/grayscale_single_color_channel_green_cocktail.png" alt="Picture of cocktail after applying single channel (green) grayscale" width="350">

##### Single channel (blue)

Uses as grayscale color the value of blue color channel

<img src="src/main/resources/result/grayscale_single_color_channel_blue_cocktail.png" alt="Picture of cocktail after applying single channel (blue) grayscale" width="350">

##### Lightness

Calculates and uses as grayscale lightness using formula from [this paper](https://www.academia.edu/13506981).

<img src="src/main/resources/result/grayscale_lightness_cocktail.png" alt="Picture of cocktail after applying lightness grayscale" width="350">

##### Lightness (HSL)

Calculates and uses as grayscale lightness using formula from HSL color format.

<img src="src/main/resources/result/grayscale_lightness_hsl_cocktail.png" alt="Picture of cocktail after applying lightness (HSL) grayscale" width="350">

##### Middle value

Calculates and uses as grayscale middle value of the RGB channels

<img src="src/main/resources/result/grayscale_middle_value_cocktail.png" alt="Picture of cocktail after applying middle value grayscale" width="350">

### N-shades of gray (using Luma 601 for initial grayscale)

Converts grayscale image to grayscale image with limited number of shades. (Example image has 8 shades of gray)

<img src="src/main/resources/result/n_shade_of_gray_cocktail.png" alt="Picture of cocktail after applying N-shades of gray algorithm (8 shades)" width="350">

### Inversion

Color inversion is a photo effect that flips all colors to their opposite hue on the color wheel (with inversion of
alpha channel or without).

<img src="src/main/resources/result/color_inversion_cocktail.png" alt="Picture of cocktail after inversion" width="350">

### Blending colors

To demonstrate different color blending algorithms two abstract (background and foreground) images will be used.
(There also additional examples in src/main/resources/source/blend/gradient folder)

Background (initial image):

<img src="src/main/resources/source/blend/abstract_background.png" alt="Abstract background" width="300">

Foreground (overlay image):

<img src="src/main/resources/source/blend/abstract_foreground.png" alt="Abstract foreground" width="300">

##### Simple Alpha Compositing

<img src="src/main/resources/result/blend/blend_simple_alpha_compositing.png" alt="Color blending with alpha compositing algorithm" width="300">

##### Dissolve

<img src="src/main/resources/result/blend/blend_dissolve.png" alt="Color blending with dissolve algorithm" width="300">

##### Multiply

<img src="src/main/resources/result/blend/blend_multiply.png" alt="Color blending with dissolve algorithm" width="300">

##### Screen

<img src="src/main/resources/result/blend/blend_screen.png" alt="Color blending with screen algorithm" width="300">

##### Overlay

<img src="src/main/resources/result/blend/blend_overlay.png" alt="Color blending with overlay algorithm" width="300">

##### Hard Light

<img src="src/main/resources/result/blend/blend_hard_light.png" alt="Color blending with hard light algorithm" width="300">

##### Soft Light

<img src="src/main/resources/result/blend/blend_soft_light.png" alt="Color blending with soft light algorithm" width="300">

##### Color Dodge

<img src="src/main/resources/result/blend/blend_color_dodge.png" alt="Color blending with color dodge algorithm" width="300">

##### Linear Dodge

<img src="src/main/resources/result/blend/blend_color_dodge.png" alt="Color blending with linear dodge algorithm" width="300">

##### Color Burn

<img src="src/main/resources/result/blend/blend_color_burn.png" alt="Color blending with color burn algorithm" width="300">

##### Linear Burn

<img src="src/main/resources/result/blend/blend_linear_burn.png" alt="Color blending with linear burn algorithm" width="300">

##### Vivid Light

<img src="src/main/resources/result/blend/blend_vivid_light.png" alt="Color blending with vivid light algorithm" width="300">

##### Linear Light

<img src="src/main/resources/result/blend/blend_linear_light.png" alt="Color blending with linear light algorithm" width="300">

##### Divide

<img src="src/main/resources/result/blend/blend_divide.png" alt="Color blending with divide algorithm" width="300">

##### Difference

<img src="src/main/resources/result/blend/blend_difference.png" alt="Color blending with difference algorithm" width="300">

##### Subtract

<img src="src/main/resources/result/blend/blend_subtract.png" alt="Color blending with subtract algorithm" width="300">

##### Lighten only

<img src="src/main/resources/result/blend/blend_lighten_only.png" alt="Color blending with lighten only algorithm" width="300">

##### Darken only

<img src="src/main/resources/result/blend/blend_darken_only.png" alt="Color blending with darken only algorithm" width="300">

##### Hard mix

<img src="src/main/resources/result/blend/blend_hard_mix.png" alt="Color blending with hard mix algorithm" width="300">

##### Pin light

<img src="src/main/resources/result/blend/blend_pin_light.png" alt="Color blending with pin light algorithm" width="300">

##### Reflect

<img src="src/main/resources/result/blend/blend_reflect.png" alt="Color blending with reflect algorithm" width="300">

##### Exclusion

<img src="src/main/resources/result/blend/blend_exclusion.png" alt="Color blending with exclusion algorithm" width="300">

##### Geometric mean

<img src="src/main/resources/result/blend/blend_geometric_mean.png" alt="Color blending with geometric mean algorithm" width="300">

##### Luminosity

<img src="src/main/resources/result/blend/blend_luminosity.png" alt="Color blending with geometric luminosity algorithm" width="300">

##### Color

<img src="src/main/resources/result/blend/blend_color.png" alt="Color blending with geometric color(HSL) algorithm" width="300">

### Binarization

Binarization is the process of converting a multi-tone grayscale image into a two-tone black and white image. This is
achieved by finding a threshold value, which is then used to create the binary output.

#### Otsu's method

Otsu's method is a thresholding technique used to separate an image into foreground and background pixels.
It calculates the optimal threshold value by maximizing the between-class variance of pixel intensities.

Otsu's method requires the image to be **converted into grayscale before applying the algorithm**, and the resulting
threshold value may vary depending on the specific grayscale conversion algorithm used.


<img src="src/main/resources/source/sheep.png" alt="Source sheep image" width="250">
<img src="src/main/resources/result/otsu_binarization_sheep.png" alt="Sheep image binarized with Otsu's method " width="250">
