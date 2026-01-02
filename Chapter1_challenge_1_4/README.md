Chapter 1 Challenge 1.4 - Configuration File Reader
Overview
This Java program reads and validates a configuration file named config.txt. The program checks the file's version number and verifies the existence of a file path specified in the configuration. It handles various exceptions to ensure robust error reporting and proper resource cleanup. The program is designed to demonstrate file input handling, exception management, and basic configuration validation.
Project Structure

File: Chapter1_challenge_1_4.java
Package: com.mycompany.chapter1_challenge_1_4
Main Class: Chapter1_challenge_1_4

How It Works

Main Method:

Calls the readConfig() method within a try-catch block to handle any exceptions.
Prints any error messages encountered during execution.


readConfig Method:

Opens config.txt using a Scanner to read its contents.
Reads the first line as the configuration version (expected to be an integer).
Validates the version: if less than 2, throws an exception ("Config version too old!").
Reads the second line as a file path and checks if the specified file exists.
If the file exists, prints a success message with the version number.
Handles exceptions for:
FileNotFoundException: If config.txt is missing.
NumberFormatException: If the version is not a valid integer.
IOException: If the specified file path does not exist.
General Exception: For other unexpected errors.


Ensures the Scanner is closed in the finally block to prevent resource leaks.
Prints a completion message ("Config read attempt finished.") regardless of success or failure.



Expected Config File Format
The config.txt file should contain:

First line: An integer representing the configuration version (e.g., 2).
Second line: A valid file path to an existing file (e.g., data.txt).

Example config.txt:
2
data.txt

Example Outputs

Successful Case (valid config.txt with version 2 and existing file data.txt):Config loaded successfully! Version: 2
Config read attempt finished.


Missing Config File:Error: Config file not found!
Config read attempt finished.


Invalid Version Format (e.g., config.txt contains abc on the first line):Error: Invalid config version format!
Config read attempt finished.


Old Version (e.g., config.txt contains 1 on the first line):Error: Config version too old!
Config read attempt finished.


Invalid File Path (e.g., config.txt contains 2 and nonexistent.txt):Error: Config file path does not exist: nonexistent.txt
Config read attempt finished.



Prerequisites

Java Development Kit (JDK) 8 or higher
A Java IDE (e.g., NetBeans, IntelliJ IDEA) or command-line tools (javac, java)
A config.txt file in the same directory as the compiled program

How to Run

Create a config.txt file in the project directory with the required format (version and file path).
Ensure the file path specified in config.txt points to an existing file.
Place the Java file in the appropriate package directory:com/mycompany/chapter1_challenge_1_4/Chapter1_challenge_1_4.java


Compile the program:javac com/mycompany/chapter1_challenge_1_4/Chapter1_challenge_1_4.java


Run the program:java com.mycompany.chapter1_challenge_1_4.Chapter1_challenge_1_4


Observe the output based on the contents of config.txt.

Key Features

File Input: Reads configuration data from config.txt using Scanner.
Exception Handling: Robustly manages file, format, and version errors with specific error messages.
Resource Management: Ensures proper closure of Scanner in the finally block.
Validation: Checks for a minimum version number and valid file paths.
Clear Feedback: Provides detailed output for success and failure cases.

Notes

The program assumes config.txt has at least two lines (version and file path). Missing lines may cause exceptions.
The specified file path in config.txt must point to an existing file, or an IOException is thrown.
Invalid input (e.g., non-integer version) is handled gracefully with appropriate error messages.
The program does not modify config.txt or the file specified in it; it only validates their existence and format.

License
This project is unlicensed. To modify the license, edit the license comment at the top of the source file as indicated.
