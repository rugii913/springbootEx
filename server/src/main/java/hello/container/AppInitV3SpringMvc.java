package hello.container;

import hello.spring.HelloConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitV3SpringMvc implements WebApplicationInitializer {
    /*
    * - WebApplicationInitializer를 구현하게 함
    *   - 앞선 AppInitV1Servlet, AppInitV2Spring과 다르게 AppInit을 구현하지 않음
    * - WebApplicationInitializer는 어떻게 동작하는가?
    *   - WebApplicationInitializer가 앞선 예제에서 직접 작성했던 AppInit interface를 대신하고
    *     - 이 WebApplicationInitializer를 다루는 servlet 초기화 코드는 spring-web 라이브러리에 이미 갖춰져 있음 
    *   - spring-web 라이브러리의 META-INF/services 디렉토리를 열어보면
    *     - jakarta.servlet.ServletContainerInitializer 파일이 있고
    *     - org.springframework.web.SpringServletContainerInitializer 텍스트가 작성되어 있음
    *   - 같은 라이브러리의 org/springframework.web.SpringServletContainerInitializer가 ServletContainerInitializer를 구현하고 있음
    *     - 여기서 @HandlesTypes(WebApplicationInitializer.class)로 WebApplicationInitializer을 구현한 모든 클래스를 끌어오고
    *     - 각 WebApplicationInitializer 구현 클래스의 onStartup()을 호출
    *   - 이런 방식으로 application 초기화를 진행하면서 dispatcher servlet을 sevlet container에 등록
    * - cf. 엔드포인트 url 패턴을 해결할 때는 우선순위를 구체적인 것이 실행
    *   - 앞서 등록해둔 /hello-servlet, /test, /spring/hello-spring 엔드포인트도 모두 동작함
    *     - 더 구체적인 url이기 때문
    *   - 다만 "/" 엔드포인트의 경우, 기존에 작동했던 것처럼 main/webapp의 index.html로 연결되지 않음
    *     - AppInitV3SpringMvc에서 "/"을 가져갔기 때문
    *     - "/"은 더 구체적으로 지정된 url 패턴을 제외한 모든 엔드포인트를 처리하도록 동작함
    * */
    // 

    @Override
    public void onStartup(ServletContext servletContext) {
        System.out.println("AppInitV3SpringMvc.onStartup");

        // Spring container 직접 생성 + 작성해둔 configuration 등록
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(HelloConfig.class);

        // Spring MVC를 호출할 dispatcher servlet 생성 - 생성 시 Spring container(AnnotationConfigWebApplicationContext 객체) 넘김
        DispatcherServlet dispatcherServlet = new DispatcherServlet(appContext);

        // dispatcher servlet을 servlet container에 등록
        ServletRegistration.Dynamic servletRegistrationDynamic = servletContext.addServlet("dispatcherV3", dispatcherServlet);

        // 위 등록한 servlet에서 해결할 엔드포인트 매핑 추가 → 모든 요청이 dispatcher servlet을 통하도록 설정
        servletRegistrationDynamic.addMapping("/");
    }
}
