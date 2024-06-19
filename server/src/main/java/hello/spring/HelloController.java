package hello.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // cf. Controller 역할을 하도록 어노테이션을 붙여줬으나 @Component의 기능은 하지 못함(component scan을 위한 base package 지정된 곳 없음)
public class HelloController {

    @GetMapping("/hello-spring")
    // "/hello-spring"이 아니라 "/spring/hello-spring"으로 매핑됨
    // "/spring" 부분은 dispatcher servlet을 찾는 데에 사용된 것
    public String hello() {
        System.out.println("HelloController.hello");
        return "hello spring!";
    }
}
