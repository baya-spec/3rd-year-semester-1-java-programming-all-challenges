/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chapter1_challenge_1_2;

/**
 *
 * @author Bahar
 */
public class Chapter1_challenge_1_2 {

    public static void main(String[] args) {
        String[] winningNumbers = {"12-34-56-78-90", "33-44-11-66-22", "01-02-03-04-05"};
        double highestAverage = 0;
        String bestNumber = "";
        for (String ticket : winningNumbers){
            System.out.println("Analyzing: " + ticket);
            String cleanNumber = ticket.replace("-", "");
            char[] digitsChar = cleanNumber.toCharArray();
             int sum = 0;
             for (char digit : digitsChar){
                  sum += Character.getNumericValue(digit);
             }
             double average = (double) sum / digitsChar.length;
             System.out.println("Digit Sum: " + sum + ", Digit Average: " + average);
             if (average > highestAverage){
                 highestAverage = average;
                 bestNumber = ticket;
             }
        }
        System.out.println("\nThe winning number with the highest average is: " + 
                          bestNumber + " with an average of " + highestAverage);
        
    }
}
