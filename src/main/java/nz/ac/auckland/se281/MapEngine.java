package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class is the main entry point. */
public class MapEngine {
  private Graph map;
  private Map<String, Country> tempCountries = new HashMap<>();

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    // add code here to create your data structures
    map = new Graph();
    // parse each string in the list
    // add countries as nodes to the map
    for (String country : countries) {
      String[] countryInfo = country.split(",");
      map.addNode(new Country(countryInfo[0], countryInfo[1], Integer.parseInt(countryInfo[2])));
      // add the country in a hashmap for temporary storage for adjacencies
      tempCountries.putIfAbsent(
          countryInfo[0],
          new Country(countryInfo[0], countryInfo[1], Integer.parseInt(countryInfo[2])));
    }
    // add adjacent countries to each country
    for (String country : adjacencies) {
      String[] adjacencyInfo = country.split(",");
      for (String adjacency : adjacencyInfo) {
        if (adjacency.equals(adjacencyInfo[0])) {
          continue;
        }
        // use temp country map to allocate adjacencies
        Country key = tempCountries.get(adjacencyInfo[0]);
        Country adjacent = tempCountries.get(adjacency);
        map.addEdge(key, adjacent);
      }
    }

  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // add code here
    MessageCli.INSERT_COUNTRY.printMessage();
    try {
      String input = processInput();
      Country country = tempCountries.get(input);
      MessageCli.COUNTRY_INFO.printMessage(country.getName(), country.getContinent(), String.valueOf(country.getTax()));
    } catch (InvalidCountryException e) {
      
    }
  }

  public String processInput() throws InvalidCountryException {
    String input = Utils.scanner.nextLine();
    boolean validCountry = false;
    for (String country : tempCountries.keySet()) {
      if (country.equals(input)) {
        validCountry = true;
      }
    }
    return input;
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
