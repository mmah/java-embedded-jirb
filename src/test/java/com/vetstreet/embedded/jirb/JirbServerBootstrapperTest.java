package com.vetstreet.embedded.jirb;

import javax.annotation.Resource;

import org.jruby.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vetstreet.embedded.jirb.SshServerFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/beans.xml"})
public class JirbServerBootstrapperTest {

	final static Logger log = LoggerFactory.getLogger(JirbServerBootstrapperTest.class);
	
	@Resource
	SshServerFactory factory;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testJirb() throws Exception {
		//System.out.println("DIE");
		
	//	System.
		//Main.main(new String[]{"-e","require 'irb'", "-e","IRB.start('')"});
		//Main main = new Main();
		//main.run(new String[]{"-e","require 'irb'\nIRB.start(__FILE__)"});
		

		System.out.println("STARTED");
		
		Thread.sleep(10000);		
		factory.stop();
		log.info("Waitin!");
		Thread.sleep(5000);
//		
		
		
//	    <bean id="sshServer" class="org.apache.sshd.SshServer" factory-method="setUpDefaultServer" scope="prototype">
//        <property name="port" value="${sshPort}"/>
//        <property name="host" value="${sshHost}"/>
//        <property name="shellFactory">
//            <bean class="org.apache.karaf.shell.ssh.ShellFactoryImpl">
//                <property name="commandProcessor" ref="commandProcessor"/>
//            </bean>
//        </property>
//        <property name="commandFactory">
//            <bean class="org.apache.karaf.shell.ssh.ShellCommandFactory">
//                <property name="commandProcessor" ref="commandProcessor"/>
//            </bean>
//        </property>
//        <property name="keyPairProvider" ref="keyPairProvider"/>
//        <property name="passwordAuthenticator" ref="passwordAuthenticator"/>
//    </bean>
//	    <bean id="keyPairProvider" class="org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider">
//        <property name="path" value="${hostKey}"/>
//    </bean>
//    <bean id="passwordAuthenticator" class="org.apache.sshd.server.jaas.JaasPasswordAuthenticator">
//        <property name="domain" value="${sshRealm}"/>
//    </bean>		
		
	}

}
