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
 * Represents a three-dimensional cube defined by a single side length.
 *
 * <p>A cube is a regular hexahedron: six identical square faces, twelve equal
 * edges, and eight vertices. All side lengths are equal.</p>
 *
 * <p>Geometric formulas used:</p>
 * <ul>
 *   <li><b>Volume</b>           &mdash; {@code s³}</li>
 *   <li><b>Surface Area</b>     &mdash; {@code 6 * s²}</li>
 *   <li><b>Face Diagonal</b>    &mdash; {@code s * √2}</li>
 *   <li><b>Space Diagonal</b>   &mdash; {@code s * √3}</li>
 *   <li><b>Face Area</b>        &mdash; {@code s²}</li>
 * </ul>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * Cube c = new Cube("DiceBlock", "White", 3.5);
 * System.out.println(c);
 * System.out.printf("Face Diagonal : %.4f%n", c.getFaceDiagonal());
 * System.out.printf("Space Diagonal: %.4f%n", c.getSpaceDiagonal());
 * }</pre>
 *
 * @author  Your Name
 * @version 1.0
 * @see     Shape3D
 */
public class Cube extends Shape3D {

    // -----------------------------------------------------------------------
    // Fields
    // -----------------------------------------------------------------------

    /**
     * The length of one side (edge) of the cube.
     * Must be strictly greater than zero.
     */
    private double sideLength;

    // -----------------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------------

    /**
     * Constructs a fully specified {@code Cube}.
     *
     * @param name       the descriptive name; see {@link Shape3D#setName(String)} for rules
     * @param color      the color; see {@link Shape3D#setColor(String)} for rules
     * @param sideLength the edge length; must be &gt; 0
     * @throws IllegalArgumentException if {@code sideLength} &le; 0, or name / color are invalid
     * @throws NullPointerException     if {@code name} or {@code color} is {@code null}
     */
    public Cube(String name, String color, double sideLength) {
        super(name, color);
        setSideLength(sideLength);
    }

    /**
     * Constructs a {@code Cube} with a name and side length; color defaults to
     * {@code "Unspecified"}.
     *
     * @param name       the descriptive name
     * @param sideLength the edge length; must be &gt; 0
     * @throws IllegalArgumentException if {@code sideLength} &le; 0 or {@code name} is invalid
     * @throws NullPointerException     if {@code name} is {@code null}
     */
    public Cube(String name, double sideLength) {
        super(name);
        setSideLength(sideLength);
    }

    /**
     * Constructs a unit {@code Cube} (side length = 1) with default name and color.
     */
    public Cube() {
        super();
        this.sideLength = 1.0;
    }

    // -----------------------------------------------------------------------
    // Abstract method implementations
    // -----------------------------------------------------------------------

    /**
     * Computes the volume of the cube using {@code s³}.
     *
     * @return the volume; always &gt; 0
     */
    @Override
    public double getVolume() {
        return Math.pow(sideLength, 3);
    }

    /**
     * Computes the surface area of the cube using {@code 6 * s²}.
     *
     * @return the surface area; always &gt; 0
     */
    @Override
    public double getSurfaceArea() {
        return 6.0 * Math.pow(sideLength, 2);
    }

    // -----------------------------------------------------------------------
    // Shape-specific methods
    // -----------------------------------------------------------------------

    /**
     * Returns the area of one face of the cube ({@code s²}).
     *
     * @return the face area; always &gt; 0
     */
    public double getFaceArea() {
        return Math.pow(sideLength, 2);
    }

    /**
     * Returns the face diagonal length &mdash; the diagonal across one square
     * face &mdash; using {@code s * √2}.
     *
     * @return the face diagonal; always &gt; 0
     */
    public double getFaceDiagonal() {
        return sideLength * Math.sqrt(2.0);
    }

    /**
     * Returns the space diagonal length &mdash; the diagonal through the body
     * of the cube from one vertex to the opposite vertex &mdash; using
     * {@code s * √3}.
     *
     * @return the space diagonal; always &gt; 0
     */
    public double getSpaceDiagonal() {
        return sideLength * Math.sqrt(3.0);
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

    /**
     * Returns the side (edge) length of the cube.
     *
     * @return the side length; always &gt; 0
     */
    public double getSideLength() {
        return sideLength;
    }

    /**
     * Sets the side (edge) length of the cube.
     *
     * @param sideLength the new edge length; must be strictly greater than zero
     * @throws IllegalArgumentException if {@code sideLength} &le; 0
     */
    public void setSideLength(double sideLength) {
        if (sideLength <= 0) {
            throw new IllegalArgumentException(
                    "Cube sideLength must be > 0, but was: " + sideLength);
        }
        this.sideLength = sideLength;
    }

    // -----------------------------------------------------------------------
    // Object overrides
    // -----------------------------------------------------------------------

    /**
     * Returns a cube-specific formatted string extending the base layout with
     * side length, face diagonal, and space diagonal details.
     *
     * @return a formatted multi-line string; never {@code null}
     */
    @Override
    public String toString() {
        return String.format(
                "╔══════════════════════════════════╗%n" +
                "║              CUBE                ║%n" +
                "╠══════════════════════════════════╣%n" +
                "║  Name      : %-20s║%n" +
                "║  Color     : %-20s║%n" +
                "╠══════════════════════════════════╣%n" +
                "║  Side Len  : %-20.4f║%n" +
                "║  Face Diag : %-20.4f║%n" +
                "║  Space Diag: %-20.4f║%n" +
                "╠══════════════════════════════════╣%n" +
                "║  Face Area : %-16.4f sq. units║%n" +
                "║  Volume    : %-16.4f cu. units║%n" +
                "║  Surface   : %-16.4f sq. units║%n" +
                "╚══════════════════════════════════╝",
                getName(), getColor(),
                sideLength, getFaceDiagonal(), getSpaceDiagonal(),
                getFaceArea(), getVolume(), getSurfaceArea());
    }

    /**
     * Compares this cube to another object for equality.
     * Two cubes are equal if their base {@link Shape3D} fields match and
     * their side lengths are identical.
     *
     * @param obj the object to compare with
     * @return {@code true} if the objects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Cube other = (Cube) obj;
        return Double.compare(sideLength, other.sideLength) == 0;
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sideLength);
    }
}