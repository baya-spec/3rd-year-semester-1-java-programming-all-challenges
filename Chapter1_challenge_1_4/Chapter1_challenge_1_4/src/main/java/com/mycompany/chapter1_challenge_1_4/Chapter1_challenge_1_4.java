/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chapter1_challenge_1_4;

/**
 *
 * @author Bahar
 */
import java.io.*;
import java.util.Scanner;
public class Chapter1_challenge_1_4 {

    public static void main(String[] args) {
        try {
            readConfig();
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    } 
    public static void readConfig() {
        Scanner scanner = null;
        try {
            File configFile = new File("config.txt");
            scanner = new Scanner(configFile);
             String versionLine = scanner.nextLine();
             int version = Integer.parseInt(versionLine);
             if (version < 2){
                 throw new Exception("Config version too old!");
             }
             String filePath = scanner.nextLine();
             File targetFile = new File(filePath);
             if (!targetFile.exists()){
                 throw new IOException("Config file path does not exist: " + filePath);
             }
             System.out.println("Config loaded successfully! Version: " + version);
        } catch (FileNotFoundException e) {
             System.out.println("Error: Config file not found!");
        }  catch (NumberFormatException e){
            System.out.println("Error: Invalid config version format!");
        }catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }catch (Exception e){
             System.out.println("Error: " + e.getMessage());
        } finally{
            if (scanner != null){
                scanner.close();
            }
            System.out.println("Config read attempt finished.");
        }
    }
}
