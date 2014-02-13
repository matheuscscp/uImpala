package org.unbiquitous.ubiengine.util;

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
   * @param e Error descriptor.
   */
  public static void log(Error e, final String fn) {
    try {
      // open ErrorLog.txt to append
      PrintWriter pw = new PrintWriter(
        new FileWriter(fn, true)
      );
      
      // logs the error by printing the date, error message and stack trace
      pw.println(new Date().toString() + ": " + e.getMessage());
      for (StackTraceElement elem : e.getStackTrace())
        pw.println("  " + elem);
      pw.println();
      
      pw.close();
    }
    catch (IOException e2) {
      e2.printStackTrace();
    }
    
    e.printStackTrace();
  }
}
