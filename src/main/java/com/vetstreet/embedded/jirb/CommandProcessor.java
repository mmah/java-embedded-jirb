package com.vetstreet.embedded.jirb;

import java.io.InputStream;
import java.io.PrintStream;

public class CommandProcessor {

	public CommandSession createSession(InputStream in,
			PrintStream printStream, PrintStream printStream2) {	
		return new CommandSession(in,printStream,printStream2);
	}

}
