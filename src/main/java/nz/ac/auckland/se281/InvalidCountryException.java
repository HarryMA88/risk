package nz.ac.auckland.se281;

/** This class is a custom exception for if the inputed country is invalid. */
public class InvalidCountryException extends Exception {
  /**
   * This is the constructor used to throw a new exception.
   *
   * @param country name of the invalid country
   */
  public InvalidCountryException(String country) {
    super(MessageCli.INVALID_COUNTRY.getMessage(country));
  }
}
