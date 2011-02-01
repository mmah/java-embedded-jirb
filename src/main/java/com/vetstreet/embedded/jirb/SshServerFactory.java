/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.vetstreet.embedded.jirb;

import java.util.HashMap;
import java.util.Properties;

import org.apache.sshd.SshServer;
import org.apache.sshd.common.Factory;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.Resource;

public class SshServerFactory implements ApplicationContextAware, InitializingBean, DisposableBean{

    private SshServer server;

    private boolean start;

	private AbstractApplicationContext context;

	private HashMap<String, Object> beans;
	
    private Resource userpasses;    

    final static Logger log = LoggerFactory.getLogger(SshServerFactory.class);
    
    public SshServerFactory() throws Exception {
		SshServer sshd = SshServer.setUpDefaultServer();
		sshd.setPort(22000);
		sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider("hostkey.ser"));
		Factory<Command> factory = null;
	//	factory = new ProcessShellFactory(new String[] {  "/bin/bash", "-i", "irb" }, EnumSet.of(ProcessShellFactory.TtyOptions.ONlCr));
		factory = new ShellFactoryImpl();//ShellFactoryImpl();
		sshd.setShellFactory(	factory	);
		final Properties userprops = new Properties();
		if(userpasses != null)
		{
			userprops.load(userpasses.getInputStream());
		}			
		PasswordAuthenticator authenticator = new PasswordAuthenticator(){
			public boolean authenticate(String arg0, String arg1,
					ServerSession arg2) {
				Object response = userprops.get(arg0);
				if(response != null)
					return response.equals(arg1);
				else if(arg0.equals("jirb") && arg1.equals("jruby15awesom3"))
					return true;
				else
					return false;
			}			
		};
		//authenticator.setDomain("embeddedjirb");
		sshd.setPasswordAuthenticator(authenticator);		
        this.server = sshd;
    }

    public boolean isStart() {
        return start;
    }

    public void start() throws Exception {
        if (!start) {
            try {
            	
                server.start();
                start = true;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

	private void mapBeans() {
		beans = new HashMap<String, Object>();
		for(String name : context.getBeanDefinitionNames())
		{
			try{
				beans.put(name, context.getBean(name));
			}catch(Exception e){}//ignore
		}
	}
    
    public void stop() throws Exception {
        if (start) {
            server.stop();
        }
    }

	public void destroy() throws Exception {
		this.stop();
	}

	public void afterPropertiesSet() throws Exception {
		this.start();
	}

	public void setApplicationContext(ApplicationContext context)
	throws BeansException {
		this.context = (AbstractApplicationContext) context;
	}

}
