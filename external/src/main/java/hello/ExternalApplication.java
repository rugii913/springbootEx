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
     *   - 2. Java 시스템 속성 → Java에서 지원하는 외부 설정, 실행한 JVM 안에서 접근 가능한 외부 설정
     *     - Java가 내부에서 미리 설정해두고 사용하는 property들도 있음
     *     - ex. file.encoding=UTF-8 → 이 property를 기본적인 파일 인코딩 정보 등으로 사용
     *   - 3. Java 커맨드 라인 인스 → 커맨드 라인에서 전달하는 외부 설정, 실행 시 main()의 args 파라미터로 전달
     *   - 4. 외부 파일(설정 데이터) → 프로그램에서 외부 파일을 직접 읽어서 사용
     * */
    /*
     * 외부 설정 1 - OS 환경 변수
     * - OS 환경 변수 조회
     *   - System.getenv() → 전체 OS 환경 변수를 Map으로 조회
     *   - System.getenv(key) → 특정 key에 대한 OS 환경 변수의 값을 String으로 조회
     * - 특징
     *   - OS의 모든 프로세스에서 공유
     *   - 특정 app에서만 사용하는 외부 설정값이 필요하다면 다른 외부 설정 방법을 활용해야 함
     * */
    /*
     * 외부 설정 2 - Java 시스템 속성(Java system properties)
     * - 설정 방법
     *   - 0. 내부 기본 property → JVM 내부에서 동작을 위해 미리 갖고 있는 property들
     *   - 1. Java 프로그램 실행 시 설정 → 외부로 설정을 분리할 수 있음
     *     - java 명령어의 "-D.." 옵션을 통해서 key=value 형식으로 부여
     *       - ex. java -Durl=dev -jar app.jar
     *       - cf. java 명령어만 입력할 경우 나오는 Usage 설명을 보면 알 수 있듯 java 명령어의 옵션들은 -jar보다 앞에 있어야 동작함
     *   - 2. 코드를 이용해 추가 → 이 경우 코드 안에서 설정하므로 외부로 설정을 분리하는 효과는 없음
     *     - System.setProperty(), System.setProperties()를 이용해 추가할 수 있음
     * - Java system properties 조회
     *   - System.getProperties() → Java system properties를 Properties type(Map의 subtype)으로 조회
     *   - System.getProperty(key) → 특정 key에 대한 Java system property의 값을 조회
     * */
    /*
     * 외부 설정 3 - command line 인수
     * - 설정 방법
     *   - 일반적인 String 전달
     *     - java -jar [jar 파일] arg1 arg2 ...
     *     - 필요한 데이터를 마지막 위치에 공백 문자로 구분해서 전달
     *       - 데이터 자체에 공백이 있는 경우 ""로 묶어서 전달
     *     - Java app 실행 시점에 외부 설정값을 main() 메서드의 args 파라미터로 전달
     *   - key=value 형식 전달 불가능
     *     - "url=devdb"과 같은 데이터를 전달해서 사용하려면 직접 parsing 필요
     *     - 직접 parsing 하는 불편함을 줄이기 위해 Spring이 제공하는 command line option 인수 활용 가능(외부 설정 4 참고)
     * - command line 인수 조회
     *   - main()이 파라미터로 받은 args로 조회
     * */
    /*
     * 외부 설정 4 - command line 옵션 인수
     * */
    /*
     * 외부 설정 4-1 - command line 옵션 인수와 Spring Boot
     * */
    /*
     * 외부 설정에 대한 Spring의 통합
     * */
}
