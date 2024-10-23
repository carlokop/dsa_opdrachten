package restaurant;

import java.time.LocalTime;

/**
 * Utility klasse die een integer valideert of dat een geldige tijd kan zijn en daar een localtime object van maakt
 */
public final class Tijd {

  //voorkomt instantiatie
  private Tijd() {}
  
  /**
   * Maakt een TimeNode object wat een tijdobject maakt uit een int uumm
   * @param time    tijd in uumm
   * @throws IllegalArgumentException
   * Complexiteit O(1) want we doorlopen hier geen lijsten en voeren allee wat sting opereraties uit die in constante tijd uitgevoerd kunnen worden
   */
  public static LocalTime maakTijd(int time) throws IllegalArgumentException {
    if(!Tijd.checkTime(time)) {
      throw new IllegalArgumentException("Tijdsformaat " + time + " (hhmm) kan niet worden omgezet in een geldige tijd");
    }
    
    String numberStr = String.format("%04d", time); 
    int hours = Integer.parseInt(numberStr.substring(0, 2));
    int minutes = Integer.parseInt(numberStr.substring(2, 4));
    return LocalTime.of(hours, minutes);
  }
  
  
  /**
   * Valideert of int met time hhmm omgezet kan worden in een geldige tijd
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
