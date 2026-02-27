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
 * Represents a three-dimensional rectangular prism (cuboid) defined by
 * a length, width, and height.
 *
 * <p>A rectangular prism has six rectangular faces arranged in three pairs of
 * parallel, congruent rectangles.</p>
 *
 * <p>Geometric formulas used:</p>
 * <ul>
 *   <li><b>Volume</b>          &mdash; {@code l * w * h}</li>
 *   <li><b>Surface Area</b>    &mdash; {@code 2 * (l*w + l*h + w*h)}</li>
 *   <li><b>Space Diagonal</b>  &mdash; {@code √(l² + w² + h²)}</li>
 *   <li><b>Base Area</b>       &mdash; {@code l * w}</li>
 *   <li><b>Face Areas</b>      &mdash; front/back: {@code l*h}, left/right: {@code w*h}</li>
 * </ul>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * RectangularPrism box = new RectangularPrism("ShippingBox", "Brown", 40.0, 30.0, 20.0);
 * System.out.println(box);
 * System.out.printf("Space Diagonal: %.4f%n", box.getSpaceDiagonal());
 * System.out.printf("Base Area     : %.4f%n", box.getBaseArea());
 * }</pre>
 *
 * @author  Your Name
 * @version 1.0
 * @see     Shape3D
 */
public class RectangularPrism extends Shape3D {

    // -----------------------------------------------------------------------
    // Fields
    // -----------------------------------------------------------------------

    /**
     * The length of the rectangular prism.
     * Must be strictly greater than zero.
     */
    private double length;

    /**
     * The width of the rectangular prism.
     * Must be strictly greater than zero.
     */
    private double width;

    /**
     * The height of the rectangular prism.
     * Must be strictly greater than zero.
     */
    private double height;

    // -----------------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------------

    /**
     * Constructs a fully specified {@code RectangularPrism}.
     *
     * @param name   the descriptive name; see {@link Shape3D#setName(String)} for rules
     * @param color  the color; see {@link Shape3D#setColor(String)} for rules
     * @param length the length dimension; must be &gt; 0
     * @param width  the width dimension; must be &gt; 0
     * @param height the height dimension; must be &gt; 0
     * @throws IllegalArgumentException if any dimension &le; 0, or name / color are invalid
     * @throws NullPointerException     if {@code name} or {@code color} is {@code null}
     */
    public RectangularPrism(String name, String color,
                            double length, double width, double height) {
        super(name, color);
        setLength(length);
        setWidth(width);
        setHeight(height);
    }

    /**
     * Constructs a {@code RectangularPrism} with a name and three dimensions;
     * color defaults to {@code "Unspecified"}.
     *
     * @param name   the descriptive name
     * @param length the length dimension; must be &gt; 0
     * @param width  the width dimension; must be &gt; 0
     * @param height the height dimension; must be &gt; 0
     * @throws IllegalArgumentException if any dimension &le; 0 or {@code name} is invalid
     * @throws NullPointerException     if {@code name} is {@code null}
     */
    public RectangularPrism(String name, double length, double width, double height) {
        super(name);
        setLength(length);
        setWidth(width);
        setHeight(height);
    }

    /**
     * Constructs a unit {@code RectangularPrism} (all dimensions = 1) with
     * default name and color.
     */
    public RectangularPrism() {
        super();
        this.length = 1.0;
        this.width  = 1.0;
        this.height = 1.0;
    }

    // -----------------------------------------------------------------------
    // Abstract method implementations
    // -----------------------------------------------------------------------

    /**
     * Computes the volume of the rectangular prism using {@code l * w * h}.
     *
     * @return the volume; always &gt; 0
     */
    @Override
    public double getVolume() {
        return length * width * height;
    }

    /**
     * Computes the total surface area using {@code 2 * (l*w + l*h + w*h)}.
     *
     * @return the total surface area; always &gt; 0
     */
    @Override
    public double getSurfaceArea() {
        return 2.0 * (length * width + length * height + width * height);
    }

    // -----------------------------------------------------------------------
    // Shape-specific methods
    // -----------------------------------------------------------------------

    /**
     * Returns the area of the base (bottom or top) face using {@code l * w}.
     *
     * @return the base area; always &gt; 0
     */
    public double getBaseArea() {
        return length * width;
    }

    /**
     * Returns the area of the front (or back) face using {@code l * h}.
     *
     * @return the front face area; always &gt; 0
     */
    public double getFrontFaceArea() {
        return length * height;
    }

    /**
     * Returns the area of the side (left or right) face using {@code w * h}.
     *
     * @return the side face area; always &gt; 0
     */
    public double getSideFaceArea() {
        return width * height;
    }

    /**
     * Returns the length of the space diagonal (corner-to-corner through the body)
     * using {@code √(l² + w² + h²)}.
     *
     * @return the space diagonal length; always &gt; 0
     */
    public double getSpaceDiagonal() {
        return Math.sqrt(Math.pow(length, 2) + Math.pow(width, 2) + Math.pow(height, 2));
    }

    /**
     * Determines whether this rectangular prism is a cube (all sides equal).
     *
     * @return {@code true} if length, width, and height are all equal
     */
    public boolean isCube() {
        return Double.compare(length, width) == 0 && Double.compare(width, height) == 0;
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

    /**
     * Returns the length dimension.
     *
     * @return the length; always &gt; 0
     */
    public double getLength() {
        return length;
    }

    /**
     * Sets the length dimension.
     *
     * @param length the new length; must be strictly greater than zero
     * @throws IllegalArgumentException if {@code length} &le; 0
     */
    public void setLength(double length) {
        if (!Double.isFinite(length) || length <= 0) {
            throw new IllegalArgumentException(
                    "RectangularPrism length must be a finite positive number, but was: " + length);
        }
        this.length = length;
    }

    /**
     * Returns the width dimension.
     *
     * @return the width; always &gt; 0
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the width dimension.
     *
     * @param width the new width; must be strictly greater than zero
     * @throws IllegalArgumentException if {@code width} &le; 0
     */
    public void setWidth(double width) {
        if (!Double.isFinite(width) || width <= 0) {
            throw new IllegalArgumentException(
                    "RectangularPrism width must be a finite positive number, but was: " + width);
        }
        this.width = width;
    }

    /**
     * Returns the height dimension.
     *
     * @return the height; always &gt; 0
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height dimension.
     *
     * @param height the new height; must be strictly greater than zero
     * @throws IllegalArgumentException if {@code height} &le; 0
     */
    public void setHeight(double height) {
        if (!Double.isFinite(height) || height <= 0) {
            throw new IllegalArgumentException(
                    "RectangularPrism height must be a finite positive number, but was: " + height);
        }
        this.height = height;
    }

    // -----------------------------------------------------------------------
    // Object overrides
    // -----------------------------------------------------------------------

    /**
     * Returns a rectangular-prism-specific formatted string extending the base
     * layout with all three dimensions, face areas, and the space diagonal.
     *
     * @return a formatted multi-line string; never {@code null}
     */
    @Override
    public String toString() {
        return String.format(
                "╔══════════════════════════════════╗%n" +
                "║       RECTANGULAR PRISM          ║%n" +
                "╠══════════════════════════════════╣%n" +
                "║  Name      : %-20s║%n" +
                "║  Color     : %-20s║%n" +
                "╠══════════════════════════════════╣%n" +
                "║  Length    : %-20.4f║%n" +
                "║  Width     : %-20.4f║%n" +
                "║  Height    : %-20.4f║%n" +
                "║  Is Cube?  : %-20s║%n" +
                "╠══════════════════════════════════╣%n" +
                "║  Base Area : %-16.4f sq. units║%n" +
                "║  Front Face: %-16.4f sq. units║%n" +
                "║  Side Face : %-16.4f sq. units║%n" +
                "║  Space Diag: %-20.4f║%n" +
                "╠══════════════════════════════════╣%n" +
                "║  Volume    : %-16.4f cu. units║%n" +
                "║  Surface   : %-16.4f sq. units║%n" +
                "╚══════════════════════════════════╝",
                getName(), getColor(),
                length, width, height, isCube(),
                getBaseArea(), getFrontFaceArea(), getSideFaceArea(), getSpaceDiagonal(),
                getVolume(), getSurfaceArea());
    }

    /**
     * Compares this rectangular prism to another object for equality.
     * Two prisms are equal if their base {@link Shape3D} fields match and
     * all three dimensions are identical.
     *
     * @param obj the object to compare with
     * @return {@code true} if the objects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        RectangularPrism other = (RectangularPrism) obj;
        return Double.compare(length, other.length) == 0 &&
               Double.compare(width,  other.width)  == 0 &&
               Double.compare(height, other.height) == 0;
    }

    /**
     * Returns a hash code consistent with {@link #equals(Object)}.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), length, width, height);
    }
}