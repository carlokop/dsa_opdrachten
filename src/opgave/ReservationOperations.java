package opgave;
public interface ReservationOperations {
  
  /**
  * Maak het opgegeven tijdslot aan.
  * @param time tijdslot in formaat hhmm (bijv. 800 voor 08:00, 1400 voor 14:00)
  */
  void insert(int time);
  /**
  * Zoek naar een reservering op het aangegeven tijdslot.
  * @param time gewenst tijdslot in formaat hhmm
  * @return null als het tijdslot niet bestaat of niet vrij is
  * instantie van Reservation als tijdslot bestaat en gereserveerd is
  */
  Reservation search(int time);
  /**
  * Zoek het dichtstbijzijnde beschikbare tijdslot bij de opgegeven tijd.
  * Als de exacte tijd niet beschikbaar is,
  * zoek dan de dichtstbijzijnde beschikbare tijd.
  * Als er twee tijdsloten worden gevonden die even ver
  * van de gegeven tijd liggen (één eerder en één later),
  * retourneer dan de eerdere.
  * Als geen tijdslot beschikbaar is, returneer dan -1.
  * @param time gewenst tijdslot in formaat hhmm
  * @return eerst beschikbaar tijdslot als een tijdslot kan worden gevonden
  * -1 als geen tijdslot kan worden gevonden
  */
  int findClosestAvailableTime(int time);
  /**
  * Boek het opgegeven tijdslot met de opgegeven naam.
  * Het tijdslot moet bestaan en vrij zijn, anders wordt er niet gereserveerd.
  * @param time tijdslot in formaat hhmm (bijv. 800 voor 08:00, 1330 voor 13:30)
  * @param name naam voor reservering (bijv. "Smit")
  */
  void book(int time, String name);
  /**
  * Print de reserveringslijst op volgorde.
  * @param all Als all==true, print dan alle tijdsloten,
  * anders alleen de geboekte tijdsloten.
  */
  void printReservationsInOrder(boolean all);

  /**
  * Annuleer de reservering op het aangegeven tijdslot.
  * Als het tijdslot vrij was of niet kon worden gevonden, doe dan niets.
  * @param time tijdslot te annuleren in formaat hhmm
  */
  void cancel(int time);

}
