# scala-kuva

**scala-kuva** (kuva means "picture" in Finnish) is supposed to be a Scala library for color (and later image)
processing, manipulation, and filters.

## Functionality

### Color types

Currently, there are implementations for 3 color formats: **RGB(A)**, **HSL(A)**, and **HSV(A)**.

They can be converted between each other and converted to java.awt.Color.

### Color manipulation

This is the original picture used to demonstrate different color operations/filters below:

![Original picture of strawberry](src/main/resources/source/strawberry.png)

#### Grayscale

Currently, this library contains these implementations of grayscale algorithms:

##### Averaging

This algorithm simply uses average of all color channels as grayscale color.

![Picture of strawberry after applying averaging grayscale](src/main/resources/result/grayscale_averaging_strawberry.png)

##### Decomposition (using max channel value)

Calculates **max** value of 3 color channels and uses it as grayscale color.

![Picture of strawberry after applying decomposition (max) grayscale](src/main/resources/result/grayscale_decomposition_max_strawberry.png)

##### Decomposition (using min channel value)

Calculates **min** value of 3 color channels and uses it as grayscale color.

![Picture of strawberry after applying decomposition (min) grayscale](src/main/resources/result/grayscale_decomposition_min_strawberry.png)

##### Desaturation

This algorithm first convert color to HSL and then reduces saturation to 0.

![Picture of strawberry after applying desaturation grayscale](src/main/resources/result/grayscale_desaturation_strawberry.png)

##### Luma (ITU-R BT 601)

Calculate grayscale color using luma (luminance or brightness) formula (from ITU-R BT 601 specification).

![Picture of strawberry after applying luma (BT 601) grayscale](src/main/resources/result/grayscale_luma_bt601_strawberry.png)

##### Luma (ITU-R BT 709)

Also calculates grayscale color using luma formula, but from newer standard (ITU-R BT 709).
The weighting coefficients used in the formulas are different between the two standards.

![Picture of strawberry after applying luma (BT 709) grayscale](src/main/resources/result/grayscale_luma_bt709_strawberry.png)

##### Single channel (red)

Uses as grayscale color the value of red color channel

![Picture of strawberry after applying single channel (red) grayscale](src/main/resources/result/grayscale_single_color_channel_red_strawberry.png)

##### Single channel (green)

Uses as grayscale color the value of green color channel

![Picture of strawberry after applying single channel (green) grayscale](src/main/resources/result/grayscale_single_color_channel_green_strawberry.png)

##### Single channel (blue)

Uses as grayscale color the value of blue color channel

![Picture of strawberry after applying single channel (blue) grayscale](src/main/resources/result/grayscale_single_color_channel_blue_strawberry.png)

##### Lightness

Calculates and uses as grayscale lightness using formula from [this paper](https://www.academia.edu/13506981).

![Picture of strawberry after applying lightness grayscale](src/main/resources/result/grayscale_lightness_strawberry.png)

##### Lightness (HSL)

Calculates and uses as grayscale lightness using formula from HSL color format.

![Picture of strawberry after applying lightness (HSL) grayscale](src/main/resources/result/grayscale_lightness_hsl_strawberry.png)

#### Inversion

Color inversion is a photo effect that flips all colors to their opposite hue on the color wheel (with inversion of
alpha channel or without).

![Picture of strawberry after inversion](src/main/resources/result/color_inversion_strawberry.png)

#### Blending colors

...

#### Darken

...

#### Lighten

...

#### Saturation

...
