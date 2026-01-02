Chapter 1 Challenge 1.3 - Dungeon Adventure Game
Overview
This Java program simulates a dungeon adventure game where a player navigates through five rooms, facing random events that affect their health or require interaction. The player starts with 100 health points and encounters traps, healing potions, or monsters in each room. The goal is to survive all rooms or be defeated if health reaches zero. The program uses random events and user input for an interactive experience.
Project Structure

File: Chapter1_challenge_1_3.java
Package: com.mycompany.chapter1_challenge_1_3
Main Class: Chapter1_challenge_1_3

How It Works

Initialization:

The player starts with health = 100.
A Random object generates random events.
A Scanner object captures user input for monster encounters.


Game Loop:

The player enters up to 5 rooms in a dungeon.
In each room, a random event occurs (1-3):
Event 1 (Trap): Reduces health by 20 points.
Event 2 (Healing Potion): Increases health by 15 points (capped at 100).
Event 3 (Monster): The player must guess a number (1-5) to defeat a monster. The correct number is randomly generated. The player keeps guessing until correct, with no health penalty.


The program prints the event outcome and current health.


Game Over Conditions:

If health drops to 0 or below, the game ends, and the player is defeated in the current or previous room.
If the player survives all 5 rooms, they win, and the final health is displayed.


Cleanup:

The Scanner is closed after the game ends.



Example Gameplay
Entering room 1...
A trap sprung! Health is now 80

Entering room 2...
You found a healing potion! Health is now 95

Entering room 3...
A monster appears!
Guess a number (1-5) to defeat it: 3
Wrong! Try again: 
Guess a number (1-5) to defeat it: 4
You defeated the monster!

Entering room 4...
A trap sprung! Health is now 75

Entering room 5...
You found a healing potion! Health is now 90

You cleared the dungeon! Victorious with 90 health!

Example Gameplay

Entering room 1...
A trap sprung! Health is now 80

Entering room 2...
You found a healing potion! Health is now 95

Entering room 3...
A monster appears!
Guess a number (1-5) to defeat it: 3
Wrong! Try again: 
Guess a number (1-5) to defeat it: 4
You defeated the monster!

Entering room 4...
A trap sprung! Health is now 75

Entering room 5...
You found a healing potion! Health is now 90

You cleared the dungeon! Victorious with 90 health!


Prerequisites

Java Development Kit (JDK) 8 or higher
A Java IDE (e.g., NetBeans, IntelliJ IDEA) or command-line tools (javac, java)

How to Run

Place the Java file in the appropriate package directory:com/mycompany/chapter1_challenge_1_3/Chapter1_challenge_1_3.java


Compile the program:javac com/mycompany/chapter1_challenge_1_3/Chapter1_challenge_1_3.java


Run the program:java com.mycompany.chapter1_challenge_1_3.Chapter1_challenge_1_3


Follow the prompts to interact with monster encounters and observe the game outcomes.

Key Features

Random Events: Uses Random to simulate varied gameplay (traps, potions, or monsters).
User Interaction: Requires player input to guess numbers during monster encounters.
Health Management: Tracks and caps health at 100, with clear feedback on status.
Early Termination: Ends the game if health reaches zero, with appropriate messaging.
Simple Interface: Console-based with clear prompts and output.

Notes

The program assumes valid integer input (1-5) for monster encounters. Invalid input (e.g., non-integers) may cause runtime errors.
The game is deterministic in its health changes (fixed trap and potion values) but randomized in event selection and monster numbers.
The health cap at 100 prevents over-healing from potions.
The program closes the Scanner to avoid resource leaks.

License
This project is unlicensed. To modify the license, edit the license comment at the top of the source file as indicated.
