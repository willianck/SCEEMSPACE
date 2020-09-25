package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//XToOne 에서는 지연로딩을 무조건 세팅 해야함

@SpringBootApplication
public class JpashopApplication {
	
	public static void main(String[] args) {
		
		Hello hello = new Hello();
		hello.setData("hello");
		String data = hello.getData();
		System.out.println("data = " + data);
		
		SpringApplication.run(JpashopApplication.class, args);
	}

}
