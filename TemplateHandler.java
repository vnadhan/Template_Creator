/**
 * 
 */
package com.ev.p2p.template.service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.regex.*;

/**
 * @author Vishnu_Nadhan
 *
 */
@Component
public class TemplateHandler {
	
	@Autowired
	static VelocityEngine velocityEngine;
	@Autowired
	static MessageSource messageSource;
	
	public static VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public static void setVelocityEngine(VelocityEngine velocityEngine) {
		TemplateHandler.velocityEngine = velocityEngine;
	}

	public static MessageSource getMessageSource() {
		return messageSource;
	}

	public static void setMessageSource(MessageSource messageSource) {
		TemplateHandler.messageSource = messageSource;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String resolveTemplate(Locale locale) {
		//VelocityContext context = new VelocityContext();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("app-context.xml");
        // params for this context
		/*context.put("name", "John Doe");
        context.put("email", "johnDoe@ev.com");
        context.put("messages", (MessageSource) ctx.getBean("messageSource"));
        context.put("locale", locale);*/
        
        Map map = new HashMap();
		map.put("name", "John Doe");
		map.put("email", "johnDoe@ev.com");
		map.put("messages", (MessageSource) ctx.getBean("messageSource"));
		map.put("locale", locale);
        
        // Message resolver
	//	MessageSource mess = (MessageSource) ctx.getBean("messageSource");
		//locale.setDefault(Locale.FRENCH);
	//	System.out.println("Message : " + mess.getMessage("message.hello", null, locale));
        // get template
        //Template t = velocityEngine.getTemplate("welcome.vm");

        /*// write into template
        final StringWriter writer = new StringWriter();
        t.merge(context, writer);*/
        String text =
                VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "welcome.vm", "UTF-8", map);
//        return writer.toString(); 
        return text; 
	}

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("app-context.xml");
		velocityEngine = (VelocityEngine) ctx.getBean("velocityEngine");
		/*
		Properties p = new Properties();
		p.setProperty("resource.loader", "file");
		p.setProperty("file.resource.loader.path", "C:/Users/vishnu_nadhan/git/p2p/target/classes/templates/");
		p.setProperty("string.resource.loader.class","org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		p.setProperty("file.resource.loader.path", "./templates/");
		ExtendedProperties eprops = new ExtendedProperties();
		// if you already have a Properties object do this
	    eprops.putAll(p);
		velocityEngine.setExtendedProperties(eprops);
		eprops.setProperty("file.resource.loader.path", "./templates/");
		velocityEngine.setProperty("file.resource.loader.path", "./templates/");*/
		
		velocityEngine.init();
		//System.out.println(resolveTemplate(Locale.ENGLISH));
		System.out.println("-----------------------------------------------------------------");
		//System.out.println(resolveTemplate(Locale.FRENCH));
		
		// template file traveller
		Template t = velocityEngine.getTemplate("welcome.vm");
		final StringWriter writer = new StringWriter();
		t.merge(new VelocityContext(), writer);
		System.out.println(writer.toString());
	}

}
