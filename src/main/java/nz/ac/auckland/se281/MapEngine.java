package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


/** This class is the main entry point. */
public class MapEngine {
  private Graph map;
  private Set<Country> countrySet = new HashSet<>();

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
      // add the country in a hashset for temporary storage for adjacencies
      countrySet.add(new Country(countryInfo[0], countryInfo[1], Integer.parseInt(countryInfo[2])));
    }
    // add adjacent countries to each country
    for (String country : adjacencies) {
      String[] adjacencyInfo = country.split(",");
      for (String adjacency : adjacencyInfo) {
        if (adjacency.equals(adjacencyInfo[0])) {
          continue;
        }
        // use temp country map to allocate adjacencies
        Country key = new Country(adjacencyInfo[0]);
        Country adjacent = null;
        Country check = new Country(adjacency);
        for (Country element : countrySet) {
          if (element.equals(check)) {
            adjacent = element;
            break;
          }
        }
        map.addEdge(key, adjacent);
      }
    }

  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // add code here
    MessageCli.INSERT_COUNTRY.printMessage();
    while (true) {
      String input = Utils.capitalizeFirstLetterOfEachWord(Utils.scanner.nextLine());
      try {
        Country country = getCountry(input);
        MessageCli.COUNTRY_INFO.printMessage(country.getName(), country.getContinent(), String.valueOf(country.getTax()));
        break;
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(input);
        continue;
      }
    }
  }

  public Country getCountry(String input) throws InvalidCountryException {
    boolean validCountry = false;
    Country country = null;
    Country check = new Country(input);
    for (Country element : countrySet) {
      if (element.equals(check)) {
        country = element;
        validCountry = true;
        break;
      }
    }
    if (!validCountry) {
      throw new InvalidCountryException(input);
    }
    return country;
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    MessageCli.INSERT_SOURCE.printMessage();
    Country source;
    Country destination;
    // get the source country
    while (true) {
      String input = Utils.capitalizeFirstLetterOfEachWord(Utils.scanner.nextLine());
      try {
        source = getCountry(input);
        break;
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(input);
        continue;
      }
    }
    // get the destination country
    MessageCli.INSERT_DESTINATION.printMessage();
    while (true) {
      String input = Utils.capitalizeFirstLetterOfEachWord(Utils.scanner.nextLine());
      try {
        destination = getCountry(input);
        break;
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(input);
        continue;
      }
    }
    // use breadth first search
    List<Country> visited = new ArrayList<>();
    Queue<Country> queue = new LinkedList<>();
    queue.add(source);
    visited.add(source);
    while (!queue.isEmpty()) {
      Country country = queue.poll();
      for (Country neighbour : map.getAdjNodes().get(country)) {
        if (!visited.contains(neighbour)) {
          visited.add(neighbour);
          queue.add(neighbour);
        }
      }
    }
  }
}
