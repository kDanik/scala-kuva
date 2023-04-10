# scala-kuva

**scala-kuva** (kuva means "picture" in Finnish) is supposed to be a Scala library for color (and later image)
processing, manipulation, and filters.

### Functionality

## Color types

Currently, there are implementations for 3 color formats: **RGB(A)**, **HSL(A)**, and **HSV(A)**.

They can be converted between each other and converted to java.awt.Color.

## Color manipulation

Pictures used to demonstrate different color operations/filters below:

<img src="src/main/resources/source/strawberry.png" alt="Original picture of strawberry" width="350">

<img src="src/main/resources/source/cocktail.png" alt="Original picture of cocktail" width="350">

### Grayscale

Currently, this library contains these implementations of grayscale algorithms:

##### Averaging

This algorithm simply uses average of all color channels as grayscale color.

<img src="src/main/resources/result/grayscale_averaging_strawberry.png" alt="Picture of strawberry after applying averaging grayscale" width="350">

<img src="src/main/resources/result/grayscale_averaging_cocktail.png" alt="Picture of cocktail after applying averaging grayscale" width="350">

##### Decomposition (using max channel value)

Calculates **max** value of 3 color channels and uses it as grayscale color.

<img src="src/main/resources/result/grayscale_decomposition_max_strawberry.png" alt="Picture of strawberry after applying decomposition (max) grayscale" width="350">

<img src="src/main/resources/result/grayscale_decomposition_max_cocktail.png" alt="Picture of cocktail after applying decomposition (max) grayscale" width="350">

##### Decomposition (using min channel value)

Calculates **min** value of 3 color channels and uses it as grayscale color.

<img src="src/main/resources/result/grayscale_decomposition_min_strawberry.png" alt="Picture of strawberry after applying decomposition (min) grayscale" width="350">

<img src="src/main/resources/result/grayscale_decomposition_min_cocktail.png" alt="Picture of cocktail after applying decomposition (min) grayscale" width="350">

##### Desaturation

This algorithm first convert color to HSL and then reduces saturation to 0.

<img src="src/main/resources/result/grayscale_desaturation_strawberry.png" alt="Picture of strawberry after applying desaturation grayscale" width="350">

<img src="src/main/resources/result/grayscale_desaturation_cocktail.png" alt="Picture of cocktail after applying desaturation grayscale" width="350">

##### Luma (ITU-R BT 601)

Calculate grayscale color using luma (luminance or brightness) formula (from ITU-R BT 601 specification).

<img src="src/main/resources/result/grayscale_luma_bt601_strawberry.png" alt="Picture of strawberry after applying luma (BT 601) grayscale" width="350">

<img src="src/main/resources/result/grayscale_luma_bt601_cocktail.png" alt="Picture of cocktail after applying luma (BT 601) grayscale" width="350">

##### Luma (ITU-R BT 709)

Also calculates grayscale color using luma formula, but from newer standard (ITU-R BT 709).
The weighting coefficients used in the formulas are different between the two standards.

<img src="src/main/resources/result/grayscale_luma_bt709_strawberry.png" alt="Picture of strawberry after applying luma (BT 709) grayscale" width="350">

<img src="src/main/resources/result/grayscale_luma_bt709_cocktail.png" alt="Picture of cocktail after applying luma (BT 709) grayscale" width="350">

##### Single channel (red)

Uses as grayscale color the value of red color channel

<img src="src/main/resources/result/grayscale_single_color_channel_red_strawberry.png" alt="Picture of strawberry after applying single channel (red) grayscale" width="350">

<img src="src/main/resources/result/grayscale_single_color_channel_red_cocktail.png" alt="Picture of cocktail after applying single channel (red) grayscale" width="350">

##### Single channel (green)

Uses as grayscale color the value of green color channel

<img src="src/main/resources/result/grayscale_single_color_channel_green_strawberry.png" alt="Picture of strawberry after applying single channel (green) grayscale" width="350">

<img src="src/main/resources/result/grayscale_single_color_channel_green_cocktail.png" alt="Picture of cocktail after applying single channel (green) grayscale" width="350">

##### Single channel (blue)

Uses as grayscale color the value of blue color channel

<img src="src/main/resources/result/grayscale_single_color_channel_blue_strawberry.png" alt="Picture of strawberry after applying single channel (blue) grayscale" width="350">

<img src="src/main/resources/result/grayscale_single_color_channel_blue_cocktail.png" alt="Picture of cocktail after applying single channel (blue) grayscale" width="350">

##### Lightness

Calculates and uses as grayscale lightness using formula from [this paper](https://www.academia.edu/13506981).

<img src="src/main/resources/result/grayscale_lightness_strawberry.png" alt="Picture of strawberry after applying lightness grayscale" width="350">

<img src="src/main/resources/result/grayscale_lightness_cocktail.png" alt="Picture of cocktail after applying lightness grayscale" width="350">

##### Lightness (HSL)

Calculates and uses as grayscale lightness using formula from HSL color format.

<img src="src/main/resources/result/grayscale_lightness_hsl_strawberry.png" alt="Picture of strawberry after applying lightness (HSL) grayscale" width="350">

<img src="src/main/resources/result/grayscale_lightness_hsl_cocktail.png" alt="Picture of cocktail after applying lightness (HSL) grayscale" width="350">

### Inversion

Color inversion is a photo effect that flips all colors to their opposite hue on the color wheel (with inversion of
alpha channel or without).

<img src="src/main/resources/result/color_inversion_strawberry.png" alt="Picture of strawberry after inversion" width="350">

<img src="src/main/resources/result/color_inversion_cocktail.png" alt="Picture of cocktail after inversion" width="350">

### Blending colors

To demonstrate different color blending algorithms 2 abstract (background and foreground) images will be used.

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

### Darken

...

### Lighten

...

#### Saturation

...
