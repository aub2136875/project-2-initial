package com.csc205.project2.shapes;


// =============================================================================
// ThreeDimensionalShape interface (included for completeness / compilation)
// =============================================================================

/**
 * Contract that all three-dimensional shapes must fulfill.
 *
 * <p>Implementing classes must provide calculations for volume and surface area,
 * as well as human-readable descriptions of each measurement.</p>
 *
 * @author  Your Name
 * @version 1.0
 */
interface ThreeDimensionalShape {

    /**
     * Computes the volume of the shape.
     *
     * @return the volume as a non-negative {@code double}
     */
    double getVolume();

    /**
     * Computes the surface area of the shape.
     *
     * @return the surface area as a non-negative {@code double}
     */
    double getSurfaceArea();

    /**
     * Returns a human-readable description of the volume.
     *
     * @return a formatted volume string
     */
    String describeVolume();

    /**
     * Returns a human-readable description of the surface area.
     *
     * @return a formatted surface area string
     */
    String describeSurfaceArea();
}