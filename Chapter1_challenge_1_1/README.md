Chapter 1 Challenge 1.1 - Cryptic Message Decoder
Overview
This Java program decodes a cryptic message represented as an integer by performing specific operations on its digits. The program extracts the first, last, second, and second-to-last digits of the input number, computes the product of the first and last digits, and the sum of the second and second-to-last digits. The result is returned as a concatenated string of these two values.
Project Structure

File: Chapter1_challenge_1_1.java
Package: com.mycompany.chapter1_challenge_1_1
Main Class: Chapter1_challenge_1_1

How It Works

The program takes an integer input (e.g., 13579) representing the cryptic message.
The decodeCrypticMessage method:
Determines the number of digits in the input using Math.log10.
Extracts:
The first digit (e.g., 1 from 13579).
The last digit (e.g., 9 from 13579).
The second digit (e.g., 3 from 13579).
The second-to-last digit (e.g., 7 from 13579).


Computes:
The product of the first and last digits (e.g., 1 * 9 = 9).
The sum of the second and second-to-last digits (e.g., 3 + 7 = 10).


Returns the product and sum concatenated as a string (e.g., "910").


The main method calls decodeCrypticMessage with a sample input (13579) and prints the result.

Example
For the input 13579:

First digit: 1
Last digit: 9
Second digit: 3
Second-to-last digit: 7
Product: 1 * 9 = 9
Sum: 3 + 7 = 10
Output: "910"

The program prints: The decrypted code is: 910
Prerequisites

Java Development Kit (JDK) 8 or higher
A Java IDE (e.g., NetBeans, IntelliJ IDEA) or a command-line environment with javac and java

How to Run

Ensure the Java file is placed in the correct package directory: com/mycompany/chapter1_challenge_1_1.
Compile the program:javac com/mycompany/chapter1_challenge_1_1/Chapter1_challenge_1_1.java


Run the program:java com.mycompany.chapter1_challenge_1_1.Chapter1_challenge_1_1


The output will display the decrypted code for the hardcoded input 13579.

Notes

The program assumes the input integer has at least two digits to perform the required operations.
For inputs with fewer than two digits, the program may produce unexpected results due to the digit extraction logic.
The output is a string concatenation of the computed product and sum, which may result in varying string lengths depending on the input.

License
This project is unlicensed. Modify the license as needed by editing the license comment in the source file.
