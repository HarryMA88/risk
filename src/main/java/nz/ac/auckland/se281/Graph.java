package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class is a graph data structure representing the map. */
public class Graph {
  private Map<Country, List<Country>> adjNodes;

  /** This is the constructor for the graph. */
  public Graph() {
    this.adjNodes = new HashMap<>();
  }

  /**
   * This method adds a node to the graph.
   *
   * @param country This is the country to be added as a node
   */
  public void addNode(Country country) {
    adjNodes.putIfAbsent(country, new ArrayList<Country>());
  }

  /**
   * This method adds a node into another nodes list of adjacent nodes.
   *
   * @param countryA node who's list is being added to
   * @param countryB node to add to the other nodes list of adjacent nodes
   */
  public void addEdge(Country countryA, Country countryB) {
    addNode(countryA);
    addNode(countryB);

    adjNodes.get(countryA).add(countryB);
  }

  /**
   * This method removes a node from the graph.
   *
   * @param country the node to be removed.
   */
  public void removeNode(Country country) {
    adjNodes.remove(country);
    for (Country node : adjNodes.keySet()) {
      adjNodes.get(node).remove(country);
    }
  }

  /**
   * This method removes a relation between two nodes
   *
   * @param countryA first node to be removed
   * @param countryB second node to be removed
   */
  public void removeEdge(Country countryA, Country countryB) {
    adjNodes.getOrDefault(countryA, new ArrayList<>()).remove(countryB);
    adjNodes.getOrDefault(countryB, new ArrayList<>()).remove(countryA);
  }

  /**
   * This is the getter method for the list of adjacent nodes.
   *
   * @return the list of adjacent nodes as a hashmap
   */
  public Map<Country, List<Country>> getAdjNodes() {
    return adjNodes;
  }
}
