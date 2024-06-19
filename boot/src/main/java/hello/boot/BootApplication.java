package hello.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootApplication {
	/*
	* - spring-boot-starter-web 라이브러리의 embedded Tomcat 종속성 확인
	*   - spring-boot-stater-web은 spring-boot-starter-tomcat 종속성을 갖고 있고
	*   - spring-boot-starter-tomcat에 tomcat-embed-core 라이브러리가 있음
	* */

	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}

}
