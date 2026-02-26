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
 * Comprehensive JUnit 5 test suite for the {@link Cone} class.
 *
 * <p>Test categories covered:</p>
 * <ul>
 *   <li><b>Construction</b>         – all three constructors and field defaults</li>
 *   <li><b>Getters / Setters</b>    – round-trip accuracy and validation guards</li>
 *   <li><b>Calculation Accuracy</b> – volume, total / lateral surface area, base area,
 *                                      slant height, diameter, apex angle</li>
 *   <li><b>Boundary Testing</b>     – extreme radii and heights</li>
 *   <li><b>Input Validation</b>     – zero, negative, null, blank, oversized strings</li>
 *   <li><b>Inheritance</b>          – polymorphic behaviour, equals, hashCode, toString</li>
 * </ul>
 *
 * @author  Your Name
 * @version 1.0
 * @see     Cone
 */
@DisplayName("Cone Tests")
class ConeTest {

    private static final double DELTA       = 1e-9;
    private static final double DELTA_LARGE = 1e-3;

    private Cone unitCone;      // radius=1, height=1
    private Cone cone3x4;       // radius=3, height=4  (3-4-5 right triangle slant)
    private Cone namedCone;     // "IceCream", "Beige", radius=2, height=6

    @BeforeEach
    void setUp() {
        unitCone  = new Cone();
        cone3x4   = new Cone("Cone3x4", "Purple", 3.0, 4.0);
        namedCone = new Cone("IceCream", "Beige", 2.0, 6.0);
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
                    () -> assertEquals("Unknown",     unitCone.getName()),
                    () -> assertEquals("Unspecified", unitCone.getColor()),
                    () -> assertEquals(1.0, unitCone.getRadius(), DELTA),
                    () -> assertEquals(1.0, unitCone.getHeight(), DELTA)
            );
        }

        @Test
        @DisplayName("Three-arg (name, r, h) sets color to Unspecified")
        void threeArgNoColor() {
            Cone c = new Cone("Party", 5.0, 12.0);
            assertEquals("Unspecified", c.getColor());
            assertEquals(5.0,  c.getRadius(), DELTA);
            assertEquals(12.0, c.getHeight(), DELTA);
        }

        @Test
        @DisplayName("Four-arg constructor stores all fields")
        void fourArgConstructor() {
            assertEquals("Cone3x4", cone3x4.getName());
            assertEquals("Purple",  cone3x4.getColor());
            assertEquals(3.0,       cone3x4.getRadius(), DELTA);
            assertEquals(4.0,       cone3x4.getHeight(), DELTA);
        }

        @Test
        @DisplayName("Constructor trims whitespace from name and color")
        void constructorTrims() {
            Cone c = new Cone("  Hat  ", "  Pink  ", 2.0, 5.0);
            assertEquals("Hat",  c.getName());
            assertEquals("Pink", c.getColor());
        }

        @Test
        @DisplayName("Zero radius throws IllegalArgumentException")
        void zeroRadiusThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cone("C", "Red", 0.0, 4.0));
        }

        @Test
        @DisplayName("Zero height throws IllegalArgumentException")
        void zeroHeightThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cone("C", "Red", 3.0, 0.0));
        }

        @Test
        @DisplayName("Negative radius throws IllegalArgumentException")
        void negativeRadiusThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cone("C", "Red", -1.0, 4.0));
        }

        @Test
        @DisplayName("Negative height throws IllegalArgumentException")
        void negativeHeightThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cone("C", "Red", 3.0, -4.0));
        }

        @Test
        @DisplayName("Null name throws NullPointerException")
        void nullNameThrows() {
            assertThrows(NullPointerException.class,
                    () -> new Cone(null, "Red", 3.0, 4.0));
        }

        @Test
        @DisplayName("Null color throws NullPointerException")
        void nullColorThrows() {
            assertThrows(NullPointerException.class,
                    () -> new Cone("C", null, 3.0, 4.0));
        }
    }

    // =======================================================================
    // 2. GETTER / SETTER TESTS
    // =======================================================================

    @Nested
    @DisplayName("2. Getter and Setter Tests")
    class GetterSetterTests {

        @Test
        @DisplayName("setRadius updates correctly")
        void setRadiusUpdates() {
            cone3x4.setRadius(6.0);
            assertEquals(6.0, cone3x4.getRadius(), DELTA);
        }

        @Test
        @DisplayName("setHeight updates correctly")
        void setHeightUpdates() {
            cone3x4.setHeight(8.0);
            assertEquals(8.0, cone3x4.getHeight(), DELTA);
        }

        @Test
        @DisplayName("setRadius zero throws")
        void setRadiusZero() {
            assertThrows(IllegalArgumentException.class,
                    () -> cone3x4.setRadius(0.0));
        }

        @Test
        @DisplayName("setHeight negative throws")
        void setHeightNegative() {
            assertThrows(IllegalArgumentException.class,
                    () -> cone3x4.setHeight(-2.0));
        }

        @Test
        @DisplayName("setName null throws NullPointerException")
        void setNameNull() {
            assertThrows(NullPointerException.class,
                    () -> cone3x4.setName(null));
        }

        @Test
        @DisplayName("setColor blank throws IllegalArgumentException")
        void setColorBlank() {
            assertThrows(IllegalArgumentException.class,
                    () -> cone3x4.setColor("  "));
        }

        @Test
        @DisplayName("setName trims correctly")
        void setNameTrims() {
            cone3x4.setName("  Dunce  ");
            assertEquals("Dunce", cone3x4.getName());
        }
    }

    // =======================================================================
    // 3. CALCULATION ACCURACY TESTS
    // =======================================================================

    @Nested
    @DisplayName("3. Calculation Accuracy Tests")
    class CalculationTests {

        @Test
        @DisplayName("Unit cone volume = π/3 ≈ 1.04720")
        void unitVolume() {
            assertEquals(Math.PI / 3.0, unitCone.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Unit cone slant height = √2 ≈ 1.41421")
        void unitSlantHeight() {
            assertEquals(Math.sqrt(2.0), unitCone.getSlantHeight(), DELTA);
        }

        @Test
        @DisplayName("Cone r=3, h=4: slant height = 5 (3-4-5 Pythagorean triple)")
        void cone3x4SlantHeight() {
            assertEquals(5.0, cone3x4.getSlantHeight(), DELTA);
        }

        @Test
        @DisplayName("Cone r=3, h=4: volume = (1/3)*π*9*4 = 12π")
        void cone3x4Volume() {
            double expected = (1.0 / 3.0) * Math.PI * 9.0 * 4.0;
            assertEquals(expected, cone3x4.getVolume(), DELTA);
        }

        @Test
        @DisplayName("Cone r=3, h=4: lateral SA = π*r*l = 15π")
        void cone3x4LateralSurface() {
            double expected = Math.PI * 3.0 * 5.0;  // l=5
            assertEquals(expected, cone3x4.getLateralSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Cone r=3, h=4: total SA = π*r*(r+l) = π*3*8 = 24π")
        void cone3x4TotalSurface() {
            double expected = Math.PI * 3.0 * (3.0 + 5.0);
            assertEquals(expected, cone3x4.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Base area = π * r²")
        void baseArea() {
            double expected = Math.PI * 9.0;
            assertEquals(expected, cone3x4.getBaseArea(), DELTA);
        }

        @Test
        @DisplayName("Diameter = 2 * radius")
        void diameter() {
            assertEquals(6.0, cone3x4.getDiameter(), DELTA);
        }

        @Test
        @DisplayName("Total SA = lateral SA + base area")
        void totalSaEqualsLateralPlusBase() {
            double expected = cone3x4.getLateralSurfaceArea() + cone3x4.getBaseArea();
            assertEquals(expected, cone3x4.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("Apex angle for r=h (isoceles right) ≈ 45°")
        void apexAngle45Degrees() {
            Cone c = new Cone("C", "Red", 5.0, 5.0);  // r == h
            assertEquals(45.0, c.getApexHalfAngleDegrees(), DELTA);
        }

        @Test
        @DisplayName("Apex angle > 0 and < 90 degrees for any valid cone")
        void apexAngleBounds() {
            double angle = cone3x4.getApexHalfAngleDegrees();
            assertTrue(angle > 0  && angle < 90,
                    "Apex angle must be in (0, 90) but was " + angle);
        }

        @Test
        @DisplayName("Very tall cone (h >> r) has small apex angle")
        void tallConeSmallApex() {
            Cone tall = new Cone("C", "Red", 1.0, 1000.0);
            assertTrue(tall.getApexHalfAngleDegrees() < 1.0);
        }

        @Test
        @DisplayName("Very wide cone (r >> h) has large apex angle")
        void wideConeWideApex() {
            Cone wide = new Cone("C", "Red", 1000.0, 1.0);
            assertTrue(wide.getApexHalfAngleDegrees() > 89.0);
        }

        @ParameterizedTest(name = "r={0}, h={1} → volume = (π/3)*r²*h")
        @CsvSource({"1.0,1.0", "3.0,4.0", "6.0,8.0", "5.0,12.0"})
        @DisplayName("Parameterized volume formula check")
        void paramVolume(double r, double h) {
            Cone c = new Cone("C", "Red", r, h);
            double expected = (1.0 / 3.0) * Math.PI * r * r * h;
            assertEquals(expected, c.getVolume(), DELTA);
        }

        @ParameterizedTest(name = "r={0}, h={1}: slant=√(r²+h²)")
        @CsvSource({"3.0,4.0,5.0", "5.0,12.0,13.0", "8.0,15.0,17.0"})
        @DisplayName("Parameterized slant height using Pythagorean triples")
        void paramSlantHeight(double r, double h, double expectedSlant) {
            Cone c = new Cone("C", "Red", r, h);
            assertEquals(expectedSlant, c.getSlantHeight(), DELTA);
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
            Cone c = new Cone("C", "Red", 1e-10, 1.0);
            assertTrue(c.getVolume() >= 0);
            assertFalse(Double.isNaN(c.getVolume()));
        }

        @Test
        @DisplayName("Very small height (1e-10) gives non-negative volume")
        void verySmallHeight() {
            Cone c = new Cone("C", "Red", 1.0, 1e-10);
            assertTrue(c.getVolume() >= 0);
        }

        @Test
        @DisplayName("Very large radius (1e8) gives finite volume")
        void veryLargeRadius() {
            Cone c = new Cone("C", "Red", 1e8, 1.0);
            assertTrue(Double.isFinite(c.getVolume()));
        }

        @Test
        @DisplayName("Very large height (1e8) gives finite volume")
        void veryLargeHeight() {
            Cone c = new Cone("C", "Red", 1.0, 1e8);
            assertTrue(Double.isFinite(c.getVolume()));
        }

        @Test
        @DisplayName("Double.MIN_VALUE for radius and height accepted")
        void minPositiveDouble() {
            double m = Double.MIN_VALUE;
            assertDoesNotThrow(() -> new Cone("C", "Red", m, m));
        }

        @ParameterizedTest(name = "r={0} is invalid")
        @ValueSource(doubles = {0.0, -0.001, -10.0})
        @DisplayName("Parameterized: zero / negative radii rejected")
        void invalidRadii(double r) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cone("C", "Red", r, 5.0));
        }

        @ParameterizedTest(name = "h={0} is invalid")
        @ValueSource(doubles = {0.0, -0.001, -10.0})
        @DisplayName("Parameterized: zero / negative heights rejected")
        void invalidHeights(double h) {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cone("C", "Red", 3.0, h));
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
                    () -> new Cone("A".repeat(101), "Red", 1.0, 1.0));
        }

        @Test
        @DisplayName("Name exactly 100 chars accepted")
        void nameMaxLength() {
            assertDoesNotThrow(() -> new Cone("A".repeat(100), "Red", 1.0, 1.0));
        }

        @Test
        @DisplayName("Color > 100 chars throws IllegalArgumentException")
        void colorTooLong() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cone("C", "B".repeat(101), 1.0, 1.0));
        }

        @Test
        @DisplayName("Blank name throws IllegalArgumentException")
        void blankName() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cone("  ", "Red", 1.0, 1.0));
        }

        @Test
        @DisplayName("NaN radius throws IllegalArgumentException")
        void nanRadius() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cone("C", "Red", Double.NaN, 1.0));
        }

        @Test
        @DisplayName("NaN height throws IllegalArgumentException")
        void nanHeight() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cone("C", "Red", 1.0, Double.NaN));
        }

        @Test
        @DisplayName("Positive infinity radius throws IllegalArgumentException")
        void posInfRadius() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Cone("C", "Red", Double.POSITIVE_INFINITY, 1.0));
        }
    }

    // =======================================================================
    // 6. INHERITANCE / POLYMORPHISM TESTS
    // =======================================================================

    @Nested
    @DisplayName("6. Inheritance and Polymorphism Tests")
    class InheritanceTests {

        @Test
        @DisplayName("Cone is instance of Shape3D")
        void isShape3D() {
            assertInstanceOf(Shape3D.class, cone3x4);
        }

        @Test
        @DisplayName("Cone is instance of ThreeDimensionalShape")
        void isInterface() {
            assertInstanceOf(ThreeDimensionalShape.class, cone3x4);
        }

        @Test
        @DisplayName("Shape3D reference calls overridden getVolume()")
        void polymorphicVolume() {
            Shape3D shape = new Cone("C", "Red", 3.0, 4.0);
            double expected = (1.0 / 3.0) * Math.PI * 9.0 * 4.0;
            assertEquals(expected, shape.getVolume(), DELTA);
        }

        @Test
        @DisplayName("ThreeDimensionalShape reference calls overridden getSurfaceArea()")
        void polymorphicSurfaceArea() {
            ThreeDimensionalShape shape = new Cone("C", "Red", 3.0, 4.0);
            double expected = Math.PI * 3.0 * (3.0 + 5.0); // l=5
            assertEquals(expected, shape.getSurfaceArea(), DELTA);
        }

        @Test
        @DisplayName("describeVolume() contains 'Volume'")
        void describeVolume() {
            assertTrue(cone3x4.describeVolume().contains("Volume"));
        }

        @Test
        @DisplayName("describeSurfaceArea() contains 'Surface'")
        void describeSurface() {
            assertTrue(cone3x4.describeSurfaceArea().contains("Surface"));
        }

        @Test
        @DisplayName("equals() true for same name, color, radius, height")
        void equalsTrue() {
            Cone a = new Cone("C", "Red", 3.0, 4.0);
            Cone b = new Cone("C", "Red", 3.0, 4.0);
            assertEquals(a, b);
        }

        @Test
        @DisplayName("equals() false when radius differs")
        void equalsFalseDiffRadius() {
            Cone a = new Cone("C", "Red", 3.0, 4.0);
            Cone b = new Cone("C", "Red", 4.0, 4.0);
            assertNotEquals(a, b);
        }

        @Test
        @DisplayName("equals() false when height differs")
        void equalsFalseDiffHeight() {
            Cone a = new Cone("C", "Red", 3.0, 4.0);
            Cone b = new Cone("C", "Red", 3.0, 5.0);
            assertNotEquals(a, b);
        }

        @Test
        @DisplayName("hashCode() consistent with equals()")
        void hashCodeConsistent() {
            Cone a = new Cone("C", "Red", 3.0, 4.0);
            Cone b = new Cone("C", "Red", 3.0, 4.0);
            assertEquals(a.hashCode(), b.hashCode());
        }

        @Test
        @DisplayName("toString() contains 'CONE', name, and color")
        void toStringContent() {
            String s = namedCone.toString();
            assertAll(
                    () -> assertTrue(s.contains("CONE"),     "should contain CONE"),
                    () -> assertTrue(s.contains("IceCream"), "should contain name"),
                    () -> assertTrue(s.contains("Beige"),    "should contain color")
            );
        }

        @Test
        @DisplayName("Cone not equal to Cylinder with same radius and height")
        void coneNotEqualToCylinder() {
            Cone     cone = new Cone("C", "Red", 3.0, 4.0);
            Cylinder cyl  = new Cylinder("C", "Red", 3.0, 4.0);
            assertNotEquals(cone, cyl);
        }
    }
}