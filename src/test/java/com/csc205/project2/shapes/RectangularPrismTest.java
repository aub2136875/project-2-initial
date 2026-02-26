package com.csc205.project2.shapes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive JUnit 5 test suite for the {@link RectangularPrism} class.
 *
 * <p>Test categories covered:</p>
 * <ul>
 *   <li><b>Construction</b>         – all three constructors and field defaults</li>
 *   <li><b>Getters / Setters</b>    – round-trip accuracy and validation guards</li>
 *   <li><b>Calculation Accuracy</b> – volume, surface area, face areas, space diagonal,
 *                                      {@code isCube()} predicate</li>
 *   <li><b>Boundary Testing</b>     – extreme dimension values</li>
 *   <li><b>Input Validation</b>     – zero, negative, null, blank, oversized strings</li>
 *   <li><b>Inheritance</b>          – polymorphic behaviour, equals, hashCode, toString</li>
 * </ul>
 *
 * @author  Your Name
 * @version 1.0
 * @see     RectangularPrism
 */
@DisplayName("RectangularPrism Tests")
class RectangularPrismTest {

    private static final double DELTA       = 1e-9;
    private static final double DELTA_LARGE = 1e-3;

    private RectangularPrism unitPrism;    // 1 × 1 × 1
    private RectangularPrism box2x3x4;    // 2 × 3 × 4
    private RectangularPrism named;       // "Crate", "Brown", 5 × 10 × 2

    @BeforeEach
    void setUp() {
        unitPrism = new RectangularPrism();
        box2x3x4  = new RectangularPrism("Box2x3x4", "Orange", 2.0, 3.0, 4.0);
        named     = new RectangularPrism("Crate", "Brown", 5.0, 10.0, 2.0);
    }

    // =======================================================================
    // 1. CONSTRUCTION TESTS
    // =======================================================================

    @Nested
    @DisplayName("1. Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("No-arg: name=Unknown, color=Unspecified, all dims=1")
        void noArgDefaults() {
            assertAll(
                    () -> assertEquals("Unknown",     unitPrism.getName()),
                    () -> assertEquals("Unspecified", unitPrism.getColor()),
                    () -> assertEquals(1.0, unitPrism.getLength(), DELTA),
                    () -> assertEquals(1.0, unitPrism.getWidth(),  DELTA),
                    () -> assertEquals(1.0, unitPrism.getHeight(), DELTA)
            );
        }

        @Test
        @DisplayName("Four-arg (name, l, w, h) sets color to Unspecified")
        void fourArgNoColor() {
            RectangularPrism p = new RectangularPrism("Box", 3.0, 4.0, 5.0);
            assertEquals("Unspecified", p.getColor());
            assertEquals(3.0, p.getLength(), DELTA);
            assertEquals(4.0, p.getWidth(),  DELTA);
            assertEquals(5.0, p.getHeight(), DELTA);
        }

        @Test
        @DisplayName("Five-arg constructor stores all fields")
        void fiveArgConstructor() {
            assertEquals("Box2x3x4", box2x3x4.getName());
            assertEquals("Orange",   box2x3x4.getColor());
            assertEquals(2.0,        box2x3x4.getLength(), DELTA);
            assertEquals(3.0,        box2x3x4.getWidth(),  DELTA);
            assertEquals(4.0,        box2x3x4.getHeight(), DELTA);
        }

        @Test
        @DisplayName("Constructor trims whitespace from name and color")
        void constructorTrims() {
            RectangularPrism p = new RectangularPrism("  Box  ", "  Red  ", 1.0, 2.0, 3.0);
            assertEquals("Box", p.getName());
            assertEquals("Red", p.getColor());
        }

        @Test
        @DisplayName("Zero length throws IllegalArgumentException")
        void zeroLengthThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism("P", "Red", 0.0, 2.0, 3.0));
        }

        @Test
        @DisplayName("Zero width throws IllegalArgumentException")
        void zeroWidthThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism("P", "Red", 1.0, 0.0, 3.0));
        }

        @Test
        @DisplayName("Zero height throws IllegalArgumentException")
        void zeroHeightThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism("P", "Red", 1.0, 2.0, 0.0));
        }

        @Test
        @DisplayName("Negative length throws IllegalArgumentException")
        void negativeLengthThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism("P", "Red", -1.0, 2.0, 3.0));
        }

        @Test
        @DisplayName("Null name throws NullPointerException")
        void nullNameThrows() {
            assertThrows(NullPointerException.class,
                    () -> new RectangularPrism(null, "Red", 1.0, 2.0, 3.0));
        }

        @Test
        @DisplayName("Null color throws NullPointerException")
        void nullColorThrows() {
            assertThrows(NullPointerException.class,
                    () -> new RectangularPrism("P", null, 1.0, 2.0, 3.0));
        }
    }

    // =======================================================================
    // 2. GETTER / SETTER TESTS
    // =======================================================================

    @Nested
    @DisplayName("2. Getter and Setter Tests")
    class GetterSetterTests {

        @Test
        @DisplayName("setLength updates correctly")
        void setLengthUpdates() {
            box2x3x4.setLength(8.0);
            assertEquals(8.0, box2x3x4.getLength(), DELTA);
        }

        @Test
        @DisplayName("setWidth updates correctly")
        void setWidthUpdates() {
            box2x3x4.setWidth(9.0);
            assertEquals(9.0, box2x3x4.getWidth(), DELTA);
        }

        @Test
        @DisplayName("setHeight updates correctly")
        void setHeightUpdates() {
            box2x3x4.setHeight(6.0);
            assertEquals(6.0, box2x3x4.getHeight(), DELTA);
        }

        @Test
        @DisplayName("setLength zero throws")
        void setLengthZero() {
            assertThrows(IllegalArgumentException.class,
                    () -> box2x3x4.setLength(0.0));
        }

        @Test
        @DisplayName("setWidth negative throws")
        void setWidthNegative() {
            assertThrows(IllegalArgumentException.class,
                    () -> box2x3x4.setWidth(-3.0));
        }

        @Test
        @DisplayName("setHeight negative throws")
        void setHeightNegative() {
            assertThrows(IllegalArgumentException.class,
                    () -> box2x3x4.setHeight(-1.0));
        }

        @Test
        @DisplayName("setName null throws NullPointerException")
        void setNameNull() {
            assertThrows(NullPointerException.class,
                    () -> box2x3x4.setName(null));
        }

        @Test
        @DisplayName("setColor blank throws IllegalArgumentException")
        void setColorBlank() {
            assertThrows(IllegalArgumentException.class,
                    () -> box2x3x4.setColor("  "));
        }
    }

    // =======================================================================
    // 3. CALCULATION ACCURACY TESTS
    // =======================================================================

    @Nested
    @DisplayName("3. Calculation Accuracy Tests")
    class CalculationTests {

        @Test
        @DisplayName("Unit prism volume = 1.0")
        void unitVolume() {
            assertEquals(1.0, unitPrism.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Unit prism surface area = 6.0")
        void unitSurfaceArea() {
            assertEquals(6.0, unitPrism.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("2×3×4 volume = 24")
        void box2x3x4Volume() {
            assertEquals(24.0, box2x3x4.getVolume(), DELTA);
        }

        @Test
        @DisplayName("2×3×4 surface area = 2*(6+8+12) = 52")
        void box2x3x4SurfaceArea() {
            // 2*(l*w + l*h + w*h) = 2*(6+8+12) = 52
            assertEquals(52.0, box2x3x4.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Base area = l * w")
        void baseArea() {
            assertEquals(6.0, box2x3x4.getBaseArea(), DELTA);
        }

        @Test
        @DisplayName("Front face area = l * h")
        void frontFaceArea() {
            assertEquals(8.0, box2x3x4.getFrontFaceArea(), DELTA);
        }

        @Test
        @DisplayName("Side face area = w * h")
        void sideFaceArea() {
            assertEquals(12.0, box2x3x4.getSideFaceArea(), DELTA);
        }

        @Test
        @DisplayName("Surface area = 2*(base + front + side) face areas")
        void surfaceAreaFromFaces() {
            double expected = 2.0 * (box2x3x4.getBaseArea()
                                   + box2x3x4.getFrontFaceArea()
                                   + box2x3x4.getSideFaceArea());
            assertEquals(expected, box2x3x4.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Space diagonal = √(l²+w²+h²)")
        void spaceDiagonal() {
            double expected = Math.sqrt(4.0 + 9.0 + 16.0);
            assertEquals(expected, box2x3x4.getSpaceDiagonal(), DELTA);
        }

        @Test
        @DisplayName("isCube() returns true for a cube (all dims equal)")
        void isCubeTrue() {
            RectangularPrism cube = new RectangularPrism("C", "Red", 3.0, 3.0, 3.0);
            assertTrue(cube.isCube());
        }

        @Test
        @DisplayName("isCube() returns false for non-cube prism")
        void isCubeFalse() {
            assertFalse(box2x3x4.isCube());
        }

        @Test
        @DisplayName("isCube() false when only two dims equal")
        void isCubeFalsePartialMatch() {
            RectangularPrism p = new RectangularPrism("P", "Red", 3.0, 3.0, 5.0);
            assertFalse(p.isCube());
        }

        @ParameterizedTest(name = "l={0}, w={1}, h={2} → volume = l*w*h")
        @CsvSource({"1.0,1.0,1.0", "2.0,3.0,4.0", "5.5,2.0,3.0", "10.0,10.0,10.0"})
        @DisplayName("Parameterized volume = l*w*h")
        void paramVolume(double l, double w, double h) {
            RectangularPrism p = new RectangularPrism("P", "Red", l, w, h);
            assertEquals(l * w * h, p.getVolume(), DELTA);
        }
    }

    // =======================================================================
    // 4. BOUNDARY TESTS
    // =======================================================================

    @Nested
    @DisplayName("4. Boundary Tests")
    class BoundaryTests {

        @Test
        @DisplayName("Very small dimensions (1e-10 each) produce non-negative volume")
        void verySmallDimensions() {
            RectangularPrism p = new RectangularPrism("P", "Red", 1e-10, 1e-10, 1e-10);
            assertTrue(p.getVolume() >= 0);
            assertFalse(Double.isNaN(p.getVolume()));
        }

        @Test
        @DisplayName("Very large dimensions (1e7 each) produce finite volume")
        void veryLargeDimensions() {
            RectangularPrism p = new RectangularPrism("P", "Red", 1e7, 1e7, 1e7);
            assertTrue(Double.isFinite(p.getVolume()));
        }

        @Test
        @DisplayName("Double.MIN_VALUE for all dims accepted")
        void minPositiveDouble() {
            double m = Double.MIN_VALUE;
            assertDoesNotThrow(() ->
                    new RectangularPrism("P", "Red", m, m, m));
        }

        @ParameterizedTest(name = "dim = {0} is invalid")
        @ValueSource(doubles = {0.0, -1.0, -100.0})
        @DisplayName("Parameterized: invalid length values rejected")
        void invalidLengths(double d) {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism("P", "Red", d, 1.0, 1.0));
        }

        @Test
        @DisplayName("NaN length throws IllegalArgumentException")
        void nanLength() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism("P", "Red", Double.NaN, 1.0, 1.0));
        }

        @Test
        @DisplayName("Positive infinity length throws IllegalArgumentException")
        void posInfLength() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism("P", "Red",
                            Double.POSITIVE_INFINITY, 1.0, 1.0));
        }
    }

    // =======================================================================
    // 5. INPUT VALIDATION TESTS
    // =======================================================================

    @Nested
    @DisplayName("5. Input Validation Tests")
    class InputValidationTests {

        @Test
        @DisplayName("Name > 100 chars throws")
        void nameTooLong() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism("A".repeat(101), "Red", 1.0, 1.0, 1.0));
        }

        @Test
        @DisplayName("Name exactly 100 chars accepted")
        void nameMaxLength() {
            assertDoesNotThrow(() ->
                    new RectangularPrism("A".repeat(100), "Red", 1.0, 1.0, 1.0));
        }

        @Test
        @DisplayName("Color > 100 chars throws")
        void colorTooLong() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism("P", "B".repeat(101), 1.0, 1.0, 1.0));
        }

        @Test
        @DisplayName("Blank name (whitespace) throws")
        void blankName() {
            assertThrows(IllegalArgumentException.class,
                    () -> new RectangularPrism("   ", "Red", 1.0, 1.0, 1.0));
        }
    }

    // =======================================================================
    // 6. INHERITANCE / POLYMORPHISM TESTS
    // =======================================================================

    @Nested
    @DisplayName("6. Inheritance and Polymorphism Tests")
    class InheritanceTests {

        @Test
        @DisplayName("RectangularPrism is instance of Shape3D")
        void isShape3D() {
            assertInstanceOf(Shape3D.class, box2x3x4);
        }

        @Test
        @DisplayName("RectangularPrism is instance of ThreeDimensionalShape")
        void isInterface() {
            assertInstanceOf(ThreeDimensionalShape.class, box2x3x4);
        }

        @Test
        @DisplayName("Shape3D reference calls overridden getVolume()")
        void polymorphicVolume() {
            Shape3D shape = new RectangularPrism("P", "Red", 2.0, 3.0, 4.0);
            assertEquals(24.0, shape.getVolume(), DELTA);
        }

        @Test
        @DisplayName("ThreeDimensionalShape reference calls overridden getSurfaceArea()")
        void polymorphicSurfaceArea() {
            ThreeDimensionalShape shape =
                    new RectangularPrism("P", "Red", 2.0, 3.0, 4.0);
            assertEquals(52.0, shape.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("describeVolume() contains 'Volume'")
        void describeVolume() {
            assertTrue(box2x3x4.describeVolume().contains("Volume"));
        }

        @Test
        @DisplayName("describeSurfaceArea() contains 'Surface'")
        void describeSurface() {
            assertTrue(box2x3x4.describeSurfaceArea().contains("Surface"));
        }

        @Test
        @DisplayName("equals() true for same name, color, all three dims")
        void equalsTrue() {
            RectangularPrism a = new RectangularPrism("P", "Red", 2.0, 3.0, 4.0);
            RectangularPrism b = new RectangularPrism("P", "Red", 2.0, 3.0, 4.0);
            assertEquals(a, b);
        }

        @Test
        @DisplayName("equals() false when width differs")
        void equalsFalseDiffWidth() {
            RectangularPrism a = new RectangularPrism("P", "Red", 2.0, 3.0, 4.0);
            RectangularPrism b = new RectangularPrism("P", "Red", 2.0, 5.0, 4.0);
            assertNotEquals(a, b);
        }

        @Test
        @DisplayName("hashCode() consistent with equals()")
        void hashCodeConsistent() {
            RectangularPrism a = new RectangularPrism("P", "Red", 2.0, 3.0, 4.0);
            RectangularPrism b = new RectangularPrism("P", "Red", 2.0, 3.0, 4.0);
            assertEquals(a.hashCode(), b.hashCode());
        }

        @Test
        @DisplayName("toString() contains 'RECTANGULAR PRISM', name, color")
        void toStringContent() {
            String s = named.toString();
            assertAll(
                    () -> assertTrue(s.contains("RECTANGULAR PRISM"), "header"),
                    () -> assertTrue(s.contains("Crate"),             "name"),
                    () -> assertTrue(s.contains("Brown"),             "color")
            );
        }

        @Test
        @DisplayName("RectangularPrism not equal to Cube with same-dimension side")
        void notEqualToCube() {
            RectangularPrism rp = new RectangularPrism("C", "Red", 3.0, 3.0, 3.0);
            Cube cube = new Cube("C", "Red", 3.0);
            assertNotEquals(rp, cube);
        }
    }
}