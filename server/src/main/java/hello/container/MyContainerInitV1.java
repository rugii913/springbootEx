package hello.container;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;

import java.util.Set;

public class MyContainerInitV1 implements ServletContainerInitializer {
    /*
    * jakarta.servlet.ServletContainerInitializer
    * - servlet container를 초기화 하는 기능을 제공하는 인터페이스
    * - servlet container는 실행 시점에 초기화 메서드인 onStartup()을 호출
    *   - 이 onStartup() 안에 필요한 기능들을 등록, 초기화 가능
    * - 초기화 클래스 작성 후 WAS에 이 클래스를 알려줘야 함
    *   - ServletContainerInitializer를 구현한 클래스를 작성한 후
    *   - src/main/resources/META-INF/services/jakarta.servlet.ServletContainerInitializer 파일에(service가 아니라 services임에 유의)
    *     - cf. 파일 이름 그대로 jakarta.servlet.ServletContainerInitializer 인터페이스를 구현한 클래스를 알려주는 것
    *   - 각 줄에 작성한 클래스의 [패키지 이름].[클래스 이름]을 작성
    *   - 그러면 Tomcat이 실행 시점에 해당 클래스의 onStartup()을 호출하여 초기화
    * */

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) {
        // 여기서 파라미터로 받는 servletContext 객체가 servlet container에 접근하기 위한 인터페이스
        // - 이 객체를 통해 servlet를 등록, 필터 등록 등 가능
        System.out.println("MyContainerInitV1.onStartup");
        System.out.println("MyContainerInitV1 set = " + set);
        System.out.println("MyContainerInitV1 servletContext = " + servletContext);
    }
}
