package com.brightstar.trpgfate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class TrpgfateApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrpgfateApplication.class, args);
	}

}
