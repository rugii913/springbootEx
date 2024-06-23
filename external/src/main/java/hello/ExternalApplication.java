package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExternalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExternalApplication.class, args);
    }

    /*
     * 외부 설정의 필요성 및 외부 설정 방법
     * - **여러 환경이 존재하더라도, 빌드는 한 번만, 실행 시점에 동적으로 외부 설정값 주입**
     *   - 하나의 app을 여러 다른 환경에서 사용해야할 때(ex. 개발 환경, 운영 환경, ...)
     *     - 각 환경에 따라 서로 다른 설정값 존재
     *     - ex. 개발 환경 DB url, 운영 환경 DB url 별도로 존재하는 경우
     *   - 이 때 app에 설정값을 포함하여 각 환경에서 필요할 때마다 빌드하게 되면 유연성이 떨어지고, 같은 소스코드임을 보장하기 어려움
     *   - 설정값을 실행 시점에 각 환경에 따라 외부에서 주입하면
     *     - 같은 소스코드임을 보장할 수 있고, 유연성 확보됨
     * - 주입 concept의 핵심
     *   - 변하는 것과 변하지 않는 것을 분리 → 유지보수하기 좋은 애플리케이션 개발
     *   - 여기에서는 환경이 변해서 설정값을 변경해야하더라도
     *     - 설정값은 외부 설정값으로 분리해두어서
     *     - 소스코드와 빌드 결과물은 변하지 않도록 유지
     * - 외부 설정 방법
     *   - 1. OS 환경 변수 → 해당 OS를 사용하는 모든 프로세스에서 사용
     *   - 2. Java 시스템 속성 → Java에서 지원하는 외부 설정, 해당 JVM 내에서 사용
     *   - 3. Java 커맨드 라인 인스 → 커맨드 라인에서 전달하는 외부 설정, 실행 시 main()의 args 파라미터로 전달
     *   - 4. 외부 파일(설정 데이터) → 프로그램에서 외부 파일을 직접 읽어서 사용
     * */
}
