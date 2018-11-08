/*
 * By: Marcos Gil
 * 
 * This is a program that will read a text file 
 * and return the 10 most frequent words found in 
 * that text file
 */

/* Need some classes from java.io to read from files */
import java.util.Scanner;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

/* Data structures needed */
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Arrays;

public class WordStats{
  
  public static void main(String[] args){
    
    /**
    * The main method will control all of this program,
    * it will read the file, find the most freqent words,
    * then output to console a visual representation of 
    * the results for the user to see
    * 
    * Input: Text filename from command line arguments
    * Return: void
    * 
    * Contract:
    *  main: Textfile -> void
    *  
    *  Purpose: Read a textfile and output the ten most
    *  frequent words in the console
    *  
    *  Pre-Conditions:
    *    Valid text file
    *  
    *  Post-Conditions:
    *    Outputs > nothing
    * 
    *  Side Effecs:
    *    None
    */
    
    /* the command line will be used to specify a filename */
    if( args == null || args.length != 1){
      System.out.println("Usage: java WordStats fname");
      return;
    }
    
    /* our dictionary */
    Hashtable<String, Integer> words = new Hashtable<String, Integer>();
    
    /* used for reading file */
    File    infile;
    Scanner in;
    
    
    /* let's measure the time */
    long startTime = System.nanoTime();
    try{
      infile = new File(args[0]);
      in = new Scanner(infile);
      
      String input = null;  
      
      while (in.hasNext()){

        input = in.next().trim().toLowerCase();
        input = input.replace(".", "");
        input = input.replace(",", "");
        input = input.replace("!", "");
        input = input.replace("?", "");
        input = input.replace("\"", "");
        
        
        if(!input.equals("")){ // When the above punctuation was removed, the input was still being passed in but with white spaces, this stops that from happening
                               // "..." would become "   " and the program would count that as a word instead of ignoring it
          if (words.containsKey(input)){
          
            Integer wordCount = words.get(input);
            wordCount = wordCount + 1;
            words.put(input, wordCount);
          } 
          else{
          
            words.put(input, 1);
          }
        }
      }
      
      ArrayList<Integer> wordValueArray = new ArrayList<Integer>(words.values());
      ArrayList<String> wordKeyArray = new ArrayList<String>(words.keySet());
      String[] maxKeys = new String[10];
      Integer[] maxValues = new Integer[10];
      int counter = 0;
      int index = 0;
      int max = 0;
      
      for (int i = 0;  i < maxKeys.length; i += 1){
      
        if (wordKeyArray.size() != 0){
          
          for (int j = 0; j < wordValueArray.size(); j += 1){
            
            if (wordValueArray.get(j) >= max){
              
              max = wordValueArray.get(j);
              index = j;
            }
          }
          
          if (i == 9){
            
            for (int k = 0; k < wordValueArray.size(); k += 1){
              
              if (wordValueArray.get(k) == max){
                
                maxValues = Arrays.copyOfRange(maxValues, 0, maxValues.length + 1);
                maxKeys = Arrays.copyOfRange(maxKeys, 0, maxKeys.length + 1);
              }
            }
          }
          
          else{
            
            maxValues[counter] = max;
            wordValueArray.remove(index);
            maxKeys[counter] = wordKeyArray.get(index);
            wordKeyArray.remove(index);
            index = 0;
            max = 0;
            counter = counter + 1;
          }
        }
      }
      
      System.out.println("--------------------------------------------------------------");
      System.out.println("The total amount of words in the text file is: " + words.size());
      System.out.println("\nBelow are the 10 most frequent words found in the text file. If there was a tie for the 10th most frequent word, they will be displayed aswell.");
      System.out.println("--------------------------------------------------------------");
      
      int maxString = 0;
      
      for (int m = 0; m < maxKeys.length; m += 1){
        
        if (maxKeys[m] != null){
          
          if (maxKeys[m].length() > maxString){
            
            maxString = maxKeys[m].length();
          }
        }
      }
      
  
       for (int h = 0; h < maxValues.length; h += 1){
        
        if (maxValues[h] != null){
          
          double currentValue = maxValues[h].doubleValue();
          double comparisonValue = maxValues[0].doubleValue();
          double calculateNumberOfPounds = (currentValue/comparisonValue) * 50.0;
          
          int numberOfPounds = (int) Math.round(calculateNumberOfPounds);
          int currentMax = maxString - maxKeys[h].length();
          System.out.print(maxKeys[h]);
          
          for (int s = 0; s < currentMax; s += 1){
            
            System.out.print(" ");
          }
          
          System.out.print("|");
          
          for (int p = 0; p < numberOfPounds; p += 1){
            
            System.out.print("#");
          }
          
          if (maxValues[h] == 1){
          
            System.out.println(" [Appeared " + maxValues[h] + " time]");
          }
          else{
            
            System.out.println(" [Appeared " + maxValues[h] + " times]");
          }
        }
       }
      
      System.out.println("--------------------------------------------------------------");
      
    }catch(FileNotFoundException e){
      System.err.println("File " + args[0] + " was not found");
      System.err.println("Exception thrown : " + e);      
    }
    
    long endTime = System.nanoTime();
    double time = (endTime-startTime)/1e9;
    System.err.println("finished in " + String.format("%0$.3f", time) + " seconds");
    
    
  }
  
}