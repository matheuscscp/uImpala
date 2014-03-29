package org.unbiquitous.uImpala.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Class to log thrown errors.
 * 
 * @author Matheus
 */
public final class Logger {
  /**
   * Logs an error into a file and prints the stack trace.
   * 
   * @param error Error descriptor.
   */
  public static void log(Error error, String fn) {
    error.printStackTrace();
    try {
      PrintWriter pw = new PrintWriter(new FileWriter(fn, true));
      
      pw.println(new Date().toString() + ": " + error.getMessage());
      for (StackTraceElement elem : error.getStackTrace())
        pw.println("  " + elem);
      pw.println();
      
      pw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
