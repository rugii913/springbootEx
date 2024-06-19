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
		// 메인 설정 정보로 @SpringBootApplication 어노테이션이 있는 현재 클래스 정보를 넘김
		SpringApplication.run(BootApplication.class, args);
		/*
		* SpringApplication.run() 메서드의 핵심 2가지 → 앞선 예제에서 직접 진행했던 작업과 큰 틀에서 다르지 않음
		* (1) Spring container 생성 및 설정
		* - static method run()에서 SpringApplication 객체 생성하며 static method에 넘긴 config 클래스 정보를 객체의 primarySources property 초기화
		*   - 그리고 생성된 SpringApplication 객체의 non static method run() 호출
		* - non-static method run()에서 createApplicationContext(), prepareContext() 호출 등의 작업을 통해 context(Spring container) 구성
		*   - ex. ServletWebServerApplicationContextFactory의 createContext()에서 AnnotationConfigServletWebServerApplicationContext 객체 생성하며 return
		* (2) WAS(embedded Tomcat) 생성 및 설정
		* - non-static method run()에서 위 prepareContext() 이후 refreshContext() 호출 → 내부 refresh() 호출
		*   - ServletWebServerApplicationContext의 refresh() 호출 이후 쭉 따라가보면
		* - ServletWebServerApplicationContext의 createWebServer() 호출
		*   - 여기서 ServletWebServerApplicationContext 객체에 별도로 설정된 내용이 없다면
		* - TomcatServletWebServerFactory의 getWebServer()가 호출
		*   - 이 method 안에서 Tomcat connector 설정 등의 작업 진행
		* - 결과적으로 org.springframework.boot.web.server.WebServer interface로 추상화된 TomcatWebServer 객체를 얻음
		* */
	}

}
