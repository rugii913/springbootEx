package hello.boot;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;
import java.util.List;

/*
* - 앞선 예제의 EmbeddedTomcatSpringMain의 main() 내부 로직을 가짐
*   - 단 main()은 다른 클래스에서 역할을 가져감
* - main()이 이 클래스의 run()을 호출할 때, container configuration 정보를 담은 config class를 넘겨 받음
*   - main()이 있는 클래스 자체가 config class가 됨 → main()이 있는 클래스가 @ComponentScan을 이용해 component를 scan하는 중심이 됨
* - component scan을 적극적으로 이용하게 함으로써, 간편해진 부분이 있지만
*   - 여전히 fat jar의 단점을 해결하기 못한 상태
* */
public class MySpringApplication {

    // 실제 Spring Boot에서 이 역할을 하는 메서드에도 Tomcat 설정, Spring container 설정, dispatcher servlet 관련 코드가 들어감
    public static void run(Class<?> configClass, String[] args) {
        System.out.println("MySpringApplication.run args=" + List.of(args));

        // Tomcat 생성 및 설정
        Tomcat tomcat = new Tomcat();
        Connector connector = new Connector();
        connector.setPort(8080);
        tomcat.setConnector(connector);

        // Spring container 생성
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(configClass);

        // Spring MVC dispatcher servlet 생성, Spring container 연결
        DispatcherServlet dispatcher = new DispatcherServlet(applicationContext);

        // dispatcher servlet을 servlet container에 등록
        Context context = tomcat.addContext("", "/");
        // 특정 환경에서 발생하는 문제 때문에 추가하는 코드 - 시작
        // 프로젝트 루트에 tomcat.8080 디렉토리가 생성되는데, webapps 디렉토리가 생성되지 않는 것 때문에 발생하는 문제로 보임
        File docBaseFile = new File(context.getDocBase());
        if (!docBaseFile.isAbsolute()) {
            File appBaseFile = ((Host) context.getParent()).getAppBaseFile();
            docBaseFile = new File(appBaseFile, docBaseFile.getPath());
        }
        docBaseFile.mkdirs();
        // 특정 환경에서 발생하는 문제 때문에 추가하는 코드 - 끝
        tomcat.addServlet("", "dispatcher", dispatcher);
        context.addServletMappingDecoded("/", "dispatcher");

        // Tomcat 시작
        try {
            tomcat.start();
        } catch (LifecycleException e) { // 예외는 unchecked 예외로 바꿔서 던짐
            throw new RuntimeException(e);
        }
    }
}
