package opgave;

import java.time.LocalTime;

/**
 * Maakt tijdsobject
 */
public class TimeNode {

  LocalTime time;
  
  /**
   * Maakt een TimeNode object wat een tijdobject maakt uit een int uumm
   * @param time
   * @throws IllegalArgumentException
   * Complexiteit O(1) want we doorlopen hier geen lijsten. 
   */
  public TimeNode(int time) throws IllegalArgumentException {
    if(!TimeNode.checkTime(time)) {
      throw new IllegalArgumentException("Ongeldig tijdsformaat");
    }
    
    String numberStr = String.format("%04d", time); 
    int hours = Integer.parseInt(numberStr.substring(0, 2));
    int minutes = Integer.parseInt(numberStr.substring(2, 4));
    this.time = LocalTime.of(hours, minutes);
    
  }
  
  /**
   * Geeft tijdsobject
   * @return    tijdsobject
   * Complexiteit O(1)
   */
  public LocalTime getTime() {
    return this.time;
  }
  
  /**
   * Controleert of geldige tijd is opgegeven
   * @param time    hhmm 
   * @return        true als het een geldige tijd is
   * Complexiteit O(1) We doen hier een aantal string operaties. Die kunenn gezien worden als constante tijd.
   */
  public static boolean checkTime(int time) {
    if(time < 0 || time > 2359) return false;
    String numberStr = String.format("%04d", time); 
    int hours = Integer.parseInt(numberStr.substring(0, 2));
    int minutes = Integer.parseInt(numberStr.substring(2, 4));
    
    // Controleer of de uren en minuten binnen de geldige grenzen vallen
    return hours >= 0 && hours < 24 && minutes >= 0 && minutes < 60;
  }

}
