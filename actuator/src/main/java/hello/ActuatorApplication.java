package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActuatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActuatorApplication.class, args);
    }

    /*
    * Spring Boot의 프로덕션 준비 기능(production-ready features)
    * - 공식 문서 https://docs.spring.io/spring-boot/reference/actuator/index.html
    * - 비기능적 요구사항(non-functional requirements)
    *   - 기획자가 직접 요구하는 경우는 드문, 기능 요구사항(비즈니스 요구사항) 외의 비기능적 요소들
    *   - production을 운영 환경에 배포할 때 준비해야하는 비기능적 요소들
    *   - 지표(metric), 추적(trace), 감사(auditing), 모니터링(monitoring)
    *     - ex. 애플리케이션이 현재 살아 있는지, 로그 정보는 정상 설정되어 있는지, 커넥션 풀은 얼마나 사용되고 있는지 등을 확인할 수 있어야 함
    * - Spring Boot는 spring-boot-actuator 라이브러리를 제공
    *   - 이 actuator 라이브러리를 통해 프로덕션 준비 기능을 편리하게 사용 가능
    *   - 이에 더해 Micrometer, Prometheus, Grafana 등 모니터링 시스템과 쉽게 연동 가능
    *   - cf. actuator는 시스템을 움직이거나 제어하는 기계 장치라는 의미
    * */
}
