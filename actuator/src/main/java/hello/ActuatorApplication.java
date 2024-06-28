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
    /*
    * Spring Boot actuator의 endpoint 설정
    * - actuator endpoint를 사용(호출 가능하도록)하려면 (1) endpoint 활성화, (2) endpoint 노출 두 과정 필요
    *   - 대부분의 endpoint는 기본적으로 활성화되어 있음(shutdown 제외)
    *   - Spring Boot autoconfigure에서 actuator endpoint 노출 기본 설정은 endpoint ID 중 health만 노출
    * - (1) endpoint 활성화/비활성화
    *   - 해당 기능을 사용할지 말지 선택하는 것
    *   - 방법 (cf. 설정 데이터로 yml 파일 사용 시 적절하게 계층구조에 맞춰 작성)
    *     - 외부 설정값을 다음과 같이 부여 → management.endpoint.[endpoint ID].enabled=true
    *     - 비활성화시킬 경우 true 대신 false 부여
    * - (2) endpoint 노출/제외
    *   - 활성화된 endpoint를 HTTP로 노출할지, JMX로 노출할지 선택하는 것
    *     - HTTP, JMX 모두 노출할 수도 있음
    *   - 노출 방법 (cf. 설정 데이터로 yml 파일 사용 시 적절하게 계층구조에 맞춰 작성)
    *     - (HTTP 노출) 외부 설정값을 다음과 같이 부여 → management.endpoints.web.exposure.include="[노출할 endpoint ID]"
    *     - (JMX 노출) 외부 설정값을 다음과 같이 부여 → management.endpoints.jmx.exposure.include="[노출할 endpoint ID]"
    *     - [노출할 endpoint ID] 자리에 *을 줄 경우 활성화된 모든 endpoint 노출
    *   - 제외 방법 (cf. 설정 데이터로 yml 파일 사용 시 적절하게 계층구조에 맞춰 작성)
    *     - 외부 설정값을 다음과 같이 부여 → management.endpoints.[web 또는 jmx].exposure.exclude="[제외할 endpoint ID]"
    * */
    /*
    * 다양한 actuator endpoint
    * - 주요 endpoint ID 목록
    *   - beans: Spring container에 등록된 Spring bean 표시
    *   - conditions: condition을 통해서 bean을 등록할 때 평가 조건과 일치하거나 일치하지 않는 이유 표시
    *   - configprops: @ConfigurationProperties(type-safe configuration properties) 표시
    *   - env: 외부 설정값 Environment 정보 표시
    *   - health: 애플리케이션 health 정보 표시 → 뒤에서 자세히 설명
    *   - httpexchanges: HTTP 호출 응답 정보 표시, HttpExchangeRepository를 구현한 bean을 별도로 등록해야 함 → 뒤에서 자세히 설명
    *   - info: 애플리케이션 정보 표시 → 뒤에서 자세히 설명
    *   - loggers: 애플리케이션 로거 설정 표시 → 뒤에서 자세히 설명
    *   - merics: 애플리케이션 메트릭 정보 표시
    *   - mappings: @RequestMappint 정보 표시
    *   - threaddump: thread dump를 실행하여 표시
    *   - shutdown: 애플리케이션 종료, 기본적으로 비활성화 되어 있음
    * - 전체 endpoint 간단한 설명 공식 문서
    *   - https://docs.spring.io/spring-boot/reference/actuator/endpoints.html
    * */
    /*
    * health
    * - (사용 목적) 애플리케이션에 문제가 발생했을 때 문제를 인지하기 위함 - 문제 발생 시 추적 범위를 좁힐 수 있음
    * - 사용 방법
    *   - 단순히 애플리케이션이 동작 중인지를 판단하는 게 아니라 다양한 정보를 종합해서 health 정보를 판단
    *     - heatlh component 중 하나라도 문제가 있다면 전체 상태가 DOWN으로 표시
    *   - 자세한 정보를 확인하려면 다음 외부 설정값 지정 → management.endpoint.health.show-details=always
    *     - (1) DB 등 응답 여부 체크 / cf. JDBC spec상 검증하는 기능이 있음 → validationQuery 정보로 확인 가능
    *     - (2) 시스템 디스크 사용량 체크
    *     - (3) ping 체크
    *   - 덜 자세한 정보를 확인하려면 다음 외부 설정값 지정 → management.endpoint.health.show-components=always
    *     - 세 가지 항목에 대한 status만 확인
    *   - 자세한 health 기본 지원 기능 참고
    *     - https://docs.spring.io/spring-boot/reference/actuator/endpoints.html#actuator.endpoints.health.auto-configured-health-indicators
    *   - 원하는 경우 직접 health 기능 구현하여 추가 가능
    *     - https://docs.spring.io/spring-boot/reference/actuator/endpoints.html#actuator.endpoints.health.writing-custom-health-indicators
    * */
}
