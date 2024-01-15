package hello.container;

import jakarta.servlet.ServletContext;

public interface AppInit {
    // 어플리케이션 초기화를 통해 더 유연한 방식으로 초기화 - 이를 위해서는 꼭 인터페이스가 필요함
    // 어플리케이션 초기화와 서블릿 컨테이너 초기화를 구분(vs. ServletContainerInitializer을 구현한 MyContainerInitV1 클래스에서의 서블릿 컨테이너 초기화)
    // - 이 인터페이스를 구현하는 클래스에서 실제로 어플리케이션 초기화 코드를 작성하고, 초기화 과정에서 서블릿들을 등록한다.

    void onStartup(ServletContext servletContext);
}
