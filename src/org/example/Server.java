package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Server {
	public static void main(String args[]) {
		ClassPathXmlApplicationContext springContext = null;

		if (args.length > 0 && "-s".equals(args[0])) {
			springContext = new ClassPathXmlApplicationContext("META-INF/applicationContextService.xml");
		} else {
			springContext = new ClassPathXmlApplicationContext("META-INF/applicationContextHttp.xml");
		}

		springContext.registerShutdownHook();
		springContext.start();
	}
}
