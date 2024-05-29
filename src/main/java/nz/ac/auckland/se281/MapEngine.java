package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.Collections;
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

  /** This method is the constructor for the class. */
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
        MessageCli.COUNTRY_INFO.printMessage(
            country.getName(), country.getContinent(), String.valueOf(country.getTax()));
        break;
      } catch (InvalidCountryException e) {
        MessageCli.INVALID_COUNTRY.printMessage(input);
        continue;
      }
    }
  }

  /**
   * Returns the country with all of its info from a string representation of the country.
   *
   * @param input the string name of the country
   * @return the country object corresponding to its name
   * @throws InvalidCountryException this exception is thrown when the inputed country name is
   *     invalid
   */
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
    // if the source is the destination print message
    if (source.equals(destination)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }
    // use breadth first search
    List<Country> visited = new ArrayList<>();
    Queue<Country> queue = new LinkedList<>();
    Map<Country, Country> parentTracker = new HashMap<>();
    queue.add(source);
    visited.add(source);
    parentTracker.putIfAbsent(source, null);
    while (!queue.isEmpty()) {
      // take country from queue
      Country country = queue.poll();
      // visit the countries unvisited neighbours and add them to the queue
      // also keep track of which country you visited the neighbour from
      for (Country neighbour : map.getAdjNodes().get(country)) {
        if (!visited.contains(neighbour)) {
          visited.add(neighbour);
          queue.add(neighbour);
          parentTracker.putIfAbsent(neighbour, country);
        }
      }
      // after visiting all the neighbours for a country, check if the destination has been visited
      // if you visited the destination, check which country you got there from, until you reach the
      // start
      if (visited.contains(destination)) {
        List<Country> path = new ArrayList<>();
        path.add(destination);
        Country child = destination;
        while (true) {
          Country parent = parentTracker.get(child);
          if (child.equals(source)) {
            break;
          }
          path.add(parent);
          child = parent;
        }
        // reverse path
        Collections.reverse(path);
        // get the path in terms of countries visited
        List<String> visitList = new ArrayList<>();
        for (Country step : path) {
          visitList.add(step.getName());
        }
        MessageCli.ROUTE_INFO.printMessage(visitList.toString());
        // go through path and check which continents you pass
        List<String> continents = new ArrayList<>();
        String continent = null;
        for (Country step : path) {
          if (continent == null) {
            continents.add(step.getContinent());
          } else if (!continent.equals(step.getContinent())) {
            continents.add(step.getContinent());
          }
          continent = step.getContinent();
        }
        MessageCli.CONTINENT_INFO.printMessage(continents.toString());
        // print the taxes paid
        int taxes = 0;
        for (Country step : path) {
          if (step.equals(source)) {
            continue;
          }
          taxes += step.getTax();
        }
        MessageCli.TAX_INFO.printMessage(String.valueOf(taxes));
        break;
      }
    }
  }
}
