package com.vetstreet.embedded.jirb;

import static org.junit.Assert.*;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.jruby.embed.ScriptingContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommandSessionTest {

	CommandSession commandSession;
	PipedInputStream containerPipe;
	
	
	@Before
	public void setUp() throws Exception {
		containerPipe = new PipedInputStream();
		PipedOutputStream containerout = new PipedOutputStream();
		containerPipe.connect(containerout);		
		commandSession = new CommandSession(null,new PrintStream(containerout),null, new HashMap());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecute() throws Exception {
		commandSession.execute("2+2");
		assertTrue(commandSession.getCommandBuffer().length() == 0);
		assertTrue(extractString().trim().equals("4"));
		commandSession.execute("b=[1,2,3].map{|y|y*3}");
		assertEquals(extractString().trim(),"[3, 6, 9]");
		assertTrue(commandSession.getCommandBuffer().length() == 0);
		commandSession.execute("a=[1,2,3].map{|x|");
		assertTrue(commandSession.getCommandBuffer().length() != 0);
		assertEquals("a=[1,2,3].map{|x|\n", commandSession.getCommandBuffer().toString());
		commandSession.execute("x*6");
		assertTrue(commandSession.getCommandBuffer().length() != 0);
		assertEquals("a=[1,2,3].map{|x|\nx*6\n", commandSession.getCommandBuffer().toString());
		commandSession.execute("}");
		assertTrue(commandSession.getCommandBuffer().length() == 0);
		assertEquals(extractString().trim(),"[6, 12, 18]");
		commandSession.execute("if(a.length > 2)");
		assertTrue(commandSession.getCommandBuffer().length() != 0);
		commandSession.execute("true");
		assertTrue(commandSession.getCommandBuffer().length() != 0);
		commandSession.execute("else");
		assertTrue(commandSession.getCommandBuffer().length() != 0);
		commandSession.execute("false");
		assertTrue(commandSession.getCommandBuffer().length() != 0);		
		commandSession.execute("end");
		assertTrue(commandSession.getCommandBuffer().length() == 0);		
		assertEquals(extractString().trim(),"true");
		try{
			commandSession.execute("accountDao");
		}catch(Exception e)
		{
			assertTrue(e.getMessage().contains("undefined local variable"));
		}
		assertTrue(commandSession.getCommandBuffer().length() == 0);		
		commandSession.execute("2+2");
		assertTrue(commandSession.getCommandBuffer().length() == 0);
		assertTrue(extractString().trim().equals("4"));		
	}
	
	public String extractString() throws Exception {
		while(containerPipe.available() == 0)
		{
			Thread.sleep(50);
		}
		byte[] ba = new byte[containerPipe.available()];
		containerPipe.read(ba);
		return new String(ba);
	}

}
