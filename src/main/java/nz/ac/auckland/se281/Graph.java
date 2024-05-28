package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
  private Map<Country, List<Country>> adjNodes;

  public Graph() {
    this.adjNodes = new HashMap<>();
  }

  public void addNode(Country country) {
    adjNodes.putIfAbsent(country, new ArrayList<Country>());
  }

  public void addEdge(Country countryA, Country countryB) {
    addNode(countryA);
    addNode(countryB);

    adjNodes.get(countryA).add(countryB);
  }

  public void removeNode(Country country) {
    adjNodes.remove(country);
    for (Country node : adjNodes.keySet()) {
      adjNodes.get(node).remove(country);
    }
  }

  public void removeEdge(Country countryA, Country countryB) {
    adjNodes.getOrDefault(countryA, new ArrayList<>()).remove(countryB);
    adjNodes.getOrDefault(countryB, new ArrayList<>()).remove(countryA);
  }

  public Map<Country, List<Country>> getAdjNodes() {
    return adjNodes;
  }

  
}
