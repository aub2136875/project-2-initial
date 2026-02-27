# Reflections Log

This document serves as a log of reflections on various topics, capturing insights, lessons learned, and personal growth. It is intended to help users track their thoughts and experiences over time.

### Mathematical Verification:

While reviewing the mathematical formulas for volume and surface area for each of the 3D shapes, the formulas seemed to match up with the provided mathematical formulas in the README.md. While reviewing the added Cone class mathematical formulas, I noticed that the volume formula matched up with the resource used but the surface area formula was a bit different. I took the given formula from the AI and looked it up, and it does seem to be a working formula for the surface area of a cone.

- Test calculations with known values (use online calculators for verification)
I did test calculations by running the ShapeDriver class, inputting values for different 3D shapes, and then using an online calculator to compare volume and surface area values using the same inputs. I did a test for each shape and the answers matched up. The only difference being that the code rounded to four decimal places while the calculator rounded to 1.

I did test calculations for the surface area and volume of each shape using ShapeDriver and compared the answers to online resources. I went through each of the shapes one by one and input different shapes.
### Code Quality Review
- While reviewing for proper naming conventions I found that: The class and interface names are using PascalCase. The instance fields are using camelCase. The method names are using camelCase. The code style seems fairly consistent across the classes.
- While checking with the test classes there were errors. I asked AI about those problems and it offered a solution while explaining what the issue was. One I implimented those fixes, the test classes worked correctly and 292 tests passed.
- There was an issue where I had to import each of the shape classes to ShapeDriver in order for it to work. This was the only issue that the AI didn't provide a solution to, but Intellij actually offered this solution which helped fix the problem.
- There was an issue where Shape3D also had the ThreeDimensionalShape interface in the same class which caused some issues. The AI offered a good solution to the problem and after that, the code was able to compile correctly.

### AI Output Analysis

- Compare outputs from different AI tools.
Claude seemed to be a bit better than Chatgpt at reviewing its own code and offering suggestions to fix issues. Although Chatgpt is good for asking general questions and explanations.

- Identify areas where AI struggled or excelled
The AI actually did really good at helping to fix identified issues and offering solutions that worked. Although it does need to be told where the problems are and what issues there are that makes sense. The AI also did well explaining things asked or pointed out. I suppose the AI "struggled" a bit making the actual classes as there were mistakes and compiling errors as well as some duplicate code. It still did well identifying solutions to those issues.

* Manual Modifications (Driver Class):
* - [Former Line 72, accepted suggestion to import class highlighted by "Shape3D:" private static final List<Shape3D> shapes = new ArrayList<>();]
* - [I wanted to see if accepting the suggestion would help get rid of the errors.]
*
* - [Former line 206, accepted suggestion to import class highlighted by "Sphere" yield new Sphere(name, color, r);]
* - [It seemed to help with the last error, so I also imported this.]
*
* - [I also imported the classes for the rest of the shapes.]
* - [This seemed to get rid of the rest of the red compiling errors.]
* Manual Modifications (Shape Classes):
* - [I replaced the shape classes with updated shape classes from the AI after finding issues within the test classes which I asked for the AI to help resolve.]
* - [These changes were necessary because they were required in order to have the test classes run without issues.]
* Manual Modifications(Shape3D Class):
* - [I ended up deleting the ThreeDimensionalShape interface which the AI put into the same class which helped fix a lot of problems. This was also a suggestion given by the AI.]
* - [These changes were necessary because there was more than one ThreeDimensionalShape interfaces which meant that the code couldn't compile properly.]

### Reflection Questions
* AI Effectiveness: Where did AI tools excel? Where did they struggle?

AI tools did well giving breakdowns about different things. I think it was decently thorough in explaining things and if I had questions it did a pretty good job giving explanations that made sense. The biggest issue I had was needing to import some things in order for the code to compile properly, but I didn't have too many issues with the generated code so far.
* Code Quality Comparison: How does AI-generated code compare to manual coding?

AI generated code is definitely a lot easier and faster than manual coding. The AI did a lot of the heavy work that would've taken a lot longer to type out. However, there is a possibility of it being harder to catch mistakes in code because you would have to go through an entire thing of code compared to if you manually code and consistently check for things as you code. The code generated by AI is also a lot more complicated than what a beginner coder would code manually.
* Learning Experience: What did you learn about inheritance AND AI-assisted development?

I learned that AI assisted coding focuses on more on reviewing and testing compared to manually coding which also involves problem-solving skills and coming up with ideas of code to create and implement. Though AI assisted development also does require problem-solving for mistakes made by the AI and compile errors. Of course manual coding requires reviewing and testing, but with AI assisted development you sort of skip to that part. AI assisted development also requires you to be good at creating a prompt in order to fulfill your request properly. This project used inheritance in order to declare method signatures without implementation for each of the shapes. That way we can define different things like volume and surface area without needing to specify what shape it is.
* Validation Process: How did you ensure the AI-generated code was correct?

To ensure that the AI code was correct I some testing, checking, and comparing information. I also asked the AI questions about the code it generated and asked it to review things and explain things to me in order to help me validate it. I made an example for each shape and compared it to google's shape volume and surface area formula calculators.
* Future Applications: How will you use AI tools in future programming projects?

I think AI tools can be really useful to ask questions and help understand certain code. For example if you get stuck on something and are not too sure how to fix the problem you could see if AI can help come up with possible solutions. You can also use it to explain things so they make more sense.