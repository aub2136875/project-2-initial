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
 * Comprehensive JUnit 5 test suite for the {@link Cylinder} class.
 *
 * <p>Test categories covered:</p>
 * <ul>
 *   <li><b>Construction</b>         – all three constructors and field defaults</li>
 *   <li><b>Getters / Setters</b>    – round-trip accuracy and validation guards</li>
 *   <li><b>Calculation Accuracy</b> – volume, total/lateral surface area, base area,
 *                                      diameter, circumference</li>
 *   <li><b>Boundary Testing</b>     – extreme radii and heights</li>
 *   <li><b>Input Validation</b>     – zero, negative, null, blank, oversized strings</li>
 *   <li><b>Inheritance</b>          – polymorphic behaviour, equals, hashCode, toString</li>
 * </ul>
 *
 * @author  Your Name
 * @version 1.0
 * @see     Cylinder
 */
@DisplayName("Cylinder Tests")
class CylinderTest {

    private static final double DELTA       = 1e-9;
    private static final double DELTA_LARGE = 1e-3;

    private Cylinder unitCylinder;   // radius=1, height=1
    private Cylinder cyl3x10;        // radius=3, height=10
    private Cylinder namedCyl;       // name="Tank", color="Silver", radius=5, height=20

    @BeforeEach
    void setUp() {
        unitCylinder = new Cylinder();
        cyl3x10      = new Cylinder("Cyl3x10", "Red", 3.0, 10.0);
        namedCyl     = new Cylinder("Tank", "Silver", 5.0, 20.0);
    }

    // =======================================================================
    // 1. CONSTRUCTION TESTS
    // =======================================================================

    @Nested
    @DisplayName("1. Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("No-arg: name=Unknown, color=Unspecified, r=1, h=1")
        void noArgDefaults() {
            assertAll(
                    () -> assertEquals("Unknown",     unitCylinder.getName()),
                    () -> assertEquals("Unspecified", unitCylinder.getColor()),
                    () -> assertEquals(1.0, unitCylinder.getRadius(), DELTA),
                    () -> assertEquals(1.0, unitCylinder.getHeight(), DELTA)
            );
        }

        @Test
        @DisplayName("Three-arg (name, r, h) sets color to Unspecified")
        void threeArgNoColor() {
            Cylinder c = new Cylinder("Pipe", 2.0, 5.0);
            assertEquals("Unspecified", c.getColor());
            assertEquals(2.0, c.getRadius(), DELTA);
            assertEquals(5.0, c.getHeight(), DELTA);
        }

        @Test
        @DisplayName("Four-arg constructor stores all fields")
        void fourArgConstructor() {
            assertEquals("Cyl3x10", cyl3x10.getName());
            assertEquals("Red",     cyl3x10.getColor());
            assertEquals(3.0,       cyl3x10.getRadius(), DELTA);
            assertEquals(10.0,      cyl3x10.getHeight(), DELTA);
        }

        @Test
        @DisplayName("Constructor trims whitespace from name and color")
        void constructorTrims() {
            Cylinder c = new Cylinder("  Drum  ", "  Gray  ", 4.0, 8.0);
            assertEquals("Drum", c.getName());
            assertEquals("Gray", c.getColor());
        }

        @Test
        @DisplayName("Zero radius throws IllegalArgumentException")
        void zeroRadiusThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder("C", "Red", 0.0, 5.0));
        }

        @Test
        @DisplayName("Zero height throws IllegalArgumentException")
        void zeroHeightThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder("C", "Red", 3.0, 0.0));
        }

        @Test
        @DisplayName("Negative radius throws IllegalArgumentException")
        void negativeRadiusThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder("C", "Red", -2.0, 5.0));
        }

        @Test
        @DisplayName("Negative height throws IllegalArgumentException")
        void negativeHeightThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder("C", "Red", 3.0, -5.0));
        }

        @Test
        @DisplayName("Null name throws NullPointerException")
        void nullNameThrows() {
            assertThrows(NullPointerException.class,
                    () -> new Cylinder(null, "Red", 3.0, 5.0));
        }

        @Test
        @DisplayName("Null color throws NullPointerException")
        void nullColorThrows() {
            assertThrows(NullPointerException.class,
                    () -> new Cylinder("C", null, 3.0, 5.0));
        }
    }

    // =======================================================================
    // 2. GETTER / SETTER TESTS
    // =======================================================================

    @Nested
    @DisplayName("2. Getter and Setter Tests")
    class GetterSetterTests {

        @Test
        @DisplayName("setRadius updates radius")
        void setRadiusUpdates() {
            cyl3x10.setRadius(7.0);
            assertEquals(7.0, cyl3x10.getRadius(), DELTA);
        }

        @Test
        @DisplayName("setHeight updates height")
        void setHeightUpdates() {
            cyl3x10.setHeight(15.0);
            assertEquals(15.0, cyl3x10.getHeight(), DELTA);
        }

        @Test
        @DisplayName("setRadius zero throws")
        void setRadiusZeroThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> cyl3x10.setRadius(0.0));
        }

        @Test
        @DisplayName("setHeight negative throws")
        void setHeightNegativeThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> cyl3x10.setHeight(-1.0));
        }

        @Test
        @DisplayName("setName null throws NullPointerException")
        void setNameNull() {
            assertThrows(NullPointerException.class,
                    () -> cyl3x10.setName(null));
        }

        @Test
        @DisplayName("setColor blank throws IllegalArgumentException")
        void setColorBlank() {
            assertThrows(IllegalArgumentException.class,
                    () -> cyl3x10.setColor(""));
        }
    }

    // =======================================================================
    // 3. CALCULATION ACCURACY TESTS
    // =======================================================================

    @Nested
    @DisplayName("3. Calculation Accuracy Tests")
    class CalculationTests {

        @Test
        @DisplayName("Unit cylinder volume = π ≈ 3.14159")
        void unitVolume() {
            assertEquals(Math.PI, unitCylinder.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Unit cylinder total surface area = 4π ≈ 12.56637")
        void unitSurfaceArea() {
            // 2*π*1*(1+1) = 4π
            assertEquals(4.0 * Math.PI, unitCylinder.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Cylinder r=3, h=10: volume = 90π ≈ 282.74334")
        void cyl3x10Volume() {
            double expected = Math.PI * 9.0 * 10.0;
            assertEquals(expected, cyl3x10.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Cylinder r=3, h=10: total surface area = 2π*3*(3+10) = 78π")
        void cyl3x10SurfaceArea() {
            double expected = 2.0 * Math.PI * 3.0 * 13.0;
            assertEquals(expected, cyl3x10.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Lateral surface area = 2π*r*h")
        void lateralSurfaceArea() {
            double expected = 2.0 * Math.PI * 3.0 * 10.0;
            assertEquals(expected, cyl3x10.getLateralSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Base area = π*r²")
        void baseArea() {
            double expected = Math.PI * 9.0;
            assertEquals(expected, cyl3x10.getBaseArea(), DELTA);
        }

        @Test
        @DisplayName("Diameter = 2 * radius")
        void diameter() {
            assertEquals(6.0, cyl3x10.getDiameter(), DELTA);
        }

        @Test
        @DisplayName("Base circumference = 2*π*r")
        void baseCircumference() {
            double expected = 2.0 * Math.PI * 3.0;
            assertEquals(expected, cyl3x10.getBaseCircumference(), DELTA);
        }

        @Test
        @DisplayName("Total SA = lateral SA + 2 * base area")
        void totalSaEqualsLateralPlusTwoBases() {
            double expected = cyl3x10.getLateralSurfaceArea()
                            + 2.0 * cyl3x10.getBaseArea();
            assertEquals(expected, cyl3x10.getSurfaceArea(), DELTA);
        }

        @ParameterizedTest(name = "r={0}, h={1} → volume = π*r²*h")
        @CsvSource({"1.0, 1.0", "2.0, 3.0", "5.0, 5.0", "10.0, 0.5"})
        @DisplayName("Parameterized volume formula check")
        void paramVolumeFormula(double r, double h) {
            Cylinder c = new Cylinder("C", "Red", r, h);
            assertEquals(Math.PI * r * r * h, c.getVolume(), DELTA);
        }
    }

    // =======================================================================
    // 4. BOUNDARY TESTS
    // =======================================================================

    @Nested
    @DisplayName("4. Boundary Tests")
    class BoundaryTests {

        @Test
        @DisplayName("Very small radius (1e-10) gives non-negative, non-NaN volume")
        void verySmallRadius() {
            Cylinder c = new Cylinder("C", "Red", 1e-10, 1.0);
            assertTrue(c.getVolume() >= 0);
            assertFalse(Double.isNaN(c.getVolume()));
        }

        @Test
        @DisplayName("Very small height (1e-10) gives non-negative volume")
        void verySmallHeight() {
            Cylinder c = new Cylinder("C", "Red", 1.0, 1e-10);
            assertTrue(c.getVolume() >= 0);
        }

        @Test
        @DisplayName("Very large radius (1e8) gives finite volume")
        void veryLargeRadius() {
            Cylinder c = new Cylinder("C", "Red", 1e8, 1.0);
            assertTrue(Double.isFinite(c.getVolume()));
        }

        @Test
        @DisplayName("Very large height (1e8) gives finite volume")
        void veryLargeHeight() {
            Cylinder c = new Cylinder("C", "Red", 1.0, 1e8);
            assertTrue(Double.isFinite(c.getVolume()));
        }

        @Test
        @DisplayName("Double.MIN_VALUE for radius and height accepted")
        void minPositiveDouble() {
            assertDoesNotThrow(() ->
                    new Cylinder("C", "Red", Double.MIN_VALUE, Double.MIN_VALUE));
        }

        @ParameterizedTest(name = "r={0} is invalid")
        @ValueSource(doubles = {0.0, -1.0, -100.0})
        @DisplayName("Parameterized: invalid radii rejected")
        void invalidRadii(double r) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder("C", "Red", r, 1.0));
        }

        @ParameterizedTest(name = "h={0} is invalid")
        @ValueSource(doubles = {0.0, -1.0, -100.0})
        @DisplayName("Parameterized: invalid heights rejected")
        void invalidHeights(double h) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder("C", "Red", 1.0, h));
        }
    }

    // =======================================================================
    // 5. INPUT VALIDATION TESTS
    // =======================================================================

    @Nested
    @DisplayName("5. Input Validation Tests")
    class InputValidationTests {

        @Test
        @DisplayName("Name > 100 chars throws IllegalArgumentException")
        void nameTooLong() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder("A".repeat(101), "Red", 1.0, 1.0));
        }

        @Test
        @DisplayName("Name exactly 100 chars accepted")
        void nameMaxLength() {
            assertDoesNotThrow(() ->
                    new Cylinder("A".repeat(100), "Red", 1.0, 1.0));
        }

        @Test
        @DisplayName("Color > 100 chars throws IllegalArgumentException")
        void colorTooLong() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder("C", "B".repeat(101), 1.0, 1.0));
        }

        @Test
        @DisplayName("NaN radius throws IllegalArgumentException")
        void nanRadius() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder("C", "Red", Double.NaN, 1.0));
        }

        @Test
        @DisplayName("NaN height throws IllegalArgumentException")
        void nanHeight() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder("C", "Red", 1.0, Double.NaN));
        }

        @Test
        @DisplayName("Negative infinity radius throws IllegalArgumentException")
        void negInfRadius() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cylinder("C", "Red", Double.NEGATIVE_INFINITY, 1.0));
        }
    }

    // =======================================================================
    // 6. INHERITANCE / POLYMORPHISM TESTS
    // =======================================================================

    @Nested
    @DisplayName("6. Inheritance and Polymorphism Tests")
    class InheritanceTests {

        @Test
        @DisplayName("Cylinder is instance of Shape3D")
        void isShape3D() {
            assertInstanceOf(Shape3D.class, cyl3x10);
        }

        @Test
        @DisplayName("Cylinder is instance of ThreeDimensionalShape")
        void isInterface() {
            assertInstanceOf(ThreeDimensionalShape.class, cyl3x10);
        }

        @Test
        @DisplayName("Shape3D reference calls overridden getVolume()")
        void polymorphicVolume() {
            Shape3D shape = new Cylinder("C", "Red", 3.0, 10.0);
            assertEquals(Math.PI * 9.0 * 10.0, shape.getVolume(), DELTA);
        }

        @Test
        @DisplayName("ThreeDimensionalShape reference calls overridden getSurfaceArea()")
        void polymorphicSurfaceArea() {
            ThreeDimensionalShape shape = new Cylinder("C", "Red", 3.0, 10.0);
            double expected = 2.0 * Math.PI * 3.0 * 13.0;
            assertEquals(expected, shape.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("describeVolume() contains 'Volume'")
        void describeVolume() {
            assertTrue(cyl3x10.describeVolume().contains("Volume"));
        }

        @Test
        @DisplayName("describeSurfaceArea() contains 'Surface'")
        void describeSurface() {
            assertTrue(cyl3x10.describeSurfaceArea().contains("Surface"));
        }

        @Test
        @DisplayName("equals() true for same fields")
        void equalsTrue() {
            Cylinder a = new Cylinder("C", "Red", 3.0, 10.0);
            Cylinder b = new Cylinder("C", "Red", 3.0, 10.0);
            assertEquals(a, b);
        }

        @Test
        @DisplayName("equals() false when height differs")
        void equalsFalseDiffHeight() {
            Cylinder a = new Cylinder("C", "Red", 3.0, 10.0);
            Cylinder b = new Cylinder("C", "Red", 3.0, 11.0);
            assertNotEquals(a, b);
        }

        @Test
        @DisplayName("equals() false when radius differs")
        void equalsFalseDiffRadius() {
            Cylinder a = new Cylinder("C", "Red", 3.0, 10.0);
            Cylinder b = new Cylinder("C", "Red", 4.0, 10.0);
            assertNotEquals(a, b);
        }

        @Test
        @DisplayName("hashCode() consistent with equals()")
        void hashCodeConsistent() {
            Cylinder a = new Cylinder("C", "Red", 3.0, 10.0);
            Cylinder b = new Cylinder("C", "Red", 3.0, 10.0);
            assertEquals(a.hashCode(), b.hashCode());
        }

        @Test
        @DisplayName("toString() contains 'CYLINDER', name, and color")
        void toStringContent() {
            String s = namedCyl.toString();
            assertAll(
                    () -> assertTrue(s.contains("CYLINDER"), "should contain CYLINDER"),
                    () -> assertTrue(s.contains("Tank"),     "should contain name"),
                    () -> assertTrue(s.contains("Silver"),   "should contain color")
            );
        }
    }
}