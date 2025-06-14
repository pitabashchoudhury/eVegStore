package com.vegstore.user_service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication
@EnableAspectJAutoProxy
public class UserServiceApplication {

	public static void main(String[] args) {
		System.out.println("Welcome To User-Service");
		SpringApplication.run(UserServiceApplication.class, args);
	}


}
