# A1 Rubric Explanation

This document is part of your first submission.  Complete it and include it in your submission zip, alongside your parser, example programs.

## How it works

The rubric explanation is the set of questions below.  The first set, the basic questions, is graded directly and is worth 10\% of your marks for this submission.  Answer them accurately to earn those marks.

The remaining sections ask one question for each of the other rubric items.  These are not graded directly, but your answers help your marker award you the marks for each rubric item, so write your answers below each question text in markdown format and point your marker to where the evidence lives in your submission.

## Basic questions (10)

1. Which chapter of the book did you use as the starting point for your solution?

### Your answer

I started with chapter 6 from the book as my starting point for my solution ( This is after working through from Chapter 4 in class on my own ). I started and continued developing in accordance to book while adding my own flow literal alongside operators for it such as -> and &.

2. What is the "working folder", and what command(s) compile your parser?

### Your answer

navigate to src folder using cd in comp_3000_interpreter in an interpreter or command line.
then run:
javac ./lox/*.java

then while in the src folder, run:
java lox.Lox

3. What literal in your language represents a river that gets 10L/s of flow on the first day after 1mm of rainfall?

### Your answer

{{F : 10, R: 1}}
In my language F represents flow and R is rainfall on first day. I can add identifiers for any and all purposes in this format and it's more readable and intuitive for me than symbols.
I think this is good. I can also add arrays instead of numbers for more specific "flow fields"

4. What symbol in your language shows two rivers combine, and is it a "unary", "binary", or "literal"?

### Your answer

river1 & river2
The & symbol shows two rivers combining.
It is binary
AST: (& river1 river2)

5. Does your language include statements, or is it an expression language?

### Your answer

It's just an expression language for now currently, it will contain statements in the future when I implement it
I have planned for it to include statements yes.

6. In your language, how long does it take all the water to work through a river system after 1 day of rain?

### Your answer

Depends on how much water has entered the system through the rain and how long the river system is. Short systems takes hours while longer ones take a few days. This is hw my language will work.
This is however just the plan I have for the language.
Currently the parser does not evaluate this.
The current Submission One language does not simulate water flow over time, so the parser itself does not determine how long water takes to move through the system. The 10-day assumption in the assignment specifies that rainfall water makes its way into the river within 10 days; simulation of flows over time is part of Submission Two.

## Log-book submissions (10)

Which file in the zip are your log-book entries and when did you make them?  Your teacher needs to have seen them during the semester.

### Your answer

Inside the LogBook folder I have my logbooks. Each week is a text file in which I have put my thoughts, work, ideas and log in.

## Grammar given in the document in Nystrom's notation (20)

Provide the grammar for your language, and how does each of your example programs parse according to it?

### Your answer

expression        → riverConnection ;

riverConnection   → riverCombination ( "->" riverCombination )* ;

riverCombination  → equality ( "&" equality )* ;

equality          → comparison ( ( "!=" | "==" ) comparison )* ;

comparison        → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;

term              → factor ( ( "-" | "+" ) factor )* ;

factor            → unary ( ( "/" | "*" ) unary )* ;

unary             → ( "!" | "-" ) unary
                   | primary ;

primary            → NUMBER
                   | STRING
                   | IDENTIFIER
                   | "false"
                   | "true"
                   | "nil"
                   | "(" expression ")"
                   | flowLiteral ;

flowLiteral        → "{{" flowField ( "," flowField )* "}}" ;

flowField          → IDENTIFIER ":" expression ;

## Three example programs (20)

Provide your three example programs here and identify which files in your zip contain them.

### Your answer

The example programs are the following and in their own respective file names.
Currently the assignment statements do not work and are just for show, the parser ignores them. Expressions are working however. It's just to give background on the performed actions. Rivers here can be modelled or name after real rivers and given the appropriate features using my notation.
I have also added the AST output

example1.flow: This is to show the flow literal works
{{F: 10, R: 1}}

AST:
{R: 1.0, F: 10.0}

example2.flow: This is to show that the & works with the flow literals
river1 = {{F: 5, R: 1}}
river2 = {{F: 50, R: 1}}

{{F: 5, R: 1}} & {{F: 50, R: 1}}

AST:
(& {R: 1.0, F: 5.0} {R: 1.0, F: 50.0})

example3.flow: Shows that you can layer operators ( -> and &) with my literal 
river1 = {{F: 5, R: 1}}
river2 = {{F: 50, R: 1}}
river3 = {{F: 80}}

({{F: 5, R: 1}} & {{F: 50, R: 1}}) -> {{F: 80}}

AST:
(-> (group (& {R: 1.0, F: 5.0} {R: 1.0, F: 50.0})) {F: 80.0})

Explanation:
expression
→ riverConnection
→ riverCombination -> riverCombination
→ (riverCombination & riverCombination) -> riverCombination
→ (flowLiteral & flowLiteral) -> flowLiteral

## Parser written in Java based on Lox codebase (20)

Which chapter of the book is your parser based on?  What did you add beyond the Chapter 6 code, and where is that explained?

### Your answer

My work is based on Chapter 4 to Chapter 6 for Assignment/ Submission One. It contains abit of Chapter 7 as we have worked on it in class before the semester break. But it is not activated and only the parser is being used alonside AST Printer.
My Logbooks explain my thought process and working throughout the week. I implemented almost all of the code and most of the ideas myself.
The code in the src folder explains my workings as the code directly shows what is different from the code in Lox Interpreters book.
After Chapter 6 I modifier the TokenType, parser, scanner, ast printer and expr java files.

The scanner now handles new token types: FLOW_START, FLOW_END, ARROW, AMPERSAND to be used for my literal
The parser handles the new tokens and creates the new literal and handles it. I use a map for the fields in the literal
The ast printer has been modified to pretty print the map which couldn't handle map fields for my flow literal before now it prints it properly

My parser is primarily based on Chapters 4 to 6 of Crafting Interpreters. I worked through the earlier chapters as part of developing the interpreter, and Chapter 6 provided the main starting point for the parser used in Submission One. Some Chapter 7 code is also present from work done in class, but it is not being used for the Submission One parser.

I made changes to several parts of the Lox codebase, including TokenType, the scanner, parser, Expr, and AstPrinter.

The scanner was extended to recognise the new tokens used by my river language, including FLOW_START, FLOW_END, COLON, AMPERSAND, and ARROW.

The parser was extended with new parsing rules for my river language. I added flowLiteral() to parse flow literals in the form:

{{IDENTIFIER : EXPRESSION}}

I also added parsing for the & operator to represent combining rivers and the -> operator to represent connecting one river expression to another. Parentheses can also be used to group river expressions.

For the flow literal, I use a Map<String, Expr> to store the named fields, such as F for flow and R for rainfall.

I also modified Expr and AstPrinter to support the additional expression type and to print the flow literal fields correctly. The AST output is used in my examples to demonstrate that the parser is reading the new river expressions correctly.

My logbooks contain my development process and explain my ideas and work throughout the assignment. The source code in the src folder also shows the changes I made to the original Lox codebase.

## Uniqueness and Creativity (20)

What did you do beyond the in-class work?  Point your marker to where it lives in your submission.

### Your answer

Planned and finished the parser ( including group github base book parser ). Suggested ideas and plans for language.
Decided to use map for my literal fields
{{IDENTIFIER : EXPRESSION}}
since it best represents it.

I designed my own syntax for representing river flow information rather than using the exact syntax from the assignment's example.

My main design choice was to create a flow literal using named fields:

{{IDENTIFIER : EXPRESSION}}

For example:

{{F: 10, R: 1}}

This allows the literal to represent different properties using readable field names. I use a Map to store these fields.

I also designed the & operator to represent two rivers combining and the -> operator to represent a connection between river expressions. These operators can be combined and grouped using parentheses, allowing expressions such as:

({{F: 5, R: 1}} & {{F: 50, R: 1}}) -> {{F: 80}}

I implemented these language features in the scanner, parser, expression classes and AST printer, and tested them using my example programs and their AST output.

My logbooks also document my ideas and development throughout the assignment.
