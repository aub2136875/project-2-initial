package com.csc205.project2;
/**
 * AI GENERATION DOCUMENTATION
 * ===========================
 * AI Tool Used: Claude Sonnet 4.6
 * Generation Date: 2/21/26
 *
 * Original Prompt:
 * "Create a sophisticated ShapeDriver.Java that demonstrates: Polymorphism with an Array/List of Shape3D references that hold different shapes, Comparative Analysis for which shape has the largest volume for given constraints, Interactive Features that allow users to create shapes with custom parameters, and Formatted Output for professional presentation of results."
 *
 * Follow-up Prompts (if any):
 * 1. "[Refinement prompt 1]"
 * 2. "[Refinement prompt 2]"
 *
 * Manual Modifications:
 * - Former Line 72, accepted suggestion to import class highlighted by "Shape3D:" private static final List<Shape3D> shapes = new ArrayList<>();
 * I wanted to see if accepting the suggestion would help get rid of the errors.
 *
 * - Former line 206, accepted suggestion to import class highlighted by "Sphere" yield new Sphere(name, color, r);
 * It seemed to help with the last error, so I also imported this.
 *
 * - I also imported the classes for the rest of the shapes.
 * This seemed to get rid of the rest of the red compiling errors.
 */
import com.csc205.project2.shapes.Shape3D;
import com.csc205.project2.shapes.Sphere;
import com.csc205.project2.shapes.Cube;
import com.csc205.project2.shapes.Cylinder;
import com.csc205.project2.shapes.RectangularPrism;
import com.csc205.project2.shapes.Cone;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;


/**
 * ============================================================================
 * ShapeDriver.java — Interactive 3D Shape Analysis Console
 * ============================================================================
 *
 * <p>Demonstrates the full power of the Shape3D hierarchy through:</p>
 * <ul>
 *   <li><b>Polymorphism</b>    — a single {@code List<Shape3D>} holds all five
 *                                concrete shape types; every operation iterates
 *                                that list through base-class references only.</li>
 *   <li><b>Comparative Analysis</b> — ranks shapes by volume / surface area,
 *                                     identifies extremes, and computes statistics.</li>
 *   <li><b>Interactive Features</b> — full menu-driven loop lets users create
 *                                     shapes, view the collection, run analyses,
 *                                     and perform fixed-material comparisons.</li>
 *   <li><b>Formatted Output</b> — every screen uses consistent bordered panels,
 *                                 aligned columns, progress bars, and ASCII charts.</li>
 * </ul>
 *
 * <p>Run from the command line:</p>
 * <pre>
 *   javac *.java
 *   java ShapeDriver
 * </pre>
 *
 * @author  Your Name
 * @version 1.0
 */
public class ShapeDriver {

    // =========================================================================
    // Terminal width and border characters
    // =========================================================================

    private static final int    WIDTH       = 70;
    private static final String DOUBLE_LINE = "═".repeat(WIDTH);
    private static final String SINGLE_LINE = "─".repeat(WIDTH);
    private static final String THICK_LINE  = "▓".repeat(WIDTH);

    // =========================================================================
    // Shared shape collection  (polymorphic List<Shape3D>)
    // =========================================================================

    /** The central polymorphic collection — holds any mix of Shape3D subtypes. */
    private static final List<Shape3D> shapes = new ArrayList<>();

    /** Scanner reused throughout the session. */
    private static final Scanner scanner = new Scanner(System.in);

    // =========================================================================
    // Entry point
    // =========================================================================

    /**
     * Program entry point. Seeds a demonstration collection, then launches
     * the interactive menu loop.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        printBanner();
        seedDemoShapes();
        runMainMenu();
        printFooter();
    }

    // =========================================================================
    // SECTION 1 — Main Menu
    // =========================================================================

    /**
     * Drives the top-level interactive menu until the user chooses to exit.
     */
    private static void runMainMenu() {
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter choice", 0, 8);
            switch (choice) {
                case 1  -> viewAllShapes();
                case 2  -> createShapeInteractive();
                case 3  -> runComparativeAnalysis();
                case 4  -> runVolumeSurfaceRanking();
                case 5  -> runMaterialConstraintComparison();
                case 6  -> runStatisticsDashboard();
                case 7  -> runPolymorphismDemo();
                case 8  -> runShapeSearch();
                case 0  -> running = false;
                default -> printError("Invalid option — please try again.");
            }
        }
    }

    /** Renders the styled main menu panel. */
    private static void printMainMenu() {
        System.out.println();
        printDoubleBorder("  ✦  3D SHAPE ANALYSIS CONSOLE  ✦  ");
        printMenuRow("1", "View All Shapes in Collection");
        printMenuRow("2", "Create a New Shape");
        printMenuRow("3", "Comparative Analysis (Volume & Surface Area)");
        printMenuRow("4", "Volume / Surface Area Rankings");
        printMenuRow("5", "Fixed-Material Constraint Comparison");
        printMenuRow("6", "Statistics Dashboard");
        printMenuRow("7", "Polymorphism Demonstration");
        printMenuRow("8", "Search / Filter Shapes");
        printMenuRow("0", "Exit");
        printBottomBorder();
    }

    // =========================================================================
    // SECTION 2 — View All Shapes  (polymorphic iteration)
    // =========================================================================

    /**
     * Displays every shape in the polymorphic list using a consistent card
     * layout.  Only {@link Shape3D} base-class references are used — runtime
     * dispatch resolves the correct overridden methods.
     */
    private static void viewAllShapes() {
        printSectionHeader("SHAPE COLLECTION  [" + shapes.size() + " shapes]");

        if (shapes.isEmpty()) {
            printInfo("The collection is empty. Use option 2 to add shapes.");
            return;
        }

        for (int i = 0; i < shapes.size(); i++) {
            Shape3D s = shapes.get(i);       // ← base-class reference only
            printShapeCard(i + 1, s);
        }
        printBottomBorder();
    }

    /**
     * Prints a compact summary card for one shape through its {@link Shape3D}
     * reference (polymorphic dispatch to overridden getVolume / getSurfaceArea).
     */
    private static void printShapeCard(int index, Shape3D s) {
        System.out.printf("║  #%-3d  %-14s  %-12s  %-10s  Vol: %10.2f  ║%n",
                index,
                truncate(s.getName(), 14),
                truncate(s.getClass().getSimpleName(), 12),
                truncate(s.getColor(), 10),
                s.getVolume());
    }

    // =========================================================================
    // SECTION 3 — Interactive Shape Creation
    // =========================================================================

    /**
     * Guides the user through creating any of the five concrete shapes and
     * appends the result to the shared polymorphic list.
     */
    private static void createShapeInteractive() {
        printSectionHeader("CREATE A NEW SHAPE");

        System.out.println("║  Select shape type:                                                  ║");
        printMenuRow("1", "Sphere");
        printMenuRow("2", "Cube");
        printMenuRow("3", "Cylinder");
        printMenuRow("4", "Rectangular Prism");
        printMenuRow("5", "Cone");
        printMenuRow("0", "Cancel");
        printSingleLine();

        int type = readInt("Shape type", 0, 5);
        if (type == 0) { printInfo("Creation cancelled."); return; }

        String name  = readString("Enter name");
        String color = readString("Enter color");

        try {
            Shape3D newShape = switch (type) {
                case 1 -> {
                    double r = readDouble("  Radius", 0.001, 1_000_000);
                    yield new Sphere(name, color, r);
                }
                case 2 -> {
                    double s = readDouble("  Side length", 0.001, 1_000_000);
                    yield new Cube(name, color, s);
                }
                case 3 -> {
                    double r = readDouble("  Radius", 0.001, 1_000_000);
                    double h = readDouble("  Height", 0.001, 1_000_000);
                    yield new Cylinder(name, color, r, h);
                }
                case 4 -> {
                    double l = readDouble("  Length", 0.001, 1_000_000);
                    double w = readDouble("  Width",  0.001, 1_000_000);
                    double h = readDouble("  Height", 0.001, 1_000_000);
                    yield new RectangularPrism(name, color, l, w, h);
                }
                case 5 -> {
                    double r = readDouble("  Radius", 0.001, 1_000_000);
                    double h = readDouble("  Height", 0.001, 1_000_000);
                    yield new Cone(name, color, r, h);
                }
                default -> throw new IllegalStateException("Unreachable");
            };

            shapes.add(newShape);   // stored as Shape3D — polymorphic!
            printSuccess("Shape '" + newShape.getName() + "' added to collection (#" + shapes.size() + ").");
            printShapeDetailPanel(newShape);

        } catch (IllegalArgumentException e) {
            printError("Validation failed: " + e.getMessage());
        }
    }

    // =========================================================================
    // SECTION 4 — Comparative Analysis
    // =========================================================================

    /**
     * Compares every shape in the collection side-by-side using only
     * polymorphic {@code Shape3D} references.
     */
    private static void runComparativeAnalysis() {
        if (requireShapes()) return;
        printSectionHeader("COMPARATIVE ANALYSIS");

        // Find extremes via polymorphic calls
        Shape3D maxVol = shapes.stream()
                .max(Comparator.comparingDouble(Shape3D::getVolume)).orElseThrow();
        Shape3D minVol = shapes.stream()
                .min(Comparator.comparingDouble(Shape3D::getVolume)).orElseThrow();
        Shape3D maxSA  = shapes.stream()
                .max(Comparator.comparingDouble(Shape3D::getSurfaceArea)).orElseThrow();
        Shape3D minSA  = shapes.stream()
                .min(Comparator.comparingDouble(Shape3D::getSurfaceArea)).orElseThrow();

        double totalVol = shapes.stream().mapToDouble(Shape3D::getVolume).sum();
        double totalSA  = shapes.stream().mapToDouble(Shape3D::getSurfaceArea).sum();
        double avgVol   = shapes.stream().mapToDouble(Shape3D::getVolume).average().orElse(0);
        double avgSA    = shapes.stream().mapToDouble(Shape3D::getSurfaceArea).average().orElse(0);

        // ── Header row ──────────────────────────────────────────────────────
        System.out.printf("║  %-4s  %-14s  %-12s  %12s  %12s  ║%n",
                "#", "Name", "Type", "Volume", "Surface Area");
        printSingleLine();

        // ── Data rows (polymorphic) ──────────────────────────────────────────
        for (int i = 0; i < shapes.size(); i++) {
            Shape3D s  = shapes.get(i);
            String vol = String.format("%,12.2f", s.getVolume());
            String sa  = String.format("%,12.2f", s.getSurfaceArea());
            String tag = buildTag(s, maxVol, maxSA);
            System.out.printf("║  %-4d  %-14s  %-12s  %s  %s  %s║%n",
                    i + 1,
                    truncate(s.getName(), 14),
                    truncate(s.getClass().getSimpleName(), 12),
                    vol, sa, tag);
        }

        // ── Summary block ────────────────────────────────────────────────────
        printSingleLine();
        System.out.printf("║  %-37s %,14.2f  %,12.2f  ║%n", "TOTAL",  totalVol, totalSA);
        System.out.printf("║  %-37s %,14.2f  %,12.2f  ║%n", "AVERAGE", avgVol,   avgSA);
        printSingleLine();
        printLabelValue("Largest Volume",       maxVol.getName() + " (" + maxVol.getClass().getSimpleName() + ")");
        printLabelValue("Smallest Volume",      minVol.getName() + " (" + minVol.getClass().getSimpleName() + ")");
        printLabelValue("Largest Surface Area", maxSA.getName()  + " (" + maxSA.getClass().getSimpleName()  + ")");
        printLabelValue("Smallest Surface Area",minSA.getName()  + " (" + minSA.getClass().getSimpleName()  + ")");
        printBottomBorder();
    }

    /**
     * Builds a short visual tag marking maximum volume / surface area winners.
     */
    private static String buildTag(Shape3D s, Shape3D maxVol, Shape3D maxSA) {
        StringBuilder sb = new StringBuilder();
        if (s == maxVol) sb.append("◆V ");
        if (s == maxSA)  sb.append("◆SA");
        // pad to fixed width so columns stay aligned
        while (sb.length() < 4) sb.append(" ");
        return sb.toString();
    }

    // =========================================================================
    // SECTION 5 — Rankings
    // =========================================================================

    /**
     * Ranks shapes from largest to smallest by volume and surface area,
     * drawing ASCII bar charts for visual impact.
     */
    private static void runVolumeSurfaceRanking() {
        if (requireShapes()) return;
        printSectionHeader("VOLUME & SURFACE AREA RANKINGS");

        List<Shape3D> byVol = shapes.stream()
                .sorted(Comparator.comparingDouble(Shape3D::getVolume).reversed())
                .collect(Collectors.toList());

        List<Shape3D> bySA = shapes.stream()
                .sorted(Comparator.comparingDouble(Shape3D::getSurfaceArea).reversed())
                .collect(Collectors.toList());

        // ── Volume ranking ──────────────────────────────────────────────────
        System.out.println("║  VOLUME RANKING (largest → smallest)                                ║");
        printSingleLine();
        double maxV = byVol.get(0).getVolume();
        for (int i = 0; i < byVol.size(); i++) {
            Shape3D s   = byVol.get(i);
            String  bar = buildBar(s.getVolume(), maxV, 28);
            System.out.printf("║  %2d. %-14s  [%s]  %,10.2f  ║%n",
                    i + 1, truncate(s.getName(), 14), bar, s.getVolume());
        }

        printSingleLine();

        // ── Surface area ranking ─────────────────────────────────────────────
        System.out.println("║  SURFACE AREA RANKING (largest → smallest)                          ║");
        printSingleLine();
        double maxSA = bySA.get(0).getSurfaceArea();
        for (int i = 0; i < bySA.size(); i++) {
            Shape3D s   = bySA.get(i);
            String  bar = buildBar(s.getSurfaceArea(), maxSA, 28);
            System.out.printf("║  %2d. %-14s  [%s]  %,10.2f  ║%n",
                    i + 1, truncate(s.getName(), 14), bar, s.getSurfaceArea());
        }

        // ── Efficiency ratio ─────────────────────────────────────────────────
        printSingleLine();
        System.out.println("║  VOLUME-TO-SURFACE-AREA EFFICIENCY  (higher = more compact)         ║");
        printSingleLine();
        List<Shape3D> byEff = shapes.stream()
                .sorted(Comparator.comparingDouble(
                        (Shape3D s) -> s.getVolume() / s.getSurfaceArea()).reversed())
                .collect(Collectors.toList());

        double maxEff = byEff.get(0).getVolume() / byEff.get(0).getSurfaceArea();
        for (int i = 0; i < byEff.size(); i++) {
            Shape3D s    = byEff.get(i);
            double  eff  = s.getVolume() / s.getSurfaceArea();
            String  bar  = buildBar(eff, maxEff, 28);
            System.out.printf("║  %2d. %-14s  [%s]  %8.4f  ║%n",
                    i + 1, truncate(s.getName(), 14), bar, eff);
        }
        printBottomBorder();
    }

    // =========================================================================
    // SECTION 6 — Fixed-Material Constraint Comparison
    // =========================================================================

    /**
     * Solves the classic "fixed material" optimisation problem: given a fixed
     * surface area budget, which shape encloses the most volume?
     *
     * <p>For each shape type, the algorithm back-calculates the dimensions that
     * exactly consume the budget, then reports the resulting volume.  This
     * demonstrates the mathematical efficiency of different geometries.</p>
     */
    private static void runMaterialConstraintComparison() {
        printSectionHeader("FIXED-MATERIAL CONSTRAINT ANALYSIS");

        double budget = readDouble("  Enter surface area budget (sq. units)", 1.0, 1_000_000_000);
        System.out.println("║                                                                      ║");

        // ── Column header ────────────────────────────────────────────────────
        System.out.printf("║  %-20s  %12s  %12s  %10s  ║%n",
                "Shape Type", "Dimension(s)", "Volume", "SA Used");
        printSingleLine();

        // ── Sphere ───────────────────────────────────────────────────────────
        // SA = 4πr²  →  r = √(SA/(4π))
        double rSphere    = Math.sqrt(budget / (4 * Math.PI));
        Sphere sphereOpt  = new Sphere("OptSphere", "—", rSphere);
        printConstraintRow("Sphere",
                String.format("r=%.4f", rSphere),
                sphereOpt.getVolume(), sphereOpt.getSurfaceArea());

        // ── Cube ─────────────────────────────────────────────────────────────
        // SA = 6s²  →  s = √(SA/6)
        double sCube   = Math.sqrt(budget / 6.0);
        Cube   cubeOpt = new Cube("OptCube", "—", sCube);
        printConstraintRow("Cube",
                String.format("s=%.4f", sCube),
                cubeOpt.getVolume(), cubeOpt.getSurfaceArea());

        // ── Cylinder (r=h optimal) ────────────────────────────────────────────
        // For equal r=h: SA = 2πr²+2πrh = 4πr²  →  r = √(SA/(4π))
        double rCyl    = Math.sqrt(budget / (4 * Math.PI));
        Cylinder cylOpt = new Cylinder("OptCyl", "—", rCyl, rCyl);
        printConstraintRow("Cylinder (r=h)",
                String.format("r=h=%.4f", rCyl),
                cylOpt.getVolume(), cylOpt.getSurfaceArea());

        // ── Cone (optimal half-angle ≈ 30°, r=h√2) ───────────────────────────
        // Full derivation: r ≈ √(SA / (π*(1 + √5)/2)) for Pythagorean optimum
        double rCone = Math.sqrt(budget / (Math.PI * (1 + Math.sqrt(5))));
        double hCone = rCone * Math.sqrt(3);
        Cone   coneOpt = new Cone("OptCone", "—", rCone, hCone);
        printConstraintRow("Cone (optimal angle)",
                String.format("r=%.4f h=%.4f", rCone, hCone),
                coneOpt.getVolume(), coneOpt.getSurfaceArea());

        // ── Rectangular Prism (cube proportion) ──────────────────────────────
        double sRP = Math.sqrt(budget / 6.0);
        RectangularPrism rpOpt = new RectangularPrism("OptRP", "—", sRP, sRP, sRP);
        printConstraintRow("Rect. Prism (cube)",
                String.format("l=w=h=%.4f", sRP),
                rpOpt.getVolume(), rpOpt.getSurfaceArea());

        // ── Winner ─────────────────────────────────────────────────────────
        List<Shape3D> candidates = List.of(sphereOpt, cubeOpt, cylOpt, coneOpt, rpOpt);
        Shape3D winner = candidates.stream()
                .max(Comparator.comparingDouble(Shape3D::getVolume)).orElseThrow();

        printSingleLine();
        printLabelValue("Budget", String.format("%,.2f sq. units", budget));
        printLabelValue("Winner", winner.getClass().getSimpleName()
                + "  →  Volume = " + String.format("%,.4f", winner.getVolume()));
        printLabelValue("Conclusion",
                "The sphere always encloses the most volume for a given surface area.");
        printBottomBorder();
    }

    /** Prints one row of the constraint comparison table. */
    private static void printConstraintRow(String type, String dims,
                                            double volume, double sa) {
        System.out.printf("║  %-20s  %-12s  %,12.2f  %,10.2f  ║%n",
                truncate(type, 20), truncate(dims, 12), volume, sa);
    }

    // =========================================================================
    // SECTION 7 — Statistics Dashboard
    // =========================================================================

    /**
     * Computes and displays descriptive statistics across the polymorphic
     * collection: min, max, mean, median, standard deviation, and a
     * type-distribution breakdown.
     */
    private static void runStatisticsDashboard() {
        if (requireShapes()) return;
        printSectionHeader("STATISTICS DASHBOARD");

        double[] vols = shapes.stream().mapToDouble(Shape3D::getVolume).sorted().toArray();
        double[] sas  = shapes.stream().mapToDouble(Shape3D::getSurfaceArea).sorted().toArray();

        System.out.printf("║  %-24s  %,16s  %,16s  ║%n", "Statistic", "Volume", "Surface Area");
        printSingleLine();
        printStatRow("Count",            shapes.size(),                     shapes.size());
        printStatRow("Minimum",          vols[0],                           sas[0]);
        printStatRow("Maximum",          vols[vols.length - 1],             sas[sas.length - 1]);
        printStatRow("Mean",             mean(vols),                        mean(sas));
        printStatRow("Median",           median(vols),                      median(sas));
        printStatRow("Std. Deviation",   stdDev(vols),                      stdDev(sas));
        printStatRow("Range",            vols[vols.length-1] - vols[0],     sas[sas.length-1] - sas[0]);
        printSingleLine();

        // ── Type distribution ─────────────────────────────────────────────
        System.out.println("║  SHAPE TYPE DISTRIBUTION                                             ║");
        printSingleLine();
        Map<String, Long> typeCount = shapes.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getClass().getSimpleName(), Collectors.counting()));

        typeCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(e -> {
                    String bar = buildBar(e.getValue(), shapes.size(), 20);
                    System.out.printf("║  %-16s  [%s]  %2d / %-2d  (%5.1f%%)  ║%n",
                            e.getKey(), bar, e.getValue(), shapes.size(),
                            100.0 * e.getValue() / shapes.size());
                });

        printBottomBorder();
    }

    private static void printStatRow(String label, double v, double sa) {
        System.out.printf("║  %-24s  %,16.4f  %,16.4f  ║%n", label, v, sa);
    }

    private static void printStatRow(String label, long v, long sa) {
        System.out.printf("║  %-24s  %,16d  %,16d  ║%n", label, v, sa);
    }

    // =========================================================================
    // SECTION 8 — Polymorphism Demonstration
    // =========================================================================

    /**
     * Walks through the classic polymorphism demonstration: the same method
     * call on a {@code Shape3D} reference produces type-correct results at
     * runtime without any casts or instanceof checks.
     */
    private static void runPolymorphismDemo() {
        printSectionHeader("POLYMORPHISM DEMONSTRATION");

        System.out.println("║  All operations below use ONLY a Shape3D base-class reference.      ║");
        System.out.println("║  Runtime dispatch selects the correct override automatically.       ║");
        printSingleLine();

        // Build a demonstration list with one of each type
        List<Shape3D> demo = new ArrayList<>();
        demo.add(new Sphere("DemoSphere",   "Cyan",   6.0));
        demo.add(new Cube("DemoCube",       "Gold",   6.0));
        demo.add(new Cylinder("DemoCyl",    "Navy",   3.0, 12.0));
        demo.add(new RectangularPrism("DemoRP", "Teal", 4.0, 5.0, 6.0));
        demo.add(new Cone("DemoCone",       "Coral",  4.0, 9.0));

        System.out.printf("║  %-16s  %-14s  %-12s  %-12s  ║%n",
                "Reference (Shape3D)", "Actual Type", "Volume", "Surface Area");
        printSingleLine();

        // ── Key polymorphism showcase: ONE loop, ONE variable type ──────────
        for (Shape3D shape : demo) {                // shape is declared Shape3D
            System.out.printf("║  %-16s  %-14s  %,12.2f  %,12.2f  ║%n",
                    truncate(shape.getName(), 16),
                    shape.getClass().getSimpleName(),
                    shape.getVolume(),              // polymorphic dispatch
                    shape.getSurfaceArea());        // polymorphic dispatch
        }

        printSingleLine();

        // ── describeVolume() — inherited concrete method on base class ───────
        System.out.println("║  Inherited describeVolume() — dispatches via base-class reference:  ║");
        printSingleLine();
        for (Shape3D shape : demo) {
            System.out.printf("║    %-14s  →  %-40s  ║%n",
                    truncate(shape.getClass().getSimpleName(), 14),
                    truncate(shape.describeVolume(), 40));
        }

        // ── Sorting by volume using only base-class comparator ───────────────
        printSingleLine();
        System.out.println("║  Sorted by volume (Comparator on base-class method only):           ║");
        printSingleLine();
        demo.sort(Comparator.comparingDouble(Shape3D::getVolume).reversed());
        for (int i = 0; i < demo.size(); i++) {
            Shape3D s = demo.get(i);
            System.out.printf("║    %d. %-16s  volume = %,12.2f  %-14s  ║%n",
                    i + 1,
                    truncate(s.getName(), 16),
                    s.getVolume(),
                    "(" + s.getClass().getSimpleName() + ")");
        }

        printBottomBorder();
    }

    // =========================================================================
    // SECTION 9 — Search / Filter
    // =========================================================================

    /**
     * Filters the polymorphic collection by shape type and / or volume range,
     * demonstrating Stream-based polymorphic queries.
     */
    private static void runShapeSearch() {
        if (requireShapes()) return;
        printSectionHeader("SEARCH & FILTER SHAPES");

        System.out.println("║  Filter options:                                                     ║");
        printMenuRow("1", "Filter by shape type");
        printMenuRow("2", "Filter by volume range");
        printMenuRow("3", "Filter by surface area range");
        printMenuRow("4", "Find shapes by color");
        printSingleLine();

        int choice = readInt("Filter type", 1, 4);
        List<Shape3D> results;

        switch (choice) {
            case 1 -> {
                System.out.println("║  Types: Sphere / Cube / Cylinder / RectangularPrism / Cone          ║");
                String type = readString("  Enter type name");
                results = shapes.stream()
                        .filter(s -> s.getClass().getSimpleName()
                                .equalsIgnoreCase(type.trim()))
                        .collect(Collectors.toList());
            }
            case 2 -> {
                double min = readDouble("  Minimum volume", 0, Double.MAX_VALUE);
                double max = readDouble("  Maximum volume", min, Double.MAX_VALUE);
                results = shapes.stream()
                        .filter(s -> s.getVolume() >= min && s.getVolume() <= max)
                        .collect(Collectors.toList());
            }
            case 3 -> {
                double min = readDouble("  Minimum surface area", 0, Double.MAX_VALUE);
                double max = readDouble("  Maximum surface area", min, Double.MAX_VALUE);
                results = shapes.stream()
                        .filter(s -> s.getSurfaceArea() >= min && s.getSurfaceArea() <= max)
                        .collect(Collectors.toList());
            }
            case 4 -> {
                String color = readString("  Enter color");
                results = shapes.stream()
                        .filter(s -> s.getColor().equalsIgnoreCase(color.trim()))
                        .collect(Collectors.toList());
            }
            default -> results = new ArrayList<>();
        }

        printSingleLine();
        if (results.isEmpty()) {
            printInfo("No shapes matched the filter criteria.");
        } else {
            System.out.printf("║  Found %d matching shape(s):%-42s║%n", results.size(), "");
            printSingleLine();
            results.forEach(s -> printShapeDetailPanel(s));
        }
        printBottomBorder();
    }

    // =========================================================================
    // HELPER — Shape Detail Panel
    // =========================================================================

    /**
     * Renders a detailed information panel for one shape, using only the
     * {@link Shape3D} base-class API plus a runtime class name query.
     *
     * @param s the shape to display (received as {@code Shape3D} reference)
     */
    private static void printShapeDetailPanel(Shape3D s) {
        System.out.println("║                                                                      ║");
        printSingleLine();
        System.out.printf( "║  ▶  %-20s  Type: %-16s  Color: %-8s  ║%n",
                s.getName(), s.getClass().getSimpleName(), truncate(s.getColor(), 8));
        printSingleLine();
        System.out.printf( "║     %-30s  %,20.4f cu. units  ║%n",
                "Volume:",       s.getVolume());
        System.out.printf( "║     %-30s  %,20.4f sq. units  ║%n",
                "Surface Area:", s.getSurfaceArea());
        System.out.printf( "║     %-30s  %,20.4f           ║%n",
                "V/SA Efficiency:", s.getVolume() / s.getSurfaceArea());
        // ── Type-specific extra row via instanceof (narrowing, explicit) ─────
        printTypeSpecificDetails(s);
        printSingleLine();
    }

    /**
     * Appends one shape-specific measurement row by narrowing the
     * {@code Shape3D} reference with {@code instanceof}.
     */
    private static void printTypeSpecificDetails(Shape3D s) {
        if (s instanceof Sphere sp) {
            System.out.printf("║     %-30s  %,20.4f units      ║%n",
                    "Diameter:", sp.getDiameter());
        } else if (s instanceof Cube c) {
            System.out.printf("║     %-30s  %,20.4f units      ║%n",
                    "Space Diagonal:", c.getSpaceDiagonal());
        } else if (s instanceof Cylinder cy) {
            System.out.printf("║     %-30s  %,20.4f sq. units  ║%n",
                    "Lateral Surface Area:", cy.getLateralSurfaceArea());
        } else if (s instanceof RectangularPrism rp) {
            System.out.printf("║     %-30s  %,20.4f units      ║%n",
                    "Space Diagonal:", rp.getSpaceDiagonal());
        } else if (s instanceof Cone co) {
            System.out.printf("║     %-30s  %,20.4f units      ║%n",
                    "Slant Height:", co.getSlantHeight());
        }
    }

    // =========================================================================
    // SEED DATA
    // =========================================================================

    /**
     * Pre-loads a varied set of shapes into the polymorphic collection so
     * the user can explore all features without creating shapes manually.
     */
    private static void seedDemoShapes() {
        shapes.add(new Sphere("EarthModel",   "Blue",         6_371.0));
        shapes.add(new Sphere("GolfBall",     "White",        2.14));
        shapes.add(new Cube("RubiksCube",     "Multicolor",   5.7));
        shapes.add(new Cube("ShippingPallet", "Tan",          120.0));
        shapes.add(new Cylinder("OilDrum",    "Silver",       27.5, 88.0));
        shapes.add(new Cylinder("Soda Can",   "Red",          3.3,  12.2));
        shapes.add(new RectangularPrism("Shoebox",   "Brown", 30.0, 15.0, 12.0));
        shapes.add(new RectangularPrism("Aquarium",  "Clear", 90.0, 40.0, 50.0));
        shapes.add(new Cone("TrafficCone",    "Orange",       15.0, 45.0));
        shapes.add(new Cone("IceCreamCone",   "Beige",        3.0,  10.0));

        printSuccess("Demo collection loaded: " + shapes.size() + " shapes ready.");
    }

    // =========================================================================
    // STATISTICS HELPERS
    // =========================================================================

    private static double mean(double[] arr) {
        return Arrays.stream(arr).average().orElse(0);
    }

    private static double median(double[] sorted) {
        int n = sorted.length;
        return (n % 2 == 0)
                ? (sorted[n / 2 - 1] + sorted[n / 2]) / 2.0
                : sorted[n / 2];
    }

    private static double stdDev(double[] arr) {
        double avg = mean(arr);
        return Math.sqrt(Arrays.stream(arr)
                .map(x -> (x - avg) * (x - avg))
                .average().orElse(0));
    }

    // =========================================================================
    // I/O HELPERS
    // =========================================================================

    /** Reads a validated integer from stdin in [min, max]. */
    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.printf("║  %s [%d-%d]: ", prompt, min, max);
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) return val;
                printError("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                printError("Invalid input — integer required.");
            }
        }
    }

    /** Reads a validated double from stdin in (min, max]. */
    private static double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.printf("║  %s: ", prompt);
            try {
                double val = Double.parseDouble(scanner.nextLine().trim());
                if (val > min && val <= max) return val;
                printError("Value must be > " + min + " and ≤ " + String.format("%,.0f", max));
            } catch (NumberFormatException e) {
                printError("Invalid input — number required.");
            }
        }
    }

    /** Reads a non-blank string from stdin. */
    private static String readString(String prompt) {
        while (true) {
            System.out.printf("║  %s: ", prompt);
            String val = scanner.nextLine().trim();
            if (!val.isEmpty() && val.length() <= 100) return val;
            printError("Input must be 1–100 characters.");
        }
    }

    // =========================================================================
    // DISPLAY HELPERS
    // =========================================================================

    /** Returns true (and prints a message) if the collection is empty. */
    private static boolean requireShapes() {
        if (shapes.isEmpty()) {
            printInfo("No shapes in collection. Use option 2 to add shapes.");
            return true;
        }
        return false;
    }

    /** Builds a proportional ASCII bar of specified width. */
    private static String buildBar(double value, double maxValue, int width) {
        int filled = (maxValue > 0) ? (int) Math.round((value / maxValue) * width) : 0;
        filled = Math.min(filled, width);
        return "█".repeat(filled) + "░".repeat(width - filled);
    }

    /** Truncates a string to maxLen, appending "…" if needed. */
    private static String truncate(String s, int maxLen) {
        if (s == null) return "";
        return (s.length() <= maxLen) ? s : s.substring(0, maxLen - 1) + "…";
    }

    // ── Formatted print helpers ───────────────────────────────────────────────

    private static void printBanner() {
        System.out.println();
        System.out.println(THICK_LINE);
        System.out.printf( "  %-68s%n",
                "  3 D   S H A P E   A N A L Y S I S   C O N S O L E");
        System.out.printf( "  %-68s%n", "  Polymorphism · Analysis · Interactive · Formatted Output");
        System.out.println(THICK_LINE);
    }

    private static void printFooter() {
        System.out.println();
        System.out.println(THICK_LINE);
        System.out.printf("  %-68s%n", "  Session ended. Goodbye!");
        System.out.println(THICK_LINE);
    }

    private static void printDoubleBorder(String title) {
        System.out.println("╔" + DOUBLE_LINE + "╗");
        System.out.printf( "║  %-" + (WIDTH - 2) + "s║%n", title);
        System.out.println("╠" + DOUBLE_LINE + "╣");
    }

    private static void printSectionHeader(String title) {
        System.out.println();
        System.out.println("╔" + DOUBLE_LINE + "╗");
        System.out.printf( "║  %-" + (WIDTH - 2) + "s║%n", title);
        System.out.println("╠" + "─".repeat(WIDTH) + "╣");
    }

    private static void printSingleLine() {
        System.out.println("╠" + SINGLE_LINE + "╣");
    }

    private static void printBottomBorder() {
        System.out.println("╚" + DOUBLE_LINE + "╝");
    }

    private static void printMenuRow(String key, String label) {
        System.out.printf("║  [%s]  %-" + (WIDTH - 7) + "s║%n", key, label);
    }

    private static void printLabelValue(String label, String value) {
        System.out.printf("║  %-22s  %-" + (WIDTH - 26) + "s║%n", label + ":", value);
    }

    private static void printInfo(String msg) {
        System.out.printf("║  ℹ  %-" + (WIDTH - 5) + "s║%n", msg);
    }

    private static void printSuccess(String msg) {
        System.out.printf("║  ✔  %-" + (WIDTH - 5) + "s║%n", msg);
    }

    private static void printError(String msg) {
        System.out.printf("║  ✖  %-" + (WIDTH - 5) + "s║%n", msg);
    }
}