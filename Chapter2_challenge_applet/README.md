Chapter 2 Challenge - Applet Animation
Overview
This Java applet creates a simple animation that displays the text "Baharu classic" moving horizontally across a black background in green, using a bold Arial font. The text bounces back to the starting position when it reaches the right edge of the applet window. The applet uses a separate thread to manage the animation loop and includes lifecycle methods for initialization, starting, stopping, and cleanup. Note that Java applets are deprecated technology, but this program demonstrates their functionality for educational purposes.
Project Structure

File: Chapter2_challenge_Applet.java
Package: chapter2_challenge_applet
Main Class: Chapter2_challenge_Applet
Dependencies: Java AWT (java.awt.*) and Applet (java.applet.Applet)

How It Works

Initialization (init):

Sets the applet size to 400x100 pixels.
Configures a black background.
Initializes the text position (textPositionX) to start at the left edge (0).


Animation Thread (start, run, stop):

Creates a thread to handle animation when the applet becomes visible (start).
Updates the text's horizontal position every 100ms (ANIMATION_DELAY) in the run method.
Moves the text 5 pixels to the right per frame; resets to the starting position when it exceeds the applet width.
Stops the thread when the applet is no longer visible (stop).


Painting (paint):

Draws the text "Baharu classic" in green, bold Arial font (24pt) at a centered vertical position.
Adds a small gray footer text ("Java Applet Demo - Deprecated Technology") at the bottom.


Cleanup (destroy):

Logs when the applet is permanently removed.


Key Features:

Smooth animation using a separate thread.
Bouncing effect by resetting text position.
Error handling for thread interruptions.
Lifecycle management with logging for debugging.



Example Output

The applet displays "Baharu classic" moving from left to right across a 400x100 black window.
The text resets to the left edge after reaching the right boundary.
Console logs show lifecycle events (e.g., "Applet initialized", "Animation thread started", etc.).

Prerequisites

Java Development Kit (JDK) 8 or higher (earlier versions preferred due to applet deprecation in modern JDKs).
A Java IDE (e.g., NetBeans, Eclipse) or command-line tools (javac, appletviewer).
A browser or appletviewer that supports Java applets (note: modern browsers no longer support applets).

How to Run

Place the Java file in the appropriate package directory:chapter2_challenge_applet/Chapter2_challenge_Applet.java


Create an HTML file to load the applet (e.g., applet.html):<html>
<head><title>Applet Demo</title></head>
<body>
<applet code="chapter2_challenge_applet.Chapter2_challenge_Applet" width="400" height="100"></applet>
</body>
</html>


Compile the Java file:javac chapter2_challenge_applet/Chapter2_challenge_Applet.java


Run using appletviewer:appletviewer applet.html

Alternatively, open applet.html in a compatible browser (if supported).

Notes

Applet Deprecation: Java applets are deprecated and unsupported in modern browsers. Use appletviewer or older environments for testing.
Thread Safety: The animation thread is safely managed with proper start/stop logic to avoid resource leaks.
Fixed Settings: The applet uses a fixed size (400x100), font (Arial, 24pt), and text ("Baharu classic"). These can be modified via constants or constructor parameters.
Error Handling: The program handles thread interruptions gracefully, logging errors to the console.
Console Output: Lifecycle methods print debug messages to track initialization, thread activity, and destruction.

License
This project is unlicensed. To modify the license, edit the license comment at the top of the source file as indicated.
