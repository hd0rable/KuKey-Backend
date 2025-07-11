package com.example.Kukey_Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableAspectJAutoProxy
@EnableJpaAuditing
@EnableScheduling
@EnableAsync
public class KukeyBackendApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(KukeyBackendApplication.class, args);
	}

}
