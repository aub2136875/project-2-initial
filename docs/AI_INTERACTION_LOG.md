# AI Interaction Log

This document serves as a log of interactions with AI systems, capturing prompts, responses, and reflections on the outcomes. It is intended to help users track their engagements with AI and improve future interactions.

## Driver
/**
* AI GENERATION DOCUMENTATION
* ===========================
* AI Tool Used: Claude Sonnet 4.6
* Generation Date: 2/21/26
*
* Original Prompt:
* "[Create a sophisticated ShapeDriver.Java that demonstrates: Polymorphism with an Array/List of Shape3D references that hold different shapes, Comparative Analysis for which shape has the largest volume for given constraints, Interactive Features that allow users to create shapes with custom parameters, and Formatted Output for professional presentation of results.]"
*
* Follow-up Prompts (if any):
* 1. "[This line is not working in ShapeDriver, is there a reason for that and how would I go about fixing that?: "private static final List<Shape3D> shapes = new ArrayList<>();" the error message says "Cannot resolve symbol 'Shape3D']"
*
*
* Manual Modifications:
* - [Former Line 72, accepted suggestion to import class highlighted by "Shape3D:" private static final List<Shape3D> shapes = new ArrayList<>();]
* - [I wanted to see if accepting the suggestion would help get rid of the errors.]
*
* - [Former line 206, accepted suggestion to import class highlighted by "Sphere" yield new Sphere(name, color, r);]
* - [It seemed to help with the last error, so I also imported this.]
*
* - [I also imported the classes for the rest of the shapes.]
* - [This seemed to get rid of the rest of the red compiling errors.]
    */

## Shape Classes
/**
* AI GENERATION DOCUMENTATION
* ===========================
* AI Tool Used: [Claude Sonnet 4.6]
* Generation Date: [2/21/26]
*
* Original Prompt:
* "[Would you create five concrete shape classes: 1 is Sphere where properties include radius, 2 is Cube where properties include sideLength, 3 is Cylinder where properties include radius and height, 4 is RectangularPrism where properties include length width and height, and 5 is cone where properties include radius and height. Each shape class must extend Shape3D which implements ThreeDimensionalShape. They must implement the abstract methods from ThreeDimensionalShape. They must include proper constructors with validation. They must have override toString() with shape specific formatting. Also add any shape-specific methods if needed.]"
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
* - Volume formula verified against: [Given Formulas (Mathematical Formulas) and https://brilliant.org/wiki/volume-cone/]
* - Surface area formula verified against: [Given Formula (Mathematical Formulas) and Google + https://brilliant.org/wiki/surface-area-of-a-cone/]
 */

## Shape3D Class
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
* - [This was another issue involving the second ThreeDimensionalShape.java in which the code couldn't compile because of the interface being in the same class.]
*
* Manual Modifications:
* - [I ended up deleting the ThreeDimensionalShape interface which the AI put into the same class which helped fix a lot of problems. This was also a suggestion given by the AI.]
* - [These changes were necessary because there was more than one ThreeDimensionalShape interfaces which meant that the code couldn't compile properly.]
    */

## Test Classes
/**
* AI GENERATION DOCUMENTATION
* ===========================
* AI Tool Used: [Claude Sonnet 4.6]
* Generation Date: [2/21/26]
*
* Original Prompt:
* "[For each shape class, generate JUnit 5 test classes that include: basic functionality including constructor, getters, and setters, Calculation Accuracy for volume and surface area with known values, Boundary Testing for zero values and very small or large numbers, Input Validation for negative values and null inputs, and Inheritance testing with polymorphic behavior verification.]"
*
* Follow-up Prompts (if any):
* 1. "[Here are the issues I got while running the test classes (I copied and pasted the issues), how would I go about fixing these?]"
*
* Manual Modifications:
* - [The solution to the issue above was to replace the shape classes I was using with updated shape classes. I did not make any manual changes to the actual test classes]
* - [The reason I made those manual modification is because it was advised by the AI in order to fix the problems within the test classes. Here is the AI Response: "The fix is to replace every <= 0 guard with a check that explicitly requires the value to be a finite positive number. This needs to be applied to every setter in all five shape classes."]
 */


## Other Prompts
* "[Can you review if the classes made follow proper java naming conventions? If not can you offer suggestions to fix that?]"

I wanted to ask for help reviewing these simple things while I went about checking and testing other things.