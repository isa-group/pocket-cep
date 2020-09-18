package com.siddhiApi;

import com.siddhiApi.multithreading.EventExecutor;
import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication//(exclude = {ErrorMvcAutoConfiguration.class})
public class ApiRestApplication {
	public static void main(String[] args) {
		BasicConfigurator.configure();
		for (int i=0; i<4;++i)
			EventExecutor.getEventExecutor().executeConsumer();
		SpringApplication.run(ApiRestApplication.class, args);
	}
}
