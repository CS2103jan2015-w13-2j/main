package parser;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParserLogger {
	
	private static String className = null;
	private static Logger logger = null;
	
	public ParserLogger(String str) {
		className = str;
		logger = Logger.getLogger(className);
	}
	
	public void logNullPointer(String msg) throws NullPointerException {
		logger.log(Level.WARNING, msg);
		throw new NullPointerException(msg);
	}
	
	public void logIOException(String msg) throws IOException {
		logger.log(Level.WARNING, msg);
		throw new IOException(msg);
	}
}
