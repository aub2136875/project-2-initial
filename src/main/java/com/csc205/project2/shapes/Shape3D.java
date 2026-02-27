package com.csc205.project2.shapes;
/**
 * AI GENERATION DOCUMENTATION
 * ===========================
 * AI Tool Used: [Claude Sonnet 4.6]
 * Generation Date: [2/21/26]
 *
 * Original Prompt:
 * "[Would you generate a class in java (Shape3D.java) that implements the ThreeDimensionalShape interface. It must have concrete implementations of getVolume() and getSurfaceArea() that call abstract methods. It must have common properties like name (String) and color (String). It must have constructors for initialization. There must be a toString() method that formats output consistently. It must have appropriate getter/setter methods. This needs to be proper JavaDoc documentation. Include input validation where appropriate.]"
 *
 * Follow-up Prompts (if any):
 * 1. "[What do I do if the code won't compile stating this as an error: "java: duplicate class: com.csc205.project2.shapes.ThreeDimensionalShape"]"
 *
 *
 * Manual Modifications:
 * - [I ended up deleting the ThreeDimensionalShape interface which the AI put into the same class which helped fix a lot of problems. This was also a suggestion given by the AI.]
 * - [These changes were necessary because there was more than one ThreeDimensionalShape interfaces which meant that the code couldn't compile properly.]
 */
import java.util.Objects;

/**
 * Abstract base class representing a three-dimensional geometric shape.
 *
 * <p>This class implements the {@link ThreeDimensionalShape} interface and provides
 * concrete implementations of {@link #getVolume()} and {@link #getSurfaceArea()} by
 * delegating to abstract methods that subclasses must define. It also encapsulates
 * common properties shared by all 3D shapes, such as {@code name} and {@code color},
 * along with full input validation, accessor/mutator methods, and a consistently
 * formatted {@link #toString()} representation.</p>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * // Concrete subclass example
 * Shape3D sphere = new Sphere("MySphere", "Red", 5.0);
 * System.out.println(sphere);
 * System.out.printf("Volume: %.2f%n", sphere.getVolume());
 * System.out.printf("Surface Area: %.2f%n", sphere.getSurfaceArea());
 * }</pre>
 *
 * @author  Your Name
 * @version 1.0
 * @see     ThreeDimensionalShape
 */
public abstract class Shape3D implements ThreeDimensionalShape {

    // -----------------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------------

    /** Minimum allowed string length for {@code name} and {@code color} fields. */
    private static final int MIN_STRING_LENGTH = 1;

    /** Maximum allowed string length for {@code name} and {@code color} fields. */
    private static final int MAX_STRING_LENGTH = 100;

    // -----------------------------------------------------------------------
    // Fields
    // -----------------------------------------------------------------------

    /**
     * The descriptive name of this shape (e.g., "Unit Cube", "Large Sphere").
     * Must be non-null and between {@value #MIN_STRING_LENGTH} and
     * {@value #MAX_STRING_LENGTH} characters (after trimming).
     */
    private String name;

    /**
     * The color of this shape (e.g., "Red", "Transparent Blue").
     * Must be non-null and between {@value #MIN_STRING_LENGTH} and
     * {@value #MAX_STRING_LENGTH} characters (after trimming).
     */
    private String color;

    // -----------------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------------

    /**
     * Constructs a {@code Shape3D} with both a name and a color.
     *
     * @param name  the descriptive name of this shape; must not be {@code null},
     *              blank, or exceed {@value #MAX_STRING_LENGTH} characters
     * @param color the color of this shape; must not be {@code null}, blank,
     *              or exceed {@value #MAX_STRING_LENGTH} characters
     * @throws IllegalArgumentException if {@code name} or {@code color} fails validation
     * @throws NullPointerException     if {@code name} or {@code color} is {@code null}
     */
    protected Shape3D(String name, String color) {
        setName(name);
        setColor(color);
    }

    /**
     * Constructs a {@code Shape3D} with a name only; color defaults to
     * {@code "Unspecified"}.
     *
     * @param name the descriptive name of this shape; must not be {@code null},
     *             blank, or exceed {@value #MAX_STRING_LENGTH} characters
     * @throws IllegalArgumentException if {@code name} fails validation
     * @throws NullPointerException     if {@code name} is {@code null}
     */
    protected Shape3D(String name) {
        this(name, "Unspecified");
    }

    /**
     * Default no-argument constructor. Sets name to {@code "Unknown"} and
     * color to {@code "Unspecified"}.
     */
    protected Shape3D() {
        this("Unknown", "Unspecified");
    }

    // -----------------------------------------------------------------------
    // Abstract methods (subclasses must implement)
    // -----------------------------------------------------------------------

    /**
     * Computes and returns the volume of this shape.
     *
     * <p>Subclasses must provide a concrete implementation using the
     * appropriate geometric formula (e.g., {@code (4/3)πr³} for a sphere).</p>
     *
     * @return the volume of this shape as a non-negative {@code double}
     */
    @Override
    public abstract double getVolume();

    /**
     * Computes and returns the surface area of this shape.
     *
     * <p>Subclasses must provide a concrete implementation using the
     * appropriate geometric formula (e.g., {@code 4πr²} for a sphere).</p>
     *
     * @return the surface area of this shape as a non-negative {@code double}
     */
    @Override
    public abstract double getSurfaceArea();

    // -----------------------------------------------------------------------
    // Concrete interface implementations
    // -----------------------------------------------------------------------

    /**
     * Returns a formatted string describing the volume of this shape.
     *
     * <p>Delegates to {@link #getVolume()} and formats the result to
     * four decimal places.</p>
     *
     * @return a human-readable volume description, e.g.
     *         {@code "Volume: 523.5988 cubic units"}
     */
    @Override
    public String describeVolume() {
        return String.format("Volume: %.4f cubic units", getVolume());
    }

    /**
     * Returns a formatted string describing the surface area of this shape.
     *
     * <p>Delegates to {@link #getSurfaceArea()} and formats the result to
     * four decimal places.</p>
     *
     * @return a human-readable surface area description, e.g.
     *         {@code "Surface Area: 314.1593 square units"}
     */
    @Override
    public String describeSurfaceArea() {
        return String.format("Surface Area: %.4f square units", getSurfaceArea());
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

    /**
     * Returns the name of this shape.
     *
     * @return the name; never {@code null} or blank
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this shape.
     *
     * @param name the new name; must not be {@code null}, blank, or longer than
     *             {@value #MAX_STRING_LENGTH} characters
     * @throws NullPointerException     if {@code name} is {@code null}
     * @throws IllegalArgumentException if {@code name} is blank or exceeds the
     *                                  maximum allowed length
     */
    public void setName(String name) {
        this.name = validateString(name, "name");
    }

    /**
     * Returns the color of this shape.
     *
     * @return the color; never {@code null} or blank
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color of this shape.
     *
     * @param color the new color; must not be {@code null}, blank, or longer than
     *              {@value #MAX_STRING_LENGTH} characters
     * @throws NullPointerException     if {@code color} is {@code null}
     * @throws IllegalArgumentException if {@code color} is blank or exceeds the
     *                                  maximum allowed length
     */
    public void setColor(String color) {
        this.color = validateString(color, "color");
    }

    // -----------------------------------------------------------------------
    // Object overrides
    // -----------------------------------------------------------------------

    /**
     * Returns a consistently formatted string representation of this shape.
     *
     * <p>The format is:</p>
     * <pre>
     * ╔══════════════════════════════╗
     * ║  Shape  : [name]
     * ║  Type   : [runtime class simple name]
     * ║  Color  : [color]
     * ║  Volume : [volume, 4 dp] cubic units
     * ║  Surface: [surface area, 4 dp] square units
     * ╚══════════════════════════════╝
     * </pre>
     *
     * @return a formatted multi-line string; never {@code null}
     */
    @Override
    public String toString() {
        return String.format(
                "╔══════════════════════════════════╗%n" +
                "║  Shape   : %-22s║%n" +
                "║  Type    : %-22s║%n" +
                "║  Color   : %-22s║%n" +
                "║  Volume  : %-16.4f cu. units║%n" +
                "║  Surface : %-16.4f sq. units║%n" +
                "╚══════════════════════════════════╝",
                name,
                getClass().getSimpleName(),
                color,
                getVolume(),
                getSurfaceArea()
        );
    }

    /**
     * Compares this shape to another object for equality.
     *
     * <p>Two {@code Shape3D} objects are considered equal if they have the
     * same runtime class, name (case-insensitive), and color (case-insensitive).</p>
     *
     * @param obj the object to compare with
     * @return {@code true} if the objects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Shape3D other = (Shape3D) obj;
        return name.equalsIgnoreCase(other.name) &&
               color.equalsIgnoreCase(other.color);
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return the hash code based on the lower-case {@code name} and {@code color}
     */
    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase(), color.toLowerCase());
    }

    // -----------------------------------------------------------------------
    // Private helper methods
    // -----------------------------------------------------------------------

    /**
     * Validates that a string field is non-null, non-blank, and within the
     * allowed length bounds, then returns the trimmed value.
     *
     * @param value     the string to validate
     * @param fieldName the field name used in exception messages
     * @return the trimmed, validated string
     * @throws NullPointerException     if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is blank or its trimmed
     *                                  length is outside
     *                                  [{@value #MIN_STRING_LENGTH}, {@value #MAX_STRING_LENGTH}]
     */
    private String validateString(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " must not be null.");
        String trimmed = value.trim();
        if (trimmed.length() < MIN_STRING_LENGTH) {
            throw new IllegalArgumentException(
                    fieldName + " must not be blank.");
        }
        if (trimmed.length() > MAX_STRING_LENGTH) {
            throw new IllegalArgumentException(
                    fieldName + " must not exceed " + MAX_STRING_LENGTH + " characters.");
        }
        return trimmed;
    }
}
