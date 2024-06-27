package hello.pay;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderRunner implements ApplicationRunner {

    /*
    * - ApplicationRunner를 구현하면
    *   - Spring bean 초기화가 모두 끝나고
    *   - application 로딩 완료 시점에 run(args) 메서드를 호출함
    * */
    private final OrderService orderService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        orderService.order(1_000);
    }
}
