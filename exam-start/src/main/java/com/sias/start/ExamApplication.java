package com.sias.start;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.sias.*"})
@MapperScan({"com.sias.*.mapper"})
public class ExamApplication {
  public static void main(String[] args) {
    SpringApplication.run(ExamApplication.class, args);
  }
}