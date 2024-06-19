package hello.embedded;

import hello.spring.HelloConfig;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

public class EmbeddedTomcatSpringMain {

    // war 파일을 이용해 Tomcat으로 server를 실행할 때와는 다르게 main()을 작성
    // - war 파일을 이용할 때에는 servlet container가 제공하는 초기화 메서드를 사용해야 했음
    public static void main(String[] args) throws LifecycleException {
        System.out.println("EmbeddedTomcatSpringMain.main");

        // Tomcat 생성 및 설정
        Tomcat tomcat = new Tomcat();
        Connector connector = new Connector();
        connector.setPort(8080);
        tomcat.setConnector(connector);

        // Spring container 생성
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(HelloConfig.class);

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
        tomcat.start();
    }
}
