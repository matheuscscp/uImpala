package org.unbiquitous.ubiengine.util;

/**
 * Class to log thrown errors.
 * 
 * @author Matheus
 */
public final class Logger {
  /**
   * Logs an error into a file named "ErrorLog.txt" and prints the stack trace.
   * 
   * @param e Error descriptor.
   */
  public static void log(Error e) {
    try {
      // open ErrorLog.txt to append
      java.io.PrintWriter pw = new java.io.PrintWriter(
        new java.io.FileWriter("ErrorLog.txt", true)
      );
      
      // logs the error by printing the date, error message and stack trace
      pw.println(new java.util.Date().toString() + ": " + e.getMessage());
      for (StackTraceElement elem : e.getStackTrace())
        pw.println("  " + elem);
      pw.println();
      
      pw.close();
    }
    catch (java.io.IOException e2) {
      e2.printStackTrace();
    }
    finally {
      e.printStackTrace();
    }
  }
}
