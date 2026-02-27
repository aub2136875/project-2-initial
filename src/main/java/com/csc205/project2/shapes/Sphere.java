package com.csc205.project2.shapes;
/**
 * AI GENERATION DOCUMENTATION
 * ===========================
 * AI Tool Used: Claude Sonnet 4.6
 * Generation Date: 2/21/26
 *
 * Original Prompt:
 * "Would you create five concrete shape classes: 1 is Sphere where properties include radius, 2 is Cube where properties include sideLength, 3 is Cylinder where properties include radius and height, 4 is RectangularPrism where properties include length width and height, and 5 is cone where properties include radius and height. Each shape class must extend Shape3D which implements ThreeDimensionalShape. They must implement the abstract methods from ThreeDimensionalShape. They must include proper constructors with validation. They must have override toString() with shape specific formatting. Also add any shape-specific methods if needed."
 *
 * Follow-up Prompts (if any):
 * 1. Surprisingly I didn't need any follow-up prompts regarding the different shape classes however there were modifications that were advised while looking through other classes.
 *
 *
 * Manual Modifications:
 * - [I replaced the shape classes with updated shape classes from the AI after finding issues within the test classes which I asked for the AI to help resolve.]
 * - [These changes were necessary because they were required in order to have the test classes run without issues.]
 *
 * Formula Verification:
 * - Volume formula verified against: [Given Formulas (Mathematical Formulas)]
 * - Surface area formula verified against: [Given Formula (Mathematical Formulas)]
 */
import java.util.Objects;

/**
 * Represents a three-dimensional sphere defined by a single radius.
 *
 * <p>Geometric formulas used:</p>
 * <ul>
 *   <li><b>Volume</b>        &mdash; {@code (4/3) * π * r³}</li>
 *   <li><b>Surface Area</b>  &mdash; {@code 4 * π * r²}</li>
 *   <li><b>Diameter</b>      &mdash; {@code 2 * r}</li>
 *   <li><b>Great Circle</b>  &mdash; {@code π * r²}  (cross-sectional area)</li>
 * </ul>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * Sphere s = new Sphere("BeachBall", "Yellow", 15.0);
 * System.out.println(s);
 * System.out.printf("Diameter : %.2f%n", s.getDiameter());
 * System.out.printf("Great Circle Area: %.2f%n", s.getGreatCircleArea());
 * }</pre>
 *
 * @author  Your Name
 * @version 1.0
 * @see     Shape3D
 */
public class Sphere extends Shape3D {

    // -----------------------------------------------------------------------
    // Fields
    // -----------------------------------------------------------------------

    /**
     * The radius of the sphere.
     * Must be strictly greater than zero.
     */
    private double radius;

    // -----------------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------------

    /**
     * Constructs a fully specified {@code Sphere}.
     *
     * @param name   the descriptive name; see {@link Shape3D#setName(String)} for rules
     * @param color  the color; see {@link Shape3D#setColor(String)} for rules
     * @param radius the radius of the sphere; must be &gt; 0
     * @throws IllegalArgumentException if {@code radius} &le; 0, or if name / color are invalid
     * @throws NullPointerException     if {@code name} or {@code color} is {@code null}
     */
    public Sphere(String name, String color, double radius) {
        super(name, color);
        setRadius(radius);
    }

    /**
     * Constructs a {@code Sphere} with a name and radius; color defaults to
     * {@code "Unspecified"}.
     *
     * @param name   the descriptive name
     * @param radius the radius of the sphere; must be &gt; 0
     * @throws IllegalArgumentException if {@code radius} &le; 0 or {@code name} is invalid
     * @throws NullPointerException     if {@code name} is {@code null}
     */
    public Sphere(String name, double radius) {
        super(name);
        setRadius(radius);
    }

    /**
     * Constructs a unit {@code Sphere} (radius = 1) with default name and color.
     */
    public Sphere() {
        super();
        this.radius = 1.0;
    }

    // -----------------------------------------------------------------------
    // Abstract method implementations
    // -----------------------------------------------------------------------

    /**
     * Computes the volume of the sphere using {@code (4/3) * π * r³}.
     *
     * @return the volume; always &gt; 0
     */
    @Override
    public double getVolume() {
        return (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
    }

    /**
     * Computes the surface area of the sphere using {@code 4 * π * r²}.
     *
     * @return the surface area; always &gt; 0
     */
    @Override
    public double getSurfaceArea() {
        return 4.0 * Math.PI * Math.pow(radius, 2);
    }

    // -----------------------------------------------------------------------
    // Shape-specific methods
    // -----------------------------------------------------------------------

    /**
     * Returns the diameter of the sphere ({@code 2 * r}).
     *
     * @return the diameter; always &gt; 0
     */
    public double getDiameter() {
        return 2.0 * radius;
    }

    /**
     * Returns the area of the great circle (largest cross-sectional circle)
     * using {@code π * r²}.
     *
     * @return the great circle area; always &gt; 0
     */
    public double getGreatCircleArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    /**
     * Returns the circumference of the great circle ({@code 2 * π * r}).
     *
     * @return the great circle circumference; always &gt; 0
     */
    public double getGreatCircleCircumference() {
        return 2.0 * Math.PI * radius;
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

    /**
     * Returns the radius of the sphere.
     *
     * @return the radius; always &gt; 0
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Sets the radius of the sphere.
     *
     * @param radius the new radius; must be strictly greater than zero
     * @throws IllegalArgumentException if {@code radius} &le; 0
     */
    public void setRadius(double radius) {
        if (!Double.isFinite(radius) || radius <= 0) {
            throw new IllegalArgumentException(
                    "Sphere radius must be a finite positive number, but was: " + radius);
        }
        this.radius = radius;
    }

    // -----------------------------------------------------------------------
    // Object overrides
    // -----------------------------------------------------------------------

    /**
     * Returns a sphere-specific formatted string that extends the base layout
     * with radius, diameter, and great-circle details.
     *
     * @return a formatted multi-line string; never {@code null}
     */
    @Override
    public String toString() {
        return String.format(
                "╔══════════════════════════════════╗%n" +
                "║            SPHERE                ║%n" +
                "╠══════════════════════════════════╣%n" +
                "║  Name    : %-22s║%n" +
                "║  Color   : %-22s║%n" +
                "╠══════════════════════════════════╣%n" +
                "║  Radius  : %-22.4f║%n" +
                "║  Diameter: %-22.4f║%n" +
                "╠══════════════════════════════════╣%n" +
                "║  Volume  : %-16.4f cu. units║%n" +
                "║  Surface : %-16.4f sq. units║%n" +
                "║  Gt.Circle: %-15.4f sq. units║%n" +
                "╚══════════════════════════════════╝",
                getName(), getColor(),
                radius, getDiameter(),
                getVolume(), getSurfaceArea(), getGreatCircleArea());
    }

    /**
     * Compares this sphere to another object for equality.
     * Two spheres are equal if their base {@link Shape3D} fields match
     * and their radii are identical.
     *
     * @param obj the object to compare with
     * @return {@code true} if the objects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Sphere other = (Sphere) obj;
        return Double.compare(radius, other.radius) == 0;
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), radius);
    }
}