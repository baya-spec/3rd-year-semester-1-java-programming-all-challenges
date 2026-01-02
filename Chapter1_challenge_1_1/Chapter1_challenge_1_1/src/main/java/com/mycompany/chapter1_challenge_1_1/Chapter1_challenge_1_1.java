/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chapter1_challenge_1_1;

/**
 *
 * @author Bahar
 */
public class Chapter1_challenge_1_1 {

    public static void main(String[] args) {
        int interceptedMessage = 13579;
        String decodeMessage = decodeCrypticMessage(interceptedMessage);
        System.out.println("The decrypted code is: " + decodeMessage);
    }
     public static String decodeCrypticMessage(int message){
         int numberOfDigits = (int) Math.log10(message) + 1;
          int firstDigit = message / (int) Math.pow(10, numberOfDigits - 1);
           int lastDigit = message % 10;
           int secondDigit = (message / (int) Math.pow(10, numberOfDigits - 2)) % 10;
           int secondLastDigit = (message / 10) % 10;
           int digitProduct = firstDigit * lastDigit;
           int digitSum = secondDigit + secondLastDigit;
            return digitProduct + "" + digitSum;
     }
}
      
