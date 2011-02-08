package com.vetstreet.embedded.jirb;

import java.util.Map;
import java.util.Properties;

import org.apache.sshd.server.PasswordAuthenticator;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class SecurityContextManager {
	
	private Map<String,String> configuration;
	private GenericApplicationContext authContext; 	
	
	public SecurityContextManager(AbstractApplicationContext parentContext, Map<String,String> configuration)
	{
		this.configuration = configuration;		
		authContext = new GenericApplicationContext(parentContext);
		Properties configurationProperties = new Properties();
		configurationProperties.putAll(configuration);
		PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
		configurer.setIgnoreUnresolvablePlaceholders(true);
		configurer.setProperties(configurationProperties);
		authContext.addBeanFactoryPostProcessor(configurer);
		XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(authContext);
		xmlReader.loadBeanDefinitions(new ClassPathResource("com/vetstreet/embedded/jirb/jirb-auth-context.xml"));
		authContext.refresh();		
	}
	
	public PasswordAuthenticator getPasswordAuthenticator() throws Exception
	{
		return (PasswordAuthenticator) authContext.getBean("passwordAuthenticator");
	}
	
	protected Object getBean(String name)
	{
		return authContext.getBean(name);
	}
}
