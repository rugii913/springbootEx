package hello.container;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.HandlesTypes;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@HandlesTypes(AppInit.class)
// application 초기화 인터페이스를 지정
// → ServletContainerInitializer가 handle할 수 있는 type의 클래스를 선언하는 역할 → 즉 여기 명시된 type의 subclass들의 정보들을 잡아옴
public class MyContainerInitV2 implements ServletContainerInitializer {
    // 앞서 MyContainerInitV1에서 보았듯
    // - ServletContainerInitializer를 상속하며
    // - jakarta.servlet.ServletContainerInitializer에 클래스 이름을 잘 넣어주면
    // - servlet container 초기화 시 onStartup()이 실행

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) {
        System.out.println("MyContainerInitV2.onStartup");
        System.out.println("MyContainerInitV2 set = " + set);
        System.out.println("MyContainerInitV2 servletContext = " + servletContext);

        // @HandlesTypes에 의해 parameter인 set으로 class hello.container.AppInitV1Servlet과 같은 AppInit을 구현한 클래스 정보들이 넘어옴
        for (Class<?> appInitClass : set) {
            try {
                AppInit appInit = (AppInit) appInitClass.getDeclaredConstructor().newInstance(); // 클래스 정보가 넘어오므로 reflection으로 인스턴스화 시켜줘야 함
                appInit.onStartup(servletContext); // servlet context 초기화 중에 application 초기화를 진행하고, 그 과정에서 servlet을 등록할 것
                // AppInit type 구현 클래스가 여러 개 있을 경우, 모두를 같은 방식으로 제어해야하므로 interface로 만들었던 것
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
