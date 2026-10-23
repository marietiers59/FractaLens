# FractaLens - A Fractal Generator Library Developed in Lens.

This project provides a *Java* library for generating fractal images.
It allows users to choose from a variety of complex sequences, including the
Julia set, Mandelbrot set, and other generalized sequences, to create
static or animated fractal images using different color palettes and
transformations.

## Build and run

The project uses *Java 25* and *Gradle* (through the provided wrapper).

```bash
./gradlew build      # compiles the library and runs the unit tests
./gradlew test       # runs the unit tests and produces the JaCoCo coverage report
./gradlew run --args="-f MANDELBROT -o mandelbrot.png"   # generates a fractal from the command line
```

### Command line

The application is a command-line program (built with *picocli*): the fractal to
generate is chosen with `-f`, and the result is saved in the file given with `-o`.

```bash
# A single image of the Burning Ship fractal, centered in -1.755 - 0.03i
./gradlew run --args="-f BURNING_SHIP -x -1.755 -y -0.03 -s 0.0001 -n 64 -w 800 -h 800 -p DEGRADE -o ship.png"

# An animated GIF zooming on the Mandelbrot fractal (30 images)
./gradlew run --args="-f MANDELBROT -t ZOOM -i 30 -o zoom.gif"

# An animated GIF in which the constant of Julia's sequence rotates
./gradlew run --args="-f JULIA -t ROTATION -i 36 -o julia.gif"
```

| Option                  | Description                                                           | Default     |
|-------------------------|-----------------------------------------------------------------------|-------------|
| `-f`, `--fractal-name`  | The fractal to generate (a value of `AllSequences`). **Required.**   |             |
| `-o`, `--output`        | The file in which the fractal is saved (a GIF file for animations). **Required.** |  |
| `-n`, `--nb-iterations` | The maximum number of iterations.                                     | `250`       |
| `-w`, `--width`         | The width of the image, in pixels.                                    | `800`       |
| `-h`, `--height`        | The height of the image, in pixels.                                   | `600`       |
| `-s`, `--scale`         | The scale (zoom) applied to the complex plane.                        | `0.004`     |
| `-x`, `--focus-x`       | The real part of the center of the image.                             | `0`         |
| `-y`, `--focus-y`       | The imaginary part of the center of the image.                        | `0`         |
| `-p`, `--palette-name`  | The color palette (a value of `AllPalettes`).                         | `GREYSCALE` |
| `-t`, `--type`          | The type of image: `SINGLE`, `ZOOM` or `ROTATION`.                    | `SINGLE`    |
| `-i`, `--image-count`   | The number of images of an animation (at least 2).                    | `30`        |
| `--help`                | Shows the help message.                                               |             |

The `ROTATION` type is only available for `JULIA` and `JULIA_GENERALIZED`.

The coverage report is generated in `build/reports/jacoco/test/html/index.html`.

## Overview

The library is split into six packages.
Each package is built around one or two design patterns, summarized below.

```plantuml
@startuml
hide empty members
set separator none
skinparam packageStyle rectangle

package "complex  [Decorator]" #E0F7FA {
    interface IComplex
    class Complex
    interface IComplexPlane
    abstract class ComplexPlaneDecorator
    IComplex <|.. Complex
    IComplexPlane <|.. ComplexPlaneDecorator
}

package "complex.sequence  [Template Method + Iterator + Strategy]" #E3F2FD {
    interface IComplexSequence
    class ComplexSequenceIterator
    class ComplexSequenceLambda
    enum AllSequences
    IComplexSequence <|.. ComplexSequenceLambda
}

package "palette  [Strategy + Decorator]" #FCE4EC {
    interface IPalette
    abstract class PaletteDecorator
    enum AllPalettes
    IPalette <|.. PaletteDecorator
}

package "image  [Adapter]" #FFFDE7 {
    interface IFractalImage
    class BufferedImageAdapter
    IFractalImage <|.. BufferedImageAdapter
}

package "generator  [Strategy + Builder + Composite]" #FFF3E0 {
    interface IFractalGenerator
    class FractalGenerator
    class FractalGeneratorAnimated
    class FractalGeneratorBuilder
    IFractalGenerator <|.. FractalGenerator
    IFractalGenerator <|.. FractalGeneratorAnimated
}

package "commandLine  [Builder]" #E8F5E9 {
    class FractalCommand
}

FractalCommand ..> FractalGeneratorBuilder : <<uses>>
FractalCommand ..> FractalGeneratorAnimated : <<creates>>
FractalGeneratorBuilder ..> FractalGenerator : <<creates>>
FractalGenerator o--> IComplexPlane
FractalGenerator o--> IPalette
FractalGenerator o--> IFractalImage
FractalGenerator ..> IComplexSequence : <<uses>>
IComplexSequence ..> IComplex : <<generates>>
@enduml
```

### Complex numbers and complex plane

A pixel of the image is associated with a complex number by an `IComplexPlane`.
The default plane (`ComplexPlane`) centers the image on 0; it can then be
*decorated* to zoom on it (`ComplexPlaneZoom`) or to move its center
(`ComplexPlaneTranslate`).
Decorators can be stacked: the builder wraps a zoom inside a translation.

**Pattern: Decorator.** Each decorator implements `IComplexPlane`, holds the plane
it decorates, delegates `asComplex()` to it and transforms the result.
Adding a new transformation (e.g. a rotation of the plane) only requires a new
decorator, without modifying the existing classes.

```plantuml
@startuml
hide empty members

interface IComplex {
    + {abstract} getRealPart() : double
    + {abstract} getImaginaryPart() : double
    + {abstract} abs() : double
    + {abstract} negate() : IComplex
    + {abstract} conjugate() : IComplex
    + {abstract} add(other : IComplex) : IComplex
    + {abstract} subtract(other : IComplex) : IComplex
    + {abstract} multiply(value : double) : IComplex
    + {abstract} multiply(other : IComplex) : IComplex
    + {abstract} divide(other : IComplex) : IComplex
    + {abstract} power(power : int) : IComplex
}

class Complex {
    - realPart : double
    - imaginaryPart : double
    + Complex(realPart : double, imaginaryPart : double)
}

interface IComplexPlane {
    + {abstract} asComplex(row : int, column : int) : IComplex
}

class ComplexPlane {
    - width : int
    - height : int
    + ComplexPlane(height : int, width : int)
    + asComplex(row : int, column : int) : IComplex
}

abstract class ComplexPlaneDecorator {
    + ComplexPlaneDecorator(complexPlane : IComplexPlane)
    + asComplex(row : int, column : int) : IComplex
}

class ComplexPlaneZoom {
    - valeurZoom : double
    + ComplexPlaneZoom(valeurZoom : double, complexPlane : IComplexPlane)
    + asComplex(row : int, column : int) : IComplex
}

class ComplexPlaneTranslate {
    - valTranslate : IComplex
    + ComplexPlaneTranslate(valTranslate : IComplex, complexPlane : IComplexPlane)
    + asComplex(row : int, column : int) : IComplex
}

IComplex <|.. Complex
IComplexPlane <|.. ComplexPlane
IComplexPlane <|.. ComplexPlaneDecorator
ComplexPlaneDecorator o--> "1" IComplexPlane : complexPlane
ComplexPlaneDecorator <|-- ComplexPlaneZoom
ComplexPlaneDecorator <|-- ComplexPlaneTranslate
IComplexPlane ..> IComplex : <<creates>>
@enduml
```

### Complex sequences

A complex sequence is defined by its first term `z0` and by the way the next
term is computed from the previous one (`computeNext()`).
The generator only needs to *count* the terms of the sequence before it
diverges (modulus greater than 2) or reaches the maximum number of terms.

**Pattern: Template Method + Iterator.** `ComplexSequenceIterator` contains the
common iteration algorithm (`hasNext()` checks the divergence criterion,
`next()` calls `computeNext()`), whereas each sequence only provides the
variable step `computeNext()`.
Since `IComplexSequence` extends `Iterable<IComplex>`, any sequence can be
traversed with a `for-each` loop.

**Pattern: Strategy.** `ComplexSequenceLambda` receives the formula of the
sequence as a `BinaryOperator<IComplex>`, so a new sequence can be defined with
a lambda expression instead of a new class.
The `AllSequences` enumeration gathers the predefined sequences and gives, for
each of them, a factory `BiFunction<IComplex, Integer, IComplexSequence>`
(pixel → sequence) that can be passed to the builder.

| Constant                   | Formula                                   | Role of the pixel `z0`             |
|----------------------------|-------------------------------------------|------------------------------------|
| `JULIA`                    | `z² + c`, with `c = -0.4 + 0.6i`          | first term of the sequence         |
| `JULIA_GENERALIZED`        | `z³ + c`, with `c = 0.285 + 0.01i`        | first term of the sequence         |
| `MANDELBROT`               | `z² + z0`                                 | constant added at each step        |
| `MANDELBROT_GENERALIZED`   | `z³ + z0`                                 | constant added at each step        |
| `BURNING_SHIP`             | `(abs(Re z) + i·abs(Im z))² + z0` | constant added at each step        |
| `BURNING_SHIP_GENERALIZED` | `(abs(Re z) + i·abs(Im z))³ + z0` | constant added at each step        |
| `TRICORN`                  | `conjugate(z)² + c`, with `c = -0.4 + 0.6i` | first term of the sequence       |
| `TRICORN_GENERALIZED`      | `conjugate(z)³ + c`, with `c = 0.285 + 0.01i` | first term of the sequence     |

For the Julia-like sequences, the pixel is the first term and `c` is fixed: the
image shows which starting points stay bounded. For the Mandelbrot-like sequences
(Mandelbrot and Burning Ship), the pixel is the constant itself: the image shows
which constants give a bounded sequence.

`JuliaRotation` and `JuliaRotationGeneralized` build the factories of the images
of a rotation animation: the constant `c` of Julia's sequence is multiplied by
`e^(iθ)`, with `θ` going through a full turn.

```plantuml
@startuml
hide empty members

interface "Iterable<IComplex>" as Iterable
interface "Iterator<IComplex>" as Iterator

interface IComplexSequence {
    + {abstract} computeNext(previous : IComplex) : IComplex
}

class ComplexSequenceIterator {
    - actual : IComplex
    - maxIterations : int
    - count : int
    + ComplexSequenceIterator(sequence : IComplexSequence, actual : IComplex, maxIterations : int)
    + hasNext() : boolean
    + next() : IComplex
}

class ComplexSequenceLambda {
    - function : BinaryOperator<IComplex>
    - z0 : IComplex
    - nbTerms : int
    + computeNext(previous : IComplex) : IComplex
    + iterator() : Iterator<IComplex>
}

class JuliaRotation {
    + {static} rotate(c : IComplex, theta : double) : IComplex
    + {static} angles(step : double) : List<Double>
    + {static} factories(c : IComplex, step : double) : List<BiFunction<IComplex, Integer, IComplexSequence>>
}

class JuliaRotationGeneralized {
    + {static} isSupported(sequence : AllSequences) : boolean
    + {static} factories(sequence : AllSequences, c : IComplex, step : double) : List<BiFunction<IComplex, Integer, IComplexSequence>>
}

class ComplexSequenceJulia
class ComplexSequenceGeneralJulia
class ComplexSequenceMandelbrot
class ComplexSequenceMandelbrotGeneralized
class ComplexSequenceBurningShip
class ComplexSequenceGeneralizedBurningShip
class ComplexSequenceTricorn
class ComplexSequenceTricornGeneralized

enum AllSequences {
    JULIA
    JULIA_GENERALIZED
    MANDELBROT
    MANDELBROT_GENERALIZED
    BURNING_SHIP
    BURNING_SHIP_GENERALIZED
    TRICORN
    TRICORN_GENERALIZED
    + getSequenceFactory() : BiFunction<IComplex, Integer, IComplexSequence>
}

Iterable <|-- IComplexSequence
Iterator <|.. ComplexSequenceIterator
ComplexSequenceIterator o--> "1" IComplexSequence : sequence

IComplexSequence <|.. ComplexSequenceLambda
IComplexSequence <|.. ComplexSequenceJulia
IComplexSequence <|.. ComplexSequenceGeneralJulia
IComplexSequence <|.. ComplexSequenceMandelbrot
IComplexSequence <|.. ComplexSequenceMandelbrotGeneralized
IComplexSequence <|.. ComplexSequenceBurningShip
IComplexSequence <|.. ComplexSequenceGeneralizedBurningShip
IComplexSequence <|.. ComplexSequenceTricorn
ComplexSequenceLambda <|-- ComplexSequenceTricornGeneralized

IComplexSequence ..> ComplexSequenceIterator : <<creates>>
AllSequences ..> ComplexSequenceLambda : <<creates>>
AllSequences ..> ComplexSequenceBurningShip : <<creates>>
AllSequences ..> ComplexSequenceGeneralizedBurningShip : <<creates>>
JuliaRotation ..> ComplexSequenceJulia : <<creates>>
JuliaRotationGeneralized ..> JuliaRotation : <<uses>>
JuliaRotationGeneralized ..> ComplexSequenceGeneralJulia : <<creates>>
JuliaRotationGeneralized ..> AllSequences : <<uses>>
@enduml
```

### Color palettes

A palette associates a number of iterations with a color.

**Pattern: Strategy.** `GreyscalePalette`, `DegradePalette` (gradient between two
colors) and `FixedPalette` (one color per number of iterations) are
interchangeable implementations of `IPalette`: the generator does not know
which one it uses.

**Pattern: Decorator.** `PaletteDecorator` wraps another palette and modifies its
colors: `InvertedPalette` inverts them, `ColorMaskPalette` only keeps some of
their components (red, green, blue, yellow, cyan or magenta).
Decorators can be combined, e.g. a masked inverted gradient.

The `AllPalettes` enumeration lists every combination
*base palette × decorators* (42 constants), and creates the corresponding
palette from the maximum number of iterations.

```plantuml
@startuml
hide empty members

interface IPalette {
    + {abstract} getColor(nbIterations : int) : Color
}

class GreyscalePalette {
    - maxIterations : int
    + GreyscalePalette(maxIterations : int)
}

class DegradePalette {
    - maxIterations : int
    - startColor : Color
    - endColor : Color
    + DegradePalette(maxIterations : int, startColor : Color, endColor : Color)
}

class FixedPalette {
    - colors : List<Color>
    + FixedPalette(colors : List<Color>)
}

abstract class PaletteDecorator {
    # wrapped : IPalette
    + PaletteDecorator(wrapped : IPalette)
}

class InvertedPalette {
    + InvertedPalette(wrapped : IPalette)
}

class ColorMaskPalette {
    - mask : Mask
    + ColorMaskPalette(wrapped : IPalette, mask : Mask)
}

enum "ColorMaskPalette.Mask" as Mask {
    RED
    GREEN
    BLUE
    YELLOW
    CYAN
    MAGENTA
}

enum AllPalettes {
    GREYSCALE
    GREYSCALE_INVERTED
    GREYSCALE_RED
    DEGRADE
    FIXED
    ...
    + getPalette(maxIterations : int) : IPalette
}

IPalette <|.. GreyscalePalette
IPalette <|.. DegradePalette
IPalette <|.. FixedPalette
IPalette <|.. PaletteDecorator
PaletteDecorator o--> "1" IPalette : wrapped
PaletteDecorator <|-- InvertedPalette
PaletteDecorator <|-- ColorMaskPalette
ColorMaskPalette --> Mask
AllPalettes ..> IPalette : <<creates>>
@enduml
```

### Images

**Pattern: Adapter.** The library manipulates images through its own interface
`IFractalImage`. `BufferedImageAdapter` adapts the `BufferedImage` class of the
Java standard library to this interface, so that the rest of the code does not
depend on AWT. The output format (PNG by default) is deduced from the extension
of the file.

```plantuml
@startuml
hide empty members

interface IFractalImage {
    + {abstract} getHeight() : int
    + {abstract} getWidth() : int
    + {abstract} setColor(row : int, column : int, color : Color) : void
    + {abstract} saveAs(path : String) : void
}

class BufferedImageAdapter {
    + BufferedImageAdapter(width : int, height : int)
    + BufferedImageAdapter(image : BufferedImage)
    + getImage() : BufferedImage
}

class BufferedImage

IFractalImage <|.. BufferedImageAdapter
BufferedImageAdapter *--> "1" BufferedImage : image
@enduml
```

### Fractal generation and configuration

`FractalGenerator` computes the color of each pixel: it converts the pixel into
a complex number with the plane, builds the sequence of this pixel, counts its
terms and asks the palette for the matching color.

**Pattern: Strategy.** The generator is configured with interchangeable
objects: the plane, the palette, the image and the function creating the
sequence of each pixel.

**Pattern: Builder.** `FractalGeneratorBuilder` provides a default value for
every parameter and fluent `withXxx()` methods to change them, checking their
validity. `build()` assembles the decorated plane, the image and the generator.
A generator created by the builder remembers its configuration: a new builder
can be created from it (`new FractalGeneratorBuilder(generator)`) to derive
another generator that only differs by a few parameters (e.g., the scale).

**Pattern: Composite.** `FractalGeneratorAnimated` is itself an `IFractalGenerator`
that contains other generators (its frames). Generating it generates every
frame, and saving it writes all the frames in a single animated GIF file.
Thus, the code using a generator does not need to know whether it produces a
single image or an animation. Two kinds of animations are provided by the command
line: a *zoom* (the scale of each frame is a fraction of the previous one) and a
*rotation* (the constant of Julia's sequence rotates from one frame to the next).

```plantuml
@startuml
hide empty members

interface IFractalGenerator {
    + {abstract} generateImage() : IFractalImage
    + {abstract} computePixelColor(row : int, column : int) : Color
    + {abstract} saveImage() : void
}

class FractalGenerator {
    - outputPath : String
    - nbTherm : int
    ~ sequenceFunction : BiFunction<IComplex, Integer, IComplexSequence>
    + FractalGenerator(image : IFractalImage, plane : IComplexPlane, palette : IPalette, outputPath : String, sequenceFunction : BiFunction<IComplex, Integer, IComplexSequence>, nbTherm : int)
    + getConfiguration() : FractalGeneratorBuilder
}

class FractalGeneratorAnimated {
    - outputPath : String
    - frameDelay : int
    + FractalGeneratorAnimated(outputPath : String, frameDelay : int)
    + addFrame(generator : IFractalGenerator) : void
}

class FractalGeneratorBuilder {
    - width : int = 800
    - height : int = 800
    - maxIterations : int = 250
    - scale : double = 0.001
    - center : IComplex
    - outputPath : String = "fractal.png"
    - FractalGeneratorBuilder()
    + FractalGeneratorBuilder(generator : IFractalGenerator)
    + {static} newInstance() : FractalGeneratorBuilder
    + withSize(width : int, height : int) : FractalGeneratorBuilder
    + withScale(scale : double) : FractalGeneratorBuilder
    + withCenter(center : IComplex) : FractalGeneratorBuilder
    + withMaxIterations(maxIterations : int) : FractalGeneratorBuilder
    + withPalette(paletteType : AllPalettes) : FractalGeneratorBuilder
    + withPalette(palette : IPalette) : FractalGeneratorBuilder
    + withSequence(sequenceType : AllSequences) : FractalGeneratorBuilder
    + withSequence(sequence : BiFunction<IComplex, Integer, IComplexSequence>) : FractalGeneratorBuilder
    + withOutputPath(outputPath : String) : FractalGeneratorBuilder
    + build() : IFractalGenerator
}

interface IFractalImage
interface IComplexPlane
interface IPalette
interface IComplexSequence

IFractalGenerator <|.. FractalGenerator
IFractalGenerator <|.. FractalGeneratorAnimated
FractalGeneratorAnimated o--> "*" IFractalGenerator : frames
FractalGenerator o--> "1" IFractalImage : image
FractalGenerator o--> "1" IComplexPlane : plane
FractalGenerator o--> "1" IPalette : palette
FractalGenerator ..> IComplexSequence : <<uses>>
FractalGeneratorBuilder ..> FractalGenerator : <<creates>>
FractalGeneratorBuilder --> IPalette : paletteFactory
@enduml
```

### Command line

`FractalCommand` (package `commandLine`) reads the options given by the user with
*picocli* and configures a `FractalGeneratorBuilder` with them. According to the
requested type of image, it then returns either the generator built by the builder
(`SINGLE`), or a `FractalGeneratorAnimated` whose frames are generators derived
from the same builder (`ZOOM` and `ROTATION`). Invalid parameters (e.g., a
rotation of a sequence that is not Julia's) are reported to the user with a usage
error instead of a stack trace.

```plantuml
@startuml
hide empty members

class FractalCommand {
    - fractalName : AllSequences
    - nbIterations : int
    - width : int
    - height : int
    - scale : double
    - focusX : double
    - focusY : double
    - paletteName : AllPalettes
    - type : ImageType
    - imageCount : int
    - output : String
    + run() : void
}

enum ImageType {
    SINGLE
    ZOOM
    ROTATION
}

class FractalGeneratorBuilder
class FractalGeneratorAnimated
interface IFractalGenerator
enum AllSequences
enum AllPalettes

FractalCommand ..|> Runnable
FractalCommand --> ImageType
FractalCommand --> AllSequences
FractalCommand --> AllPalettes
FractalCommand ..> FractalGeneratorBuilder : <<uses>>
FractalCommand ..> FractalGeneratorAnimated : <<creates>>
FractalCommand ..> IFractalGenerator : <<runs>>
@enduml
```

## Feature list

| Features                                               | Design Pattern(s)          | Status      | Author(s)        |
|--------------------------------------------------------|----------------------------|-------------|------------------|
| Complex number                                         |                            | Done        | Marie Tiers      |
| Default complex plane                                  | Decorator                  | Done        | Thibault Breguet |
| Scaled complex plane                                   | Decorator                  | Done        | Thibault Breguet |
| Translated complex plane                               | Decorator                  | Done        | Théo Brureau     |
| Sequence of complex numbers                            | Template method            | Done        | Théo Brureau     |
| Iterator over a sequence of complex numbers            | Iterator                   | Done        | Joan Pesson      |
| Julia sequence                                         | Strategy                   | Done        | Théo Brureau     |
| Mandelbrot sequence                                    | Strategy                   | Done        | Joan Pesson      |
| Multijulia sequence                                    | Strategy                   | Done        | Thibault Breguet |
| Multibrot sequence                                     | Strategy                   | Done        | Joan Pesson      |
| Burning Ship sequence (and generalized version)        | Strategy                   | Done        | Théo Brureau     |
| Tricorn sequence (and generalized version)             | Strategy                   | Done        | Marie Tiers      |
| Generic sequences (defined by a lambda expression)     | Strategy                   | Done        | Théo Brureau     |
| Enumeration of the available sequences                 | Strategy                   | Done        | Thibault Breguet |
| Color palette                                          | Strategy                   | Done        | Marie Tiers      |
| Grey scale palette                                     | Strategy                   | Done        | Marie Tiers      |
| Red/Green/Blue/... scale palette                       | Decorator                  | Done        | Marie Tiers      |
| Inverted color palette                                 | Decorator                  | Done        | Joan Pesson      |
| Gradient-based color palette                           | Strategy                   | Done        | Théo Brureau     |
| Predefined color palette                               | Strategy                   | Done        | Théo Brureau     |
| Enumeration of the available palettes                  | Strategy + Decorator       | Done        | Marie Tiers      |
| `BufferedImage` used as an `IFractalImage`             | Adapter                    | Done        | Théo Brureau     |
| Static image generation                                | Strategy                   | Done        | Théo Brureau     |
| Configuration of the fractal generator                 | Strategy + Builder         | Done        | Théo Brureau     |
| Composite of fractal generators (animated GIF)         | Composite                  | Done        | Théo Brureau     |
| Scale-based animated image generation                  | Composite + Builder        | Done        | Thibault Breguet |
| Rotation-based animated image generation               | Composite + Builder        | Done        | Marie Tiers      |
| Command-line interface                                 | Abstract factory + Builder | Done        | Joan Pesson      |

## Team

This project has been developed by:

- Brureau Théo
- Pesson Joan
- Breguet Thibault
- Tiers Marie