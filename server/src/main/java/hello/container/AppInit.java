package hello.container;

import jakarta.servlet.ServletContext;

public interface AppInit { // AppInitializer의 줄임말이라고 생각하면 됨
    /*
    * - application 초기화를 통해 더 유연한 방식으로 초기화 - 이를 위해 반드시 interface가 필요
    *   - application 초기화와 servlet container 초기화(ServletContainerInitializer을 구현한 MyContainerInitV1 클래스에서의 servlet container 초기화)를 구분
    *   - 이 인터페이스를 구현하는 클래스에서 실제로 application 초기화 코드를 작성하고, 초기화 과정에서 서블릿들을 등록한다.
    * - servlet container 초기화만 사용해도 될 것 같은데 복잡하게 application 초기화를 추가로 이용해서 servlet을 등록하는 이유?
    *   - (1) 편리함
    *     - servlet container 초기화를 이용해서 직접 servlet을 등록하려면 ServletContainerInitializer를 구현한 코드를 작성
    *     - 이에 더해 META-INF/services/jakarta.servlet.ServletContainerInitializer 파일에 해당 클래스를 직접 지정까지 해줘야 함
    *     - application 초기화를 이용한다면 적절한 interface만 구현하면 됨
    *   - (2) 의존성
    *     - application 초기화를 위한 interface는 servlet container과 상관 없이, 즉 ServletContext에 의존하지 않는 방식으로 작성하는 것도 가능함
    * */

    void onStartup(ServletContext servletContext);
}
