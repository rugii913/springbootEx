package hello.container;

import hello.spring.HelloConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitV2Spring implements AppInit { // application 초기화 과정에서 application context 초기화를 진행한 것
    
    @Override
    public void onStartup(ServletContext servletContext) {
        System.out.println("AppInitV2Spring.onStartup");

        // Spring container 직접 생성 + 작성해둔 configuration 등록
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(HelloConfig.class);
        
        // Spring MVC를 호출할 dispatcher servlet 생성 - 생성 시 Spring container(AnnotationConfigWebApplicationContext 객체) 넘김
        // - AnnotationConfigWebApplicationContext는 WebApplicationContext의 구현 클래스로 servlet과 관련된 작업을 할 수 있는 메서드를 갖고 있음
        // - DispatcherServlet는 생성 시 WebApplicationContext를 arg로 받아 webApplicationContext property로 두고,
        //   - DispatcherServlet에서 각 controller로 연결하는 작업에 이용함
        // - 위에서 Spring MVC를 호출한다는 것은 Spring container에
        //   - 특정 규칙에 따라 엔드포인트로 기능하는 bean(객체)가 있어
        //   - dispatcher servlet과 해당 엔드포인트를 중심으로 MVC 구조에 따라 동작함을 의미함
        DispatcherServlet dispatcherServlet = new DispatcherServlet(appContext);
        
        // dispatcher servlet을 servlet container에 등록
        ServletRegistration.Dynamic servletRegistrationDynamic = servletContext.addServlet("dispatcherV2", dispatcherServlet);

        // 위 등록한 servlet에서 해결할 엔드포인트 매핑 추가 → /spring/* 요청이 dispatcher servlet을 통하도록 설정
        servletRegistrationDynamic.addMapping("/spring/*");
    }
}
