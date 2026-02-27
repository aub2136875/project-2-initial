package com.csc205.project2.shapes;
/**
 * AI GENERATION DOCUMENTATION
 * ===========================
 * AI Tool Used: [Claude Sonnet 4.6]
 * Generation Date: [2/21/26]
 *
 * Original Prompt:
 * "[For each shape class, generate JUnit 5 test classes that include: basic functionality including constructor, getters, and setters, Calculation Accuracy for volume and surface area with known values, Boundary Testing for zero values and very small or large numbers, Input Validation for negative values and null inputs, and Inheritance testing with polymorphic behavior verification.]"
 *
 * Follow-up Prompts (if any):
 * 1. "[Here are the issues I got while running the test classes (I copied and pasted the issues), how would I go about fixing these?]"
 *
 * Manual Modifications:
 * - [The solution to the issue above was to replace the shape classes I was using with updated shape classes.]
 * - [The reason I made those manual modification is because it was advised by the AI in order to fix the problems within the test classes. Here is the AI Response: "The fix is to replace every <= 0 guard with a check that explicitly requires the value to be a finite positive number. This needs to be applied to every setter in all five shape classes."]
 */
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive JUnit 5 test suite for the {@link Cube} class.
 *
 * <p>Test categories covered:</p>
 * <ul>
 *   <li><b>Construction</b>         – all three constructors and field defaults</li>
 *   <li><b>Getters / Setters</b>    – round-trip accuracy and validation guards</li>
 *   <li><b>Calculation Accuracy</b> – volume, surface area, face area, diagonals</li>
 *   <li><b>Boundary Testing</b>     – very small / very large side lengths</li>
 *   <li><b>Input Validation</b>     – zero, negative, null, blank, oversized strings</li>
 *   <li><b>Inheritance</b>          – polymorphic behaviour, equals, hashCode, toString</li>
 * </ul>
 *
 * @author  Your Name
 * @version 1.0
 * @see     Cube
 */
@DisplayName("Cube Tests")
class CubeTest {

    private static final double DELTA       = 1e-9;
    private static final double DELTA_LARGE = 1e-3;

    private Cube unitCube;
    private Cube cube4;
    private Cube namedCube;

    @BeforeEach
    void setUp() {
        unitCube  = new Cube();
        cube4     = new Cube("Cube4", "Blue", 4.0);
        namedCube = new Cube("TestCube", "Green", 6.0);
    }

    // =======================================================================
    // 1. CONSTRUCTION TESTS
    // =======================================================================

    @Nested
    @DisplayName("1. Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("No-arg constructor: name=Unknown, color=Unspecified, side=1")
        void noArgDefaults() {
            assertEquals("Unknown",     unitCube.getName());
            assertEquals("Unspecified", unitCube.getColor());
            assertEquals(1.0,           unitCube.getSideLength(), DELTA);
        }

        @Test
        @DisplayName("Two-arg constructor (name, side) sets color to Unspecified")
        void twoArgConstructor() {
            Cube c = new Cube("MyCube", 3.0);
            assertEquals("MyCube",      c.getName());
            assertEquals("Unspecified", c.getColor());
            assertEquals(3.0,           c.getSideLength(), DELTA);
        }

        @Test
        @DisplayName("Three-arg constructor stores all fields correctly")
        void threeArgConstructor() {
            assertEquals("Cube4", cube4.getName());
            assertEquals("Blue",  cube4.getColor());
            assertEquals(4.0,     cube4.getSideLength(), DELTA);
        }

        @Test
        @DisplayName("Constructor trims whitespace in name and color")
        void constructorTrims() {
            Cube c = new Cube("  Box  ", "  White  ", 2.0);
            assertEquals("Box",   c.getName());
            assertEquals("White", c.getColor());
        }

        @Test
        @DisplayName("Zero side length throws IllegalArgumentException")
        void zeroSideThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cube("C", "Red", 0.0));
        }

        @Test
        @DisplayName("Negative side length throws IllegalArgumentException")
        void negativeSideThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cube("C", "Red", -3.0));
        }

        @Test
        @DisplayName("Null name throws NullPointerException")
        void nullNameThrows() {
            assertThrows(NullPointerException.class,
                    () -> new Cube(null, "Red", 3.0));
        }

        @Test
        @DisplayName("Null color throws NullPointerException")
        void nullColorThrows() {
            assertThrows(NullPointerException.class,
                    () -> new Cube("C", null, 3.0));
        }
    }

    // =======================================================================
    // 2. GETTER / SETTER TESTS
    // =======================================================================

    @Nested
    @DisplayName("2. Getter and Setter Tests")
    class GetterSetterTests {

        @Test
        @DisplayName("setSideLength updates correctly")
        void setSideLengthUpdates() {
            cube4.setSideLength(8.0);
            assertEquals(8.0, cube4.getSideLength(), DELTA);
        }

        @Test
        @DisplayName("setSideLength with zero throws IllegalArgumentException")
        void setSideLengthZeroThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> cube4.setSideLength(0.0));
        }

        @Test
        @DisplayName("setSideLength with negative throws IllegalArgumentException")
        void setSideLengthNegativeThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> cube4.setSideLength(-1.0));
        }

        @Test
        @DisplayName("setName with null throws NullPointerException")
        void setNameNullThrows() {
            assertThrows(NullPointerException.class,
                    () -> cube4.setName(null));
        }

        @Test
        @DisplayName("setColor with blank throws IllegalArgumentException")
        void setColorBlankThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> cube4.setColor("   "));
        }

        @Test
        @DisplayName("setName trims before storing")
        void setNameTrims() {
            cube4.setName("  IceCube  ");
            assertEquals("IceCube", cube4.getName());
        }
    }

    // =======================================================================
    // 3. CALCULATION ACCURACY TESTS
    // =======================================================================

    @Nested
    @DisplayName("3. Calculation Accuracy Tests")
    class CalculationTests {

        @Test
        @DisplayName("Unit cube volume = 1.0")
        void unitCubeVolume() {
            assertEquals(1.0, unitCube.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Unit cube surface area = 6.0")
        void unitCubeSurfaceArea() {
            assertEquals(6.0, unitCube.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Cube s=4: volume = 64")
        void cube4Volume() {
            assertEquals(64.0, cube4.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Cube s=4: surface area = 96")
        void cube4SurfaceArea() {
            assertEquals(96.0, cube4.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Face area = s²")
        void faceArea() {
            assertEquals(16.0, cube4.getFaceArea(), DELTA);
        }

        @Test
        @DisplayName("Face diagonal = s * √2")
        void faceDiagonal() {
            double expected = 4.0 * Math.sqrt(2.0);
            assertEquals(expected, cube4.getFaceDiagonal(), DELTA);
        }

        @Test
        @DisplayName("Space diagonal = s * √3")
        void spaceDiagonal() {
            double expected = 4.0 * Math.sqrt(3.0);
            assertEquals(expected, cube4.getSpaceDiagonal(), DELTA);
        }

        @Test
        @DisplayName("Surface area = 6 * face area")
        void surfaceArea6TimesFaceArea() {
            assertEquals(6.0 * cube4.getFaceArea(), cube4.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Volume scales as cube of side (s=3 gives 27x unit volume)")
        void volumeScalesCubically() {
            Cube c3 = new Cube("C", "Red", 3.0);
            assertEquals(27.0 * unitCube.getVolume(), c3.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Surface area scales as square of side (s=2 gives 4x unit SA)")
        void surfaceAreaScalesSquarely() {
            Cube c2 = new Cube("C", "Red", 2.0);
            assertEquals(4.0 * unitCube.getSurfaceArea(), c2.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Space diagonal > face diagonal > side length")
        void diagonalOrdering() {
            assertTrue(cube4.getSpaceDiagonal() > cube4.getFaceDiagonal());
            assertTrue(cube4.getFaceDiagonal()  > cube4.getSideLength());
        }
    }

    // =======================================================================
    // 4. BOUNDARY TESTS
    // =======================================================================

    @Nested
    @DisplayName("4. Boundary Tests")
    class BoundaryTests {

        @Test
        @DisplayName("Very small side (1e-10) produces non-negative volume")
        void verySmallSide() {
            Cube tiny = new Cube("T", "Red", 1e-10);
            assertTrue(tiny.getVolume() >= 0);
            assertFalse(Double.isNaN(tiny.getVolume()));
        }

        @Test
        @DisplayName("Very large side (1e8) produces finite volume")
        void veryLargeSide() {
            Cube huge = new Cube("H", "Red", 1e8);
            assertTrue(Double.isFinite(huge.getVolume()));
        }

        @Test
        @DisplayName("Double.MIN_VALUE side length is accepted")
        void minPositiveDouble() {
            assertDoesNotThrow(() -> new Cube("C", "Red", Double.MIN_VALUE));
        }

        @ParameterizedTest(name = "side = {0} is valid")
        @ValueSource(doubles = {0.001, 0.5, 1.0, 50.0, 1_000.0})
        @DisplayName("Parameterized: various positive sides accepted")
        void parameterizedValidSides(double s) {
            assertDoesNotThrow(() -> new Cube("C", "Red", s));
        }

        @ParameterizedTest(name = "side = {0} is invalid")
        @ValueSource(doubles = {0.0, -0.001, -10.0})
        @DisplayName("Parameterized: zero / negative sides rejected")
        void parameterizedInvalidSides(double s) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cube("C", "Red", s));
        }

        @Test
        @DisplayName("NaN side throws IllegalArgumentException")
        void nanSideThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cube("C", "Red", Double.NaN));
        }
    }

    // =======================================================================
    // 5. INPUT VALIDATION TESTS
    // =======================================================================

    @Nested
    @DisplayName("5. Input Validation Tests")
    class InputValidationTests {

        @Test
        @DisplayName("Name over 100 chars throws IllegalArgumentException")
        void nameTooLong() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cube("A".repeat(101), "Red", 1.0));
        }

        @Test
        @DisplayName("Name exactly 100 chars is accepted")
        void nameMaxLength() {
            assertDoesNotThrow(() -> new Cube("A".repeat(100), "Red", 1.0));
        }

        @Test
        @DisplayName("Color over 100 chars throws IllegalArgumentException")
        void colorTooLong() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cube("C", "B".repeat(101), 1.0));
        }

        @Test
        @DisplayName("Blank name (spaces only) throws IllegalArgumentException")
        void blankName() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cube("   ", "Red", 1.0));
        }

        @Test
        @DisplayName("Negative infinity side throws IllegalArgumentException")
        void negativeInfinitySide() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cube("C", "Red", Double.NEGATIVE_INFINITY));
        }
    }

    // =======================================================================
    // 6. INHERITANCE / POLYMORPHISM TESTS
    // =======================================================================

    @Nested
    @DisplayName("6. Inheritance and Polymorphism Tests")
    class InheritanceTests {

        @Test
        @DisplayName("Cube is an instance of Shape3D")
        void isShape3D() {
            assertInstanceOf(Shape3D.class, cube4);
        }

        @Test
        @DisplayName("Cube is an instance of ThreeDimensionalShape")
        void isThreeDimensionalShape() {
            assertInstanceOf(ThreeDimensionalShape.class, cube4);
        }

        @Test
        @DisplayName("Polymorphic Shape3D reference calls overridden getVolume()")
        void polymorphicVolume() {
            Shape3D shape = new Cube("C", "Red", 4.0);
            assertEquals(64.0, shape.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Polymorphic ThreeDimensionalShape reference calls getVolume()")
        void polymorphicInterface() {
            ThreeDimensionalShape shape = new Cube("C", "Red", 3.0);
            assertEquals(27.0, shape.getVolume(), DELTA);
        }

        @Test
        @DisplayName("describeVolume() contains 'Volume'")
        void describeVolume() {
            assertTrue(cube4.describeVolume().contains("Volume"));
        }

        @Test
        @DisplayName("describeSurfaceArea() contains 'Surface'")
        void describeSurfaceArea() {
            assertTrue(cube4.describeSurfaceArea().contains("Surface"));
        }

        @Test
        @DisplayName("equals() true for same name, color, side")
        void equalsTrue() {
            Cube a = new Cube("C", "Red", 4.0);
            Cube b = new Cube("C", "Red", 4.0);
            assertEquals(a, b);
        }

        @Test
        @DisplayName("equals() false when side length differs")
        void equalsFalseDifferentSide() {
            Cube a = new Cube("C", "Red", 4.0);
            Cube b = new Cube("C", "Red", 5.0);
            assertNotEquals(a, b);
        }

        @Test
        @DisplayName("hashCode() matches for equal cubes")
        void hashCodeConsistent() {
            Cube a = new Cube("C", "Red", 4.0);
            Cube b = new Cube("C", "Red", 4.0);
            assertEquals(a.hashCode(), b.hashCode());
        }

        @Test
        @DisplayName("toString() contains 'CUBE' and field values")
        void toStringContent() {
            String s = cube4.toString();
            assertAll(
                    () -> assertTrue(s.contains("CUBE"),  "should contain CUBE"),
                    () -> assertTrue(s.contains("Cube4"), "should contain name"),
                    () -> assertTrue(s.contains("Blue"),  "should contain color")
            );
        }
    }
}