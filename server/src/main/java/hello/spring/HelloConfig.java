package hello.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfig {

    @Bean
    public HelloController helloController() {
        System.out.println("HelloConfig.helloController"); // cf. 처음 시작할 때 바로 출력되지 않음, 연관된 servlet이 호출될 때에서야 bean이 생성됨을 알 수 있음 
        return new HelloController();
    }
}
