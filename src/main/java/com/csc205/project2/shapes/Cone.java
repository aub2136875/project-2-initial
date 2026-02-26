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
 * - Volume formula verified against: https://brilliant.org/wiki/volume-cone/
 * - Surface area formula verified against: Google + https://brilliant.org/wiki/surface-area-of-a-cone/#:~:text=The%20surface%20area%20of%20a,also%20called%20the%20lateral%20area.
 */
import java.util.Objects;

/**
 * Represents a three-dimensional right circular cone defined by a base
 * radius and a vertical height.
 *
 * <p>Geometric formulas used:</p>
 * <ul>
 *   <li><b>Volume</b>          &mdash; {@code (1/3) * π * r² * h}</li>
 *   <li><b>Slant Height</b>    &mdash; {@code √(r² + h²)}</li>
 *   <li><b>Lateral Surface</b> &mdash; {@code π * r * l}  (where l = slant height)</li>
 *   <li><b>Base Area</b>       &mdash; {@code π * r²}</li>
 *   <li><b>Total Surface</b>   &mdash; {@code π * r * (r + l)}</li>
 * </ul>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * Cone c = new Cone("IceCreamCone", "Beige", 4.0, 10.0);
 * System.out.println(c);
 * System.out.printf("Slant Height         : %.4f%n", c.getSlantHeight());
 * System.out.printf("Lateral Surface Area : %.4f%n", c.getLateralSurfaceArea());
 * }</pre>
 *
 * @author  Your Name
 * @version 1.0
 * @see     Shape3D
 */
public class Cone extends Shape3D {

    // -----------------------------------------------------------------------
    // Fields
    // -----------------------------------------------------------------------

    /**
     * The radius of the circular base of the cone.
     * Must be strictly greater than zero.
     */
    private double radius;

    /**
     * The vertical height of the cone (apex to base centre).
     * Must be strictly greater than zero.
     */
    private double height;

    // -----------------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------------

    /**
     * Constructs a fully specified {@code Cone}.
     *
     * @param name   the descriptive name; see {@link Shape3D#setName(String)} for rules
     * @param color  the color; see {@link Shape3D#setColor(String)} for rules
     * @param radius the base radius; must be &gt; 0
     * @param height the vertical height; must be &gt; 0
     * @throws IllegalArgumentException if {@code radius} or {@code height} &le; 0,
     *                                  or name / color are invalid
     * @throws NullPointerException     if {@code name} or {@code color} is {@code null}
     */
    public Cone(String name, String color, double radius, double height) {
        super(name, color);
        setRadius(radius);
        setHeight(height);
    }

    /**
     * Constructs a {@code Cone} with a name, radius, and height;
     * color defaults to {@code "Unspecified"}.
     *
     * @param name   the descriptive name
     * @param radius the base radius; must be &gt; 0
     * @param height the vertical height; must be &gt; 0
     * @throws IllegalArgumentException if {@code radius} or {@code height} &le; 0,
     *                                  or {@code name} is invalid
     * @throws NullPointerException     if {@code name} is {@code null}
     */
    public Cone(String name, double radius, double height) {
        super(name);
        setRadius(radius);
        setHeight(height);
    }

    /**
     * Constructs a unit {@code Cone} (radius = 1, height = 1) with default
     * name and color.
     */
    public Cone() {
        super();
        this.radius = 1.0;
        this.height = 1.0;
    }

    // -----------------------------------------------------------------------
    // Abstract method implementations
    // -----------------------------------------------------------------------

    /**
     * Computes the volume of the cone using {@code (1/3) * π * r² * h}.
     *
     * @return the volume; always &gt; 0
     */
    @Override
    public double getVolume() {
        return (1.0 / 3.0) * Math.PI * Math.pow(radius, 2) * height;
    }

    /**
     * Computes the total surface area (base + lateral) using
     * {@code π * r * (r + l)}, where {@code l} is the slant height.
     *
     * @return the total surface area; always &gt; 0
     */
    @Override
    public double getSurfaceArea() {
        return Math.PI * radius * (radius + getSlantHeight());
    }

    // -----------------------------------------------------------------------
    // Shape-specific methods
    // -----------------------------------------------------------------------

    /**
     * Returns the slant height of the cone — the straight-line distance from
     * the apex to any point on the base edge — using {@code √(r² + h²)}.
     *
     * @return the slant height; always &gt; 0
     */
    public double getSlantHeight() {
        return Math.sqrt(Math.pow(radius, 2) + Math.pow(height, 2));
    }

    /**
     * Returns the lateral (curved) surface area of the cone, excluding the
     * base, using {@code π * r * l} where {@code l} is the slant height.
     *
     * @return the lateral surface area; always &gt; 0
     */
    public double getLateralSurfaceArea() {
        return Math.PI * radius * getSlantHeight();
    }

    /**
     * Returns the area of the circular base using {@code π * r²}.
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
     * Returns the half-angle of the cone at the apex (in degrees).
     * This is the angle between the axis and the slant side,
     * computed as {@code atan(r / h)} converted to degrees.
     *
     * @return the apex half-angle in degrees; in range (0°, 90°)
     */
    public double getApexHalfAngleDegrees() {
        return Math.toDegrees(Math.atan(radius / height));
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

    /**
     * Returns the radius of the cone's circular base.
     *
     * @return the radius; always &gt; 0
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Sets the radius of the cone's circular base.
     *
     * @param radius the new radius; must be strictly greater than zero
     * @throws IllegalArgumentException if {@code radius} &le; 0
     */
    public void setRadius(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException(
                    "Cone radius must be > 0, but was: " + radius);
        }
        this.radius = radius;
    }

    /**
     * Returns the vertical height of the cone.
     *
     * @return the height; always &gt; 0
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the vertical height of the cone.
     *
     * @param height the new height; must be strictly greater than zero
     * @throws IllegalArgumentException if {@code height} &le; 0
     */
    public void setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException(
                    "Cone height must be > 0, but was: " + height);
        }
        this.height = height;
    }

    // -----------------------------------------------------------------------
    // Object overrides
    // -----------------------------------------------------------------------

    /**
     * Returns a cone-specific formatted string extending the base layout with
     * radius, height, slant height, apex angle, lateral surface area, and
     * base area details.
     *
     * @return a formatted multi-line string; never {@code null}
     */
    @Override
    public String toString() {
        return String.format(
                "╔══════════════════════════════════╗%n" +
                "║              CONE                ║%n" +
                "╠══════════════════════════════════╣%n" +
                "║  Name        : %-18s║%n" +
                "║  Color       : %-18s║%n" +
                "╠══════════════════════════════════╣%n" +
                "║  Radius      : %-18.4f║%n" +
                "║  Diameter    : %-18.4f║%n" +
                "║  Height      : %-18.4f║%n" +
                "║  Slant Height: %-18.4f║%n" +
                "║  Apex Angle  : %-14.4f degrees║%n" +
                "╠══════════════════════════════════╣%n" +
                "║  Base Area   : %-14.4f sq. units║%n" +
                "║  Lateral SA  : %-14.4f sq. units║%n" +
                "║  Volume      : %-14.4f cu. units║%n" +
                "║  Surface     : %-14.4f sq. units║%n" +
                "╚══════════════════════════════════╝",
                getName(), getColor(),
                radius, getDiameter(), height, getSlantHeight(),
                getApexHalfAngleDegrees(),
                getBaseArea(), getLateralSurfaceArea(),
                getVolume(), getSurfaceArea());
    }

    /**
     * Compares this cone to another object for equality.
     * Two cones are equal if their base {@link Shape3D} fields match and
     * both radius and height are identical.
     *
     * @param obj the object to compare with
     * @return {@code true} if the objects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Cone other = (Cone) obj;
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