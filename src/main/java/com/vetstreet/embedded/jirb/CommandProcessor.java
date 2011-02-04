package com.vetstreet.embedded.jirb;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;

public class CommandProcessor {

	public CommandSession createSession(InputStream in,
			PrintStream printStream, PrintStream printStream2, Map<String,Object> beans) {	
		return new CommandSession(in,printStream,printStream2, beans);
	}

}
