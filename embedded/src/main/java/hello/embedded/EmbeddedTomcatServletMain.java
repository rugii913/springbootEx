package hello.embedded;

import hello.servlet.HelloServlet;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class EmbeddedTomcatServletMain {

    // war 파일을 이용해 Tomcat으로 server를 실행할 때와는 다르게 main()을 작성
    // - war 파일을 이용할 때에는 servlet container가 제공하는 초기화 메서드를 사용해야 했음
    public static void main(String[] args) throws LifecycleException {
        System.out.println("EmbeddedTomcatServletMain.main");

        /*
        * - Embedded Tomcat 다루는 것을 자체를 깊이 있게 학습하는 것은 권장하지 않음
        *   - 어떻게 동작하는지 대략 큰 그림만 파악, main()에서 바로 Tomcat을 띄울 수 있음을 아는 정도만
        *   - 제대로 다루려면 예외 처리 등 해줘야할 일들이 많음
        * */
        // # Tomcat 생성 및 설정
        // Tomcat 객체 생성
        Tomcat tomcat = new Tomcat();
        // Tomcat을 연결할 Connector 생성 및 설정 - org.apache.catalina.connector 패키지
        Connector connector = new Connector();
        connector.setPort(8080); // connector의 port 설정
        // Tomcat에 connector 설정(연결)
        tomcat.setConnector(connector);

        // # Tomcat에 servlet 등록
        Context context = tomcat.addContext("", "/"); // Tomcat에 사용할 contextPath와 docBase 지정
        // 특정 환경에서 발생하는 문제 때문에 추가하는 코드 - 시작
        // 프로젝트 루트에 tomcat.8080 디렉토리가 생성되는데, webapps 디렉토리가 생성되지 않는 것 때문에 발생하는 문제로 보임
        File docBaseFile = new File(context.getDocBase());
        if (!docBaseFile.isAbsolute()) {
            File appBaseFile = ((Host) context.getParent()).getAppBaseFile();
            docBaseFile = new File(appBaseFile, docBaseFile.getPath());
        }
        docBaseFile.mkdirs();
        // 특정 환경에서 발생하는 문제 때문에 추가하는 코드 - 끝
        tomcat.addServlet("", "helloServlet", new HelloServlet()); // servlet 등록
        context.addServletMappingDecoded("/hello-servlet", "helloServlet"); // 등록한 servlet의 url path를 매핑
        tomcat.start(); // Tomcat 시작 → 등록한 servlet 정상 동작하는지 확인
    }
}
