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
		/*
		* Spring Boot application의 빌드, 배포 - executable JAR
		* - ./gradlew clean build로 build된 파일을 jar -xvf [파일이름]으로 압축 해제 후 확인해보면
		* - 디렉토리 구조
		*   - META-INF → jar 실행을 위해 필요한 MANIFEST.MF 파일 등
		*   - BOOT-INF
		*     - classes 디렉토리 → application 코드(class 파일 바이트 코드) 및 리소스 파일
		*     - lib 디렉토리 → 외부 라이브러리 jar 파일들
		*     - classpath.idx → 외부 라이브러리 경로(BOOT-INF/lib 하위로 존재하는 라이브러리 목록 쭉 명시)
		*     - layers.idx → Spring Boot 구조 경로
		*   - org → Spring Boot를 실행하기 위한 class 파일 바이트 코드
		*     - org/springframework/boot/loader/JarLauncher.class가 실제 main()을 실행하는 클래스
		*     - org/springframework/boot/loader 하위의 여러 파일들은 Spring Boot가 빌드 시 넣어준 것
		* - executable JAR(실행 가능 Jar)
		*   - Java 표준이 아닌 Spring Boot에서 새롭게 정의한 것
		*   - jar 내부에 jar를 포함할 수 있는 특별한 구조
		*   - 내부 jar를 포함하여 실행할 수 있게 함
		*   - 내부에 어떤 라이브러리가 포함되어 있는지 쉽게 확인 가능, jar가 압축 해제되지 않으므로 파일명 중복 문제 해결
		* - executable JAR의 문제 해결 방법
		*   - Jar 실행 시의 Java 표준 규격은 따라감
		*     - META-INF/MANIFEST.MF의 Main-Class로 명시된 클래스의 main()을 찾아서 실행
		*     - Main-Class 외의 나머지는 Java 표준이 아니라 Spring Boot가 임의로 작성하고 사용하는 정보
		*   - 즉 org.springframework.boot.loader.JarLauncher.class의 main()을 실행
		*     - 이 JarLauncher가 다음의 작업을 처리해줌
		*       - jar 내부의 jar을 읽는 기능
		*       - 특별한 구조에 맞게 클래스 정보를 읽는 기능
		*     - 위 작업들을 처리 후 META-INF/MANIFEST.MF의 Start-Class로 명시된 클래스의 main()을 실행
		*   - application 코드의 main()이 있는 클래스가 META-INF/MANIFEST.MF의 Start-Class로 지정되어 있음
		*     - JarLauncher가 args 등을 그대로 넘기며 application의 main()을 호출한다고 볼 수 있음
		*   - 위와 같은 작업에 org/springframework/boot/loader 하위에 있는 클래스들이 사용됨
		*     - 이들이 JarLauncher를 중심으로 Spring Boot의 executable JAR를 실제로 구동하는 것
		*   - war 파일에서 WEB-INF에 application 바이트 코드와 라이브러리를 포함하고 있는 것을 본따
		*     - BOOT-INF에 application 바이트 코드와 라이브러리들이 위치하도록 구조를 짜두었고
		*     - 이 BOOT-INF에 있는 파일들이 실행될 수 있도록
		*       - META-INF/MANIFEST.MF 규격에 맞춰 실행된 JarLauncher가
		*       - org/springframework/boot/loader 하위의 코드를 이용하는 형태
		* - 실행 과정 정리
		*   - 1. java -jar xxx.jar
		*   - 2. MANIFEST.MF 인식
		*   - 3. JarLauncher.main() 실행 → BOOT-INF/classes/ 및 BOOT-INF/lib/ 인식
		*   - 4. application의 main() 실행
		* - cf. IDE에서 실행할 때는 라이브러리를 직접 읽을 수 있으므로 application의 main()을 바로 실행, JarLauncher가 필요하지 않음
		* */
	}

}
