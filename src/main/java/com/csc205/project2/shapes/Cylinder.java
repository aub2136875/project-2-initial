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
 * 1. "[Refinement prompt 1]"
 * 2. "[Refinement prompt 2]"
 *
 * Manual Modifications:
 * - [List any changes you made to the AI output]
 * - [Explain why changes were necessary]
 *
 * Formula Verification:
 * - Volume formula verified against: Given Formula (Mathematical Formulas)
 * - Surface area formula verified against: Given Formula (Mathematical Formulas)
 */
import java.util.Objects;

/**
 * Represents a three-dimensional right circular cylinder defined by a
 * radius and a height.
 *
 * <p>Geometric formulas used:</p>
 * <ul>
 *   <li><b>Volume</b>           &mdash; {@code π * r² * h}</li>
 *   <li><b>Surface Area</b>     &mdash; {@code 2 * π * r * (r + h)}</li>
 *   <li><b>Lateral Surface</b>  &mdash; {@code 2 * π * r * h}  (curved side only)</li>
 *   <li><b>Base Area</b>        &mdash; {@code π * r²}          (one circular cap)</li>
 *   <li><b>Diameter</b>         &mdash; {@code 2 * r}</li>
 * </ul>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * Cylinder c = new Cylinder("OilDrum", "Metallic Grey", 30.0, 90.0);
 * System.out.println(c);
 * System.out.printf("Lateral Surface Area: %.4f%n", c.getLateralSurfaceArea());
 * System.out.printf("Base Area           : %.4f%n", c.getBaseArea());
 * }</pre>
 *
 * @author  Your Name
 * @version 1.0
 * @see     Shape3D
 */
public class Cylinder extends Shape3D {

    // -----------------------------------------------------------------------
    // Fields
    // -----------------------------------------------------------------------

    /**
     * The radius of the circular base/top of the cylinder.
     * Must be strictly greater than zero.
     */
    private double radius;

    /**
     * The height (axial length) of the cylinder.
     * Must be strictly greater than zero.
     */
    private double height;

    // -----------------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------------

    /**
     * Constructs a fully specified {@code Cylinder}.
     *
     * @param name   the descriptive name; see {@link Shape3D#setName(String)} for rules
     * @param color  the color; see {@link Shape3D#setColor(String)} for rules
     * @param radius the base radius; must be &gt; 0
     * @param height the height; must be &gt; 0
     * @throws IllegalArgumentException if {@code radius} or {@code height} &le; 0,
     *                                  or name / color are invalid
     * @throws NullPointerException     if {@code name} or {@code color} is {@code null}
     */
    public Cylinder(String name, String color, double radius, double height) {
        super(name, color);
        setRadius(radius);
        setHeight(height);
    }

    /**
     * Constructs a {@code Cylinder} with a name, radius, and height;
     * color defaults to {@code "Unspecified"}.
     *
     * @param name   the descriptive name
     * @param radius the base radius; must be &gt; 0
     * @param height the height; must be &gt; 0
     * @throws IllegalArgumentException if {@code radius} or {@code height} &le; 0,
     *                                  or {@code name} is invalid
     * @throws NullPointerException     if {@code name} is {@code null}
     */
    public Cylinder(String name, double radius, double height) {
        super(name);
        setRadius(radius);
        setHeight(height);
    }

    /**
     * Constructs a unit {@code Cylinder} (radius = 1, height = 1) with
     * default name and color.
     */
    public Cylinder() {
        super();
        this.radius = 1.0;
        this.height = 1.0;
    }

    // -----------------------------------------------------------------------
    // Abstract method implementations
    // -----------------------------------------------------------------------

    /**
     * Computes the volume of the cylinder using {@code π * r² * h}.
     *
     * @return the volume; always &gt; 0
     */
    @Override
    public double getVolume() {
        return Math.PI * Math.pow(radius, 2) * height;
    }

    /**
     * Computes the total surface area (two caps + lateral surface) using
     * {@code 2 * π * r * (r + h)}.
     *
     * @return the total surface area; always &gt; 0
     */
    @Override
    public double getSurfaceArea() {
        return 2.0 * Math.PI * radius * (radius + height);
    }

    // -----------------------------------------------------------------------
    // Shape-specific methods
    // -----------------------------------------------------------------------

    /**
     * Returns the lateral (curved) surface area of the cylinder,
     * excluding the two circular caps, using {@code 2 * π * r * h}.
     *
     * @return the lateral surface area; always &gt; 0
     */
    public double getLateralSurfaceArea() {
        return 2.0 * Math.PI * radius * height;
    }

    /**
     * Returns the area of one circular base (or top cap) using {@code π * r²}.
     *
     * @return the base area; always &gt; 0
     */
    public double getBaseArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    /**
     * Returns the diameter of the circular base ({@code 2 * r}).
     *
     * @return the diameter; always &gt; 0
     */
    public double getDiameter() {
        return 2.0 * radius;
    }

    /**
     * Returns the circumference of the circular base ({@code 2 * π * r}).
     *
     * @return the base circumference; always &gt; 0
     */
    public double getBaseCircumference() {
        return 2.0 * Math.PI * radius;
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

    /**
     * Returns the radius of the cylinder's circular base.
     *
     * @return the radius; always &gt; 0
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Sets the radius of the cylinder's circular base.
     *
     * @param radius the new radius; must be strictly greater than zero
     * @throws IllegalArgumentException if {@code radius} &le; 0
     */
    public void setRadius(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException(
                    "Cylinder radius must be > 0, but was: " + radius);
        }
        this.radius = radius;
    }

    /**
     * Returns the height of the cylinder.
     *
     * @return the height; always &gt; 0
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height of the cylinder.
     *
     * @param height the new height; must be strictly greater than zero
     * @throws IllegalArgumentException if {@code height} &le; 0
     */
    public void setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException(
                    "Cylinder height must be > 0, but was: " + height);
        }
        this.height = height;
    }

    // -----------------------------------------------------------------------
    // Object overrides
    // -----------------------------------------------------------------------

    /**
     * Returns a cylinder-specific formatted string extending the base layout
     * with radius, height, diameter, and lateral surface area details.
     *
     * @return a formatted multi-line string; never {@code null}
     */
    @Override
    public String toString() {
        return String.format(
                "╔══════════════════════════════════╗%n" +
                "║           CYLINDER               ║%n" +
                "╠══════════════════════════════════╣%n" +
                "║  Name      : %-20s║%n" +
                "║  Color     : %-20s║%n" +
                "╠══════════════════════════════════╣%n" +
                "║  Radius    : %-20.4f║%n" +
                "║  Diameter  : %-20.4f║%n" +
                "║  Height    : %-20.4f║%n" +
                "╠══════════════════════════════════╣%n" +
                "║  Base Area : %-16.4f sq. units║%n" +
                "║  Lateral SA: %-16.4f sq. units║%n" +
                "║  Volume    : %-16.4f cu. units║%n" +
                "║  Surface   : %-16.4f sq. units║%n" +
                "╚══════════════════════════════════╝",
                getName(), getColor(),
                radius, getDiameter(), height,
                getBaseArea(), getLateralSurfaceArea(),
                getVolume(), getSurfaceArea());
    }

    /**
     * Compares this cylinder to another object for equality.
     * Two cylinders are equal if their base {@link Shape3D} fields match
     * and both radius and height are identical.
     *
     * @param obj the object to compare with
     * @return {@code true} if the objects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Cylinder other = (Cylinder) obj;
        return Double.compare(radius, other.radius) == 0 &&
               Double.compare(height, other.height) == 0;
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), radius, height);
    }
}