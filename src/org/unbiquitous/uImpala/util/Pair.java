package org.unbiquitous.uImpala.util;

/**
 * C++ pair class.
 * @author Pimenta
 *
 * @param <FirstType>
 * @param <SecondType>
 */
public class Pair<FirstType, SecondType> {
  public FirstType first;
  public SecondType second;
  public Pair(FirstType f, SecondType s) {
    first = f;
    second = s;
  }
}
