package nz.ac.auckland.se281;

/** This class represents a country in the map. */
public class Country {
  private String name;
  private String continent;
  private int tax;

  /**
   * This is the main constructor for the country.
   *
   * @param name the name of the country as a string
   * @param continent the continent to which the country belongs to as a string
   * @param tax the amount of tax you need to pay to cross this country's borders as an int
   */
  public Country(String name, String continent, int tax) {
    this.name = name;
    this.continent = continent;
    this.tax = tax;
  }

  /**
   * This is an alternative constructor used to fetch a country by its string name.
   *
   * @param name the name of the country as a string
   */
  public Country(String name) {
    this.name = name;
  }

  /** This method generates the hashcode for the country. */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  /** This method checks if two countries are the same. */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Country other = (Country) obj;
    if (name == null) {
      if (other.name != null) return false;
    } else if (!name.equals(other.name)) return false;
    return true;
  }

  /**
   * This is the getter method for the country's name.
   *
   * @return the country's name as a string
   */
  public String getName() {
    return name;
  }

  /**
   * This is the getter method for the country's continent.
   *
   * @return the country's continent as a string
   */
  public String getContinent() {
    return continent;
  }

  /**
   * This is the getter method for the country's cross border tax.
   *
   * @return the country's tax as an int
   */
  public int getTax() {
    return tax;
  }
}
