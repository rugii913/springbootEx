package hello.spring;

import org.springframework.context.annotation.Bean;

//@Configuration // @ComponentScan 사용하게 하면서 주석 처리 - 이 클래스는 config로 사용되지 않게 했음
public class HelloConfig {

    @Bean
    public HelloController helloController() {
        System.out.println("HelloConfig.helloController");
        return new HelloController();
    }
}
