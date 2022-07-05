package com.example;


import com.example.test.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;

import java.util.List;

//This class should be put in the root package so that all classes
// under the root package can be scanned by Spring
@SpringBootApplication
@ServletComponentScan//it needs this annotation if application is using WebServlet, WebFilter, WebListener
public class MainApplication {


	public static void main(String... args) throws java.io.IOException {

		//System.out.println("Args: " + args[0]+ args[1]);
//		InputStreamReader i = new InputStreamReader(System.in);
//		BufferedReader b = new BufferedReader(i);
//		System.out.println("Enter Course");
//		String course = b.readLine();
//		System.out.println("Edureka" + course);
		String encoding = System.getProperty("file.encoding");
		String timezone = System.getProperty("user.timezone");
		System.out.println("--- Start to Run Spring Application---");
		ApplicationContext ctx = SpringApplication.run(MainApplication.class, args);
		System.out.println("--- Run Spring Application Successfully---");

		List<User> userList = parseArray("response", User.class);
/*
		CollectionGeneric c = new CollectionGeneric(new ArrayList());c.show();
		CollectionGeneric<ArrayList> c1 = new CollectionGeneric<>(new ArrayList());c1.show();
		CollectionGeneric c2 = new CollectionGeneric<>(new ArrayList());c2.show();
		CollectionGeneric<ArrayList> c3 = new CollectionGeneric(new ArrayList());c3.show();
		CollectionGeneric<ArrayList> c4 = new CollectionGeneric<ArrayList>(new ArrayList());c4.show();
		*/
//		String[] beans = ctx.getBeanDefinitionNames();
//		log.info("--- Bean List---");
//		for(String s : beans) System.out.println(s);
	}

	public static <T> List<T> parseArray(String response, Class<T> Object){
		List<T> list = null;
		return list;
	}
}
