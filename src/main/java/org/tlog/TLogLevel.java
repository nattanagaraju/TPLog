package org.tlog;

import java.util.logging.Level;

public class TLogLevel extends Level {
	private static final long serialVersionUID = -2208062886926125314L;
	public static final Level REPORT = new TLogLevel("REPORT", Level.SEVERE.intValue() + 1);
	public static final Level ERROR = new TLogLevel("ERROR", Level.WARNING.intValue() + 1);
	  public TLogLevel(String name, int value) {
	    super(name, value);
	  }
}