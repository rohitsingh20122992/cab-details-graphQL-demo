package com.nulpointerexception.cabdetailsgraphQLdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CabDetailsGraphQlDemoApplication {

	public static void main(String[] args) {
		System.out.println("****************** GraphQL starts **************************");
		SpringApplication.run(CabDetailsGraphQlDemoApplication.class, args);
		System.out.println("****************** GraphQL ends **************************");
	}

}
