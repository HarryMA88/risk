package nz.ac.auckland.se281;

import java.util.List;

/** This class is the main entry point. */
public class MapEngine {
  Graph map;

  public MapEngine() {
    // add other code here if you want
    map = new Graph();
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    // parse each string in the list
    for (String country : countries) {
      String[] countryInfo  = country.split(",");
      
    }
    List<String> adjacencies = Utils.readAdjacencies();
    // add code here to create your data structures
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // add code here
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}