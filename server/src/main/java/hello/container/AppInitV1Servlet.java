package hello.container;

import hello.servlet.HelloServlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;

/**
 * http://localhost:8880/hello-servlet
 */
public class AppInitV1Servlet implements AppInit {

    @Override
    public void onStartup(ServletContext servletContext) { // servlet context 초기화 중 이 onStartup() 호출을 통해 application 초기화를 진행하고, 이 과정에서 servlet을 등록
        System.out.println("AppInitV1Servlet.onStartup");

        // 순수 servlet 코드 등록 - 서블릿을 프로그래밍 방식으로 등록(TestServlet에서 사용한 @WebServlet 어노테이션 이용 방식 등록 x)
        ServletRegistration.Dynamic helloServlet = servletContext.addServlet("helloServlet", new HelloServlet());
        // 위처럼 servletContext에 코드로 서블릿을 등록하면 Dynamic 타입을 반환 - 이 Dynamic에 mapping 정보를 추가
        helloServlet.addMapping("/hello-servlet");
    }
}
