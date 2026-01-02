Chapter 1 Challenge 1.2 - Winning Lottery Ticket Analyzer
Overview
This Java program analyzes a set of lottery ticket numbers to determine which ticket has the highest average digit value. Each ticket is represented as a string with digits separated by hyphens (e.g., "12-34-56-78-90"). The program processes each ticket by removing hyphens, summing the individual digits, calculating the average, and identifying the ticket with the highest average.
Project Structure

File: Chapter1_challenge_1_2.java
Package: com.mycompany.chapter1_challenge_1_2
Main Class: Chapter1_challenge_1_2

How It Works

The program defines an array of ticket numbers:
javaString[] winningNumbers = {"12-34-56-78-90", "33-44-11-66-22", "01-02-03-04-05"};

It iterates through each ticket:

Prints the ticket being analyzed.
Removes hyphens using replace("-", "").
Converts the cleaned string into a character array.
Sums the numeric values of each digit using Character.getNumericValue().
Calculates the average by dividing the sum by the number of digits.
Prints the sum and average for the current ticket.


Tracks the highest average and the corresponding ticket.
After processing all tickets, prints the winning ticket with the highest average.

Example Output
textAnalyzing: 12-34-56-78-90
Digit Sum: 60, Digit Average: 6.0
Analyzing: 33-44-11-66-22
Digit Sum: 32, Digit Average: 3.2
Analyzing: 01-02-03-04-05
Digit Sum: 15, Digit Average: 1.5

The winning number with the highest average is: 12-34-56-78-90 with an average of 6.0
Prerequisites

Java Development Kit (JDK) 8 or higher
A Java IDE (e.g., NetBeans, IntelliJ IDEA) or command-line tools (javac, java)

How To Run


1,Place the Java file in the appropriate package directory:

com/mycompany/chapter1_challenge_1_2/Chapter1_challenge_1_2.java

2,Place the Java file in the appropriate package directory:

javac com/mycompany/chapter1_challenge_1_2/Chapter1_challenge_1_2.java

3, Execute the program:

java com.mycompany.chapter1_challenge_1_2.Chapter1_challenge_1_2

4, Observe the analysis output and the final winning ticket.

Key Features

Hyphen Removal: Cleans ticket strings for digit processing.
Digit Summation: Uses Character.getNumericValue() to safely convert characters to integers.
Average Calculation: Computes the mean digit value with floating-point precision.
Comparison Logic: Identifies the ticket with the maximum average using a simple tracking mechanism.
Notes

All tickets in the example have exactly 10 digits (5 pairs separated by hyphens), ensuring consistent average calculations.
The program assumes valid input format (digits and hyphens only). Invalid characters may cause unexpected behavior.
Leading zeros (e.g., "01") are handled correctly as digits.
The output includes intermediate analysis for transparency.

