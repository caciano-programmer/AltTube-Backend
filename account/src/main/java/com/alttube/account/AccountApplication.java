package com.alttube.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

import java.io.File;
import java.nio.file.Paths;

@EnableJms
@SpringBootApplication
public class AccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
		new File(Paths.get("").toAbsolutePath().toString() + "/account-images").mkdir();
	}
}
