package kunstvoorwerpen;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Sorteeraglorithe Radix sort
 * Dit algoritme is niet heel uitgebreid en zonder code voorbeeld in het boek behandeld
 * Derhalve heb deze hele klasse op wat kleine aanpassingen na laten genereren door ChatGPT
 * 
 * Bron: OpenAI. (2024). ChatGPT [GPT-4]. chat.openai.com/chat.
 */
public class RadixArtifact {
  
  /**
   * Zoekt in de lijst met artifacts de hoogste waarde
   * @param artifacts   de lijst met artifacts om in te zoeken
   * @param isValue     true voor value false voor prijs
   * @return            de maximale prijs als int
   * 
   * O(n)
   */
  private static int getMax(ArrayList<Artifact> artifacts, boolean isValue) {
      double max = isValue ? artifacts.get(0).getValue() : artifacts.get(0).getPrice();
      for (Artifact artifact : artifacts) {
          double current = isValue ? artifact.getValue() : artifact.getPrice();
          if (current > max) {
              max = current;
          }
      }
      return (int) (max * 100); // Scale to integer (2 decimal places)
  }
  
   
  /**
   * Sorteert de lijst met artifacts voor een gegeven exponent
   * @param artifacts       lijst met artifacts die gesorteerd moeten worden
   * @param exp             aantal decimalen
   * @param isValue         true voor value false voor prijs
   * @param descending      true om de lijst in aflopende volgorde te sorteren false in oplopende volgerde
   * 
   * O(n)
   */
  private static void countingSort(ArrayList<Artifact> artifacts, int exp, boolean isValue, boolean descending) {
      int n = artifacts.size();
      ArrayList<Artifact> output = new ArrayList<>(Collections.nCopies(n, null));
      int[] count = new int[10]; // For digits 0-9

      // Initialize count array as 0
      for (int i = 0; i < 10; i++) {
          count[i] = 0;
      }
     

      // Count occurrences of digits
      for (Artifact artifact : artifacts) {
          int number = (int) ((isValue ? artifact.getValue() : artifact.getPrice()) * 100); // Scale to integer
          int digit = (number / exp) % 10;
          count[digit]++;
      }

      // Modify count array to store actual positions, for descending sort reverse
      if (descending) {
          for (int i = 8; i >= 0; i--) {
              count[i] += count[i + 1];
          }
      } else {
          for (int i = 1; i < 10; i++) {
              count[i] += count[i - 1];
          }
      }
     

      // Build the output array based on the count array
      for (int i = n - 1; i >= 0; i--) {
          int number = (int) ((isValue ? artifacts.get(i).getValue() : artifacts.get(i).getPrice()) * 100);
          int digit = (number / exp) % 10;
          output.set(count[digit] - 1, artifacts.get(i));
          count[digit]--;
      }

      // Copy the output array to artifacts
      for (int i = 0; i < n; i++) {
          artifacts.set(i, output.get(i));
      }
      
      
  }
   

  /**
   * Sorteert een lijst met artifacts gebareerd op de tupel price en value
   * In deze methode wordt steeds of de prijs of de value gesorteerd gebaseerd op de keuze van isValue
   * @param artifacts       lijst met artifacts die gesorteerd moeten worden
   * @param isValue         true om op value te sortere false om op prijs te sorteren
   * @param descending      true om de lijst in aflopende volgorde te sorteren false in oplopende volgerde
   * 
   * O(n)
   */
  public static void radixSort(ArrayList<Artifact> artifacts, boolean isValue, boolean descending) {
      int max = getMax(artifacts, isValue);                     //O(n)
      for (int exp = 1; max / exp > 0; exp *= 10) {             //O(d) aantal decimalen
          countingSort(artifacts, exp, isValue, descending);    //O(n)
      }
  }
 
  

}
