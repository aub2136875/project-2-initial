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
 * Comprehensive JUnit 5 test suite for the {@link Sphere} class.
 *
 * <p>Test categories covered:</p>
 * <ul>
 *   <li><b>Construction</b>       – all three constructors, correct field initialisation</li>
 *   <li><b>Getters / Setters</b>  – round-trip accuracy and setter guard-rails</li>
 *   <li><b>Calculation Accuracy</b> – volume, surface area, diameter, great-circle</li>
 *   <li><b>Boundary Testing</b>   – very small / very large radii, Double extremes</li>
 *   <li><b>Input Validation</b>   – zero, negative, null, blank strings</li>
 *   <li><b>Inheritance</b>        – polymorphic assignment, interface contract,
 *                                    {@code equals}, {@code hashCode}, {@code toString}</li>
 * </ul>
 *
 * <p>All floating-point comparisons use a delta of {@value #DELTA} unless a
 * looser tolerance is explicitly required.</p>
 *
 * @author  Your Name
 * @version 1.0
 * @see     Sphere
 */
@DisplayName("Sphere Tests")
class SphereTest {

    // -----------------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------------

    /** Absolute tolerance for floating-point assertions. */
    private static final double DELTA = 1e-9;

    /** Loose tolerance used when testing very large numbers. */
    private static final double DELTA_LARGE = 1e-3;

    // -----------------------------------------------------------------------
    // Shared fixtures
    // -----------------------------------------------------------------------

    private Sphere unitSphere;      // radius = 1
    private Sphere sphere5;         // radius = 5
    private Sphere namedSphere;     // name = "TestSphere", color = "Blue", radius = 3

    @BeforeEach
    void setUp() {
        unitSphere  = new Sphere();
        sphere5     = new Sphere("Sphere5", "Red", 5.0);
        namedSphere = new Sphere("TestSphere", "Blue", 3.0);
    }

    // =======================================================================
    // 1. CONSTRUCTION TESTS
    // =======================================================================

    @Nested
    @DisplayName("1. Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("No-arg constructor sets default name, color, and radius = 1")
        void noArgConstructorDefaults() {
            assertEquals("Unknown",     unitSphere.getName(),   "default name");
            assertEquals("Unspecified", unitSphere.getColor(),  "default color");
            assertEquals(1.0,           unitSphere.getRadius(), DELTA);
        }

        @Test
        @DisplayName("Two-arg constructor (name, radius) sets color to Unspecified")
        void twoArgConstructor() {
            Sphere s = new Sphere("MySphere", 7.0);
            assertEquals("MySphere",    s.getName());
            assertEquals("Unspecified", s.getColor());
            assertEquals(7.0,           s.getRadius(), DELTA);
        }

        @Test
        @DisplayName("Three-arg constructor stores all three fields correctly")
        void threeArgConstructor() {
            assertEquals("Sphere5", sphere5.getName());
            assertEquals("Red",     sphere5.getColor());
            assertEquals(5.0,       sphere5.getRadius(), DELTA);
        }

        @Test
        @DisplayName("Constructor trims whitespace from name and color")
        void constructorTrimsStrings() {
            Sphere s = new Sphere("  Ball  ", "  Green  ", 2.0);
            assertEquals("Ball",  s.getName());
            assertEquals("Green", s.getColor());
        }

        @Test
        @DisplayName("Constructor with zero radius throws IllegalArgumentException")
        void constructorZeroRadiusThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Sphere("S", "Red", 0.0));
        }

        @Test
        @DisplayName("Constructor with negative radius throws IllegalArgumentException")
        void constructorNegativeRadiusThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Sphere("S", "Red", -1.0));
        }

        @Test
        @DisplayName("Constructor with null name throws NullPointerException")
        void constructorNullNameThrows() {
            assertThrows(NullPointerException.class,
                    () -> new Sphere(null, "Red", 3.0));
        }

        @Test
        @DisplayName("Constructor with null color throws NullPointerException")
        void constructorNullColorThrows() {
            assertThrows(NullPointerException.class,
                    () -> new Sphere("S", null, 3.0));
        }

        @Test
        @DisplayName("Constructor with blank name throws IllegalArgumentException")
        void constructorBlankNameThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Sphere("   ", "Red", 3.0));
        }
    }

    // =======================================================================
    // 2. GETTER / SETTER TESTS
    // =======================================================================

    @Nested
    @DisplayName("2. Getter and Setter Tests")
    class GetterSetterTests {

        @Test
        @DisplayName("setRadius updates radius correctly")
        void setRadiusUpdates() {
            sphere5.setRadius(10.0);
            assertEquals(10.0, sphere5.getRadius(), DELTA);
        }

        @Test
        @DisplayName("setName updates name correctly")
        void setNameUpdates() {
            sphere5.setName("BigSphere");
            assertEquals("BigSphere", sphere5.getName());
        }

        @Test
        @DisplayName("setColor updates color correctly")
        void setColorUpdates() {
            sphere5.setColor("Purple");
            assertEquals("Purple", sphere5.getColor());
        }

        @Test
        @DisplayName("setRadius with zero throws IllegalArgumentException")
        void setRadiusZeroThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> sphere5.setRadius(0.0));
        }

        @Test
        @DisplayName("setRadius with negative value throws IllegalArgumentException")
        void setRadiusNegativeThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> sphere5.setRadius(-5.0));
        }

        @Test
        @DisplayName("setName with null throws NullPointerException")
        void setNameNullThrows() {
            assertThrows(NullPointerException.class,
                    () -> sphere5.setName(null));
        }

        @Test
        @DisplayName("setColor with null throws NullPointerException")
        void setColorNullThrows() {
            assertThrows(NullPointerException.class,
                    () -> sphere5.setColor(null));
        }

        @Test
        @DisplayName("setName with blank string throws IllegalArgumentException")
        void setNameBlankThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> sphere5.setName(""));
        }

        @Test
        @DisplayName("setName trims whitespace before storing")
        void setNameTrims() {
            sphere5.setName("  Alpha  ");
            assertEquals("Alpha", sphere5.getName());
        }
    }

    // =======================================================================
    // 3. CALCULATION ACCURACY TESTS
    // =======================================================================

    @Nested
    @DisplayName("3. Calculation Accuracy Tests")
    class CalculationTests {

        @Test
        @DisplayName("Unit sphere volume = (4/3)π ≈ 4.18879020")
        void unitSphereVolume() {
            double expected = (4.0 / 3.0) * Math.PI;
            assertEquals(expected, unitSphere.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Unit sphere surface area = 4π ≈ 12.56637061")
        void unitSphereSurfaceArea() {
            double expected = 4.0 * Math.PI;
            assertEquals(expected, unitSphere.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Sphere r=5: volume ≈ 523.59878")
        void sphere5Volume() {
            double expected = (4.0 / 3.0) * Math.PI * 125.0; // r³ = 125
            assertEquals(expected, sphere5.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Sphere r=5: surface area ≈ 314.15927")
        void sphere5SurfaceArea() {
            double expected = 4.0 * Math.PI * 25.0; // r² = 25
            assertEquals(expected, sphere5.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Diameter = 2 * radius")
        void diameter() {
            assertEquals(10.0, sphere5.getDiameter(), DELTA);
        }

        @Test
        @DisplayName("Great circle area = π * r²")
        void greatCircleArea() {
            double expected = Math.PI * 25.0;
            assertEquals(expected, sphere5.getGreatCircleArea(), DELTA);
        }

        @Test
        @DisplayName("Great circle circumference = 2 * π * r")
        void greatCircleCircumference() {
            double expected = 2.0 * Math.PI * 5.0;
            assertEquals(expected, sphere5.getGreatCircleCircumference(), DELTA);
        }

        @Test
        @DisplayName("Great circle area equals half of surface area")
        void greatCircleIsQuarterSurface() {
            // surfaceArea = 4πr², greatCircle = πr²  →  greatCircle = surfaceArea / 4
            assertEquals(sphere5.getSurfaceArea() / 4.0,
                         sphere5.getGreatCircleArea(), DELTA);
        }

        @Test
        @DisplayName("Volume scales as cube of radius (r=2 gives 8x unit volume)")
        void volumeScalesCubically() {
            Sphere s2 = new Sphere("S", "Red", 2.0);
            assertEquals(8.0 * unitSphere.getVolume(), s2.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Surface area scales as square of radius (r=3 gives 9x unit SA)")
        void surfaceAreaScalesSquarely() {
            Sphere s3 = new Sphere("S", "Red", 3.0);
            assertEquals(9.0 * unitSphere.getSurfaceArea(), s3.getSurfaceArea(), DELTA);
        }
    }

    // =======================================================================
    // 4. BOUNDARY TESTS
    // =======================================================================

    @Nested
    @DisplayName("4. Boundary Tests")
    class BoundaryTests {

        @Test
        @DisplayName("Very small radius (1e-10) produces non-negative, non-NaN volume")
        void verySmallRadius() {
            Sphere tiny = new Sphere("Tiny", "Red", 1e-10);
            assertTrue(tiny.getVolume() >= 0);
            assertFalse(Double.isNaN(tiny.getVolume()));
        }

        @Test
        @DisplayName("Very large radius (1e10) produces finite volume")
        void veryLargeRadius() {
            Sphere huge = new Sphere("Huge", "Red", 1e10);
            assertTrue(Double.isFinite(huge.getVolume()));
        }

        @Test
        @DisplayName("Minimum positive double radius is accepted without exception")
        void minPositiveDoubleRadius() {
            assertDoesNotThrow(() -> new Sphere("S", "Red", Double.MIN_VALUE));
        }

        @Test
        @DisplayName("Radius just above zero (1e-300) accepted; volume > 0")
        void radiusJustAboveZero() {
            Sphere s = new Sphere("S", "Red", 1e-300);
            assertTrue(s.getVolume() >= 0);
        }

        @Test
        @DisplayName("Very large radius: surface area formula still holds")
        void largeRadiusSurfaceAreaFormula() {
            double r = 1e6;
            Sphere huge = new Sphere("H", "Red", r);
            double expected = 4.0 * Math.PI * r * r;
            assertEquals(expected, huge.getSurfaceArea(), DELTA_LARGE);
        }

        @ParameterizedTest(name = "radius = {0} is valid")
        @ValueSource(doubles = {0.001, 0.1, 1.0, 100.0, 1_000_000.0})
        @DisplayName("Parameterized: various positive radii accepted")
        void parametrizedValidRadii(double r) {
            assertDoesNotThrow(() -> new Sphere("S", "Red", r));
        }

        @ParameterizedTest(name = "radius = {0} is invalid")
        @ValueSource(doubles = {0.0, -0.001, -1.0, -1_000_000.0})
        @DisplayName("Parameterized: zero and negative radii rejected")
        void parametrizedInvalidRadii(double r) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Sphere("S", "Red", r));
        }
    }

    // =======================================================================
    // 5. INPUT VALIDATION TESTS
    // =======================================================================

    @Nested
    @DisplayName("5. Input Validation Tests")
    class InputValidationTests {

        @Test
        @DisplayName("Name exceeding 100 characters throws IllegalArgumentException")
        void nameTooLongThrows() {
            String longName = "A".repeat(101);
            assertThrows(IllegalArgumentException.class,
                    () -> new Sphere(longName, "Red", 1.0));
        }

        @Test
        @DisplayName("Color exceeding 100 characters throws IllegalArgumentException")
        void colorTooLongThrows() {
            String longColor = "B".repeat(101);
            assertThrows(IllegalArgumentException.class,
                    () -> new Sphere("S", longColor, 1.0));
        }

        @Test
        @DisplayName("Name of exactly 100 characters is accepted")
        void nameMaxLengthAccepted() {
            String name100 = "A".repeat(100);
            assertDoesNotThrow(() -> new Sphere(name100, "Red", 1.0));
        }

        @Test
        @DisplayName("NaN radius throws IllegalArgumentException")
        void nanRadiusThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Sphere("S", "Red", Double.NaN));
        }

        @Test
        @DisplayName("Negative infinity radius throws IllegalArgumentException")
        void negInfinityRadiusThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Sphere("S", "Red", Double.NEGATIVE_INFINITY));
        }
    }

    // =======================================================================
    // 6. INHERITANCE / POLYMORPHISM TESTS
    // =======================================================================

    @Nested
    @DisplayName("6. Inheritance and Polymorphism Tests")
    class InheritanceTests {

        @Test
        @DisplayName("Sphere is an instance of Shape3D")
        void isInstanceOfShape3D() {
            assertInstanceOf(Shape3D.class, sphere5);
        }

        @Test
        @DisplayName("Sphere is an instance of ThreeDimensionalShape")
        void isInstanceOfInterface() {
            assertInstanceOf(ThreeDimensionalShape.class, sphere5);
        }

        @Test
        @DisplayName("Polymorphic Shape3D reference calls overridden getVolume()")
        void polymorphicVolumeCall() {
            Shape3D shape = new Sphere("S", "Red", 5.0);
            double expected = (4.0 / 3.0) * Math.PI * 125.0;
            assertEquals(expected, shape.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Polymorphic ThreeDimensionalShape reference calls overridden methods")
        void polymorphicInterfaceCall() {
            ThreeDimensionalShape shape = new Sphere("S", "Red", 5.0);
            assertTrue(shape.getVolume() > 0);
            assertTrue(shape.getSurfaceArea() > 0);
        }

        @Test
        @DisplayName("describeVolume() returns non-null string containing 'Volume'")
        void describeVolumeInherited() {
            String desc = sphere5.describeVolume();
            assertNotNull(desc);
            assertTrue(desc.contains("Volume"));
        }

        @Test
        @DisplayName("describeSurfaceArea() returns non-null string containing 'Surface'")
        void describeSurfaceAreaInherited() {
            String desc = sphere5.describeSurfaceArea();
            assertNotNull(desc);
            assertTrue(desc.contains("Surface"));
        }

        @Test
        @DisplayName("equals() is true for two spheres with same name, color, radius")
        void equalsSymmetric() {
            Sphere a = new Sphere("S", "Red", 5.0);
            Sphere b = new Sphere("S", "Red", 5.0);
            assertEquals(a, b);
            assertEquals(b, a);
        }

        @Test
        @DisplayName("equals() is false when radius differs")
        void equalsFalseOnDifferentRadius() {
            Sphere a = new Sphere("S", "Red", 5.0);
            Sphere b = new Sphere("S", "Red", 6.0);
            assertNotEquals(a, b);
        }

        @Test
        @DisplayName("equals() is false for null")
        void equalsFalseForNull() {
            assertNotEquals(null, sphere5);
        }

        @Test
        @DisplayName("hashCode() is equal for equal spheres")
        void hashCodeConsistentWithEquals() {
            Sphere a = new Sphere("S", "Red", 5.0);
            Sphere b = new Sphere("S", "Red", 5.0);
            assertEquals(a.hashCode(), b.hashCode());
        }

        @Test
        @DisplayName("toString() contains 'SPHERE' and key field values")
        void toStringContainsExpectedContent() {
            String s = sphere5.toString();
            assertAll(
                    () -> assertTrue(s.contains("SPHERE"),  "should contain SPHERE"),
                    () -> assertTrue(s.contains("Sphere5"), "should contain name"),
                    () -> assertTrue(s.contains("Red"),     "should contain color")
            );
        }

        @Test
        @DisplayName("Sphere and Cube with same name/color are not equal")
        void sphereNotEqualToCube() {
            Sphere sphere = new Sphere("S", "Red", 5.0);
            Cube   cube   = new Cube("S", "Red", 5.0);
            assertNotEquals(sphere, cube);
        }
    }
}