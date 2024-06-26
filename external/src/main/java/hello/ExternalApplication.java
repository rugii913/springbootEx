package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExternalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExternalApplication.class, args);
    }

    /*
    * 외부 설정 간 우선 순위 → 맨 마지막 부분에 설명
    * - 참고 자료 https://docs.spring.io/spring-boot/reference/features/external-config.html
    * */
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
     * 외부 설정 3-1 - Spring Boot의 command line option 인수
     * - 설정 방법
     *   - Spring Boot의 방식, 인수가 "--"(dash 2개)로 시작하면 Spring Boot에서 key=value 형식으로 인식
     * - 조회 방법
     *   - ApplicationArguments 인터페이스의 getOptionNames(), getOptionValues(), getNonOptionArgs() 이용
     * - 동작 구조
     *   - ApplicationArguments 인터페이스, DefaultApplicationArguments 구현 클래스, CommandLinePropertySource 추상 클래스 이용
     *     - arg 데이터를 ApplicationArguments ADT로 변환하여 사용
     *       - 그 subtype인 DefaultApplicationArguments에서는 source라는 property를 갖도록 함
     *     - source property는 CommandLinePropertySource, PropertySource의 subtype인 Source type
     *       - PropertySource는 내부에 또 source property를 갖고 있고
     *       - SimpleCommandLinePropertySource에서 source property의 generic type이 CommandLineArgs로 지정됨
     *       - CommandLineArgs type은 Map type의 optionsArgs property와 List type의 nonOptionArgs를 가짐
     *       - parsing은 SimpleCommandLinePropertySource의 생성자에서 SimpleCommandLineArgsParser의 parse()를 호출하며 동작함
     *   - 정리하면 new DefaultApplicationArguments(args)로 ApplicationArguments type 객체를 생성할 때
     *     - 일정 로직에 따라 Map 형식 optionsArgs, List nonOptionArgs이 나뉘어져, 이미 구현된 parsing 로직을 사용하는 것
     *   - value는 List<String> 형태가 되어 하나의 key에 여러 value를 줄 수 있음
     * - command line option 인수를 담고 있는 ApplicationArguments의 Spring bean 등록
     *   - Spring Boot는 command line option 인수를 활용할 수 있는 ApplicationArguments를 Spring bean으로 등록해둠
     *     - 해당 type 객체에 입력받은 command line을 모두 저장해둠(non option arguments 포함)
     *     - 해당 bean을 이용하면 command line 인수로 입력받은 값을 어디에서든 사용할 수 있음
     *   - 즉 main()에서 뿐만이 아니라 app 전체에서 코드를 통해 args 값을 이용할 수 있다는 것
     *     - 특히 단순 command line 인수가 아니라 "--"가 붙은 Spring boot의 command line option 인수라면 key=value 형식으로 이용 가능
     * */
    /*
     * 외부 설정에 대한 Spring의 통합
     * - cf. Spring Boot가 아닌 Spring Core의 기능
     * - 필요성
     *   - 앞서 살펴본 OS 환경 변수, Java 시스템 속성, 커맨드라인 옵션 인수 모두 단순하게 생각하면 key=value 형식의 외부 설정값
     *   - 그런데 어디에 있는 외부 설정값을 읽어야 하는지에 따라 읽는 방법이 다름
     *     - System.getenv(), System.getProperties(), main의 args 또는 ApplicationArguments bean의 source
     *   - 문제점
     *     - 환경 변수를 두는 곳이 변경(ex. OS 환경 변수에서 Java 시스템 속성으로)되면
     *     - 환경 변수를 읽어들이는 소스 코드가 변경되어야 하고, 다시 빌드해야 함
     * - 해결 방법 → 추상화
     *   - abstract class PropertySource를 통한 추상화
     *     - 각각의 외부 설정을 조회하는 XxxPropertySource 구현체를 Spring 로딩 시점에 생성
     *     - Environment bean에서 사용할 수 있도록 연결해둠 
     *   - interface Environment를 통한 추상화
     *     - 외부 설정의 종류와 관계 없이 Environment bean을 통해 환경 변수 조회 가능
     *   - **사용하는 외부 설정이 변경되더라도 소스코드는 변경되지 않아도 되도록, 변경되는 부분과 변경되지 않는 부분을 잘 나누어 놓은 것**
     * - key 적용 우선 순위
     *   - 더 유연한 것이 우선권을 가짐 → file 형태(변경 어려움) 외부 설정보다 Java 시스템 속성(실행 시 원하는 값 부여)이 우선권을 가짐
     *   - 범위가 넓은 것보다 좁은 것이 우선권을 가짐 → Java 시스템 속성보다 커맨드 라인 옵션 인수가 우선권을 가짐
     * */
    /*
    * 외부 설정 4 - 파일 형태 설정
    * - 필요성
    *   - 외부 설정값이 많아지면 앞에서 언급한 OS 환경 변수, Java 시스템 속성, 커맨드라인 옵션 인수를 사용하기 불편
    * - (방법 1) 외부 파일
    *   - 사용 방법
    *     - 실행시킬 jar 파일을 둘 경로에 application.properties 파일을 두고 key=value 형식 설정값 작성
    *   - 단점
    *     - 각 machine마다 설정 파일을 관리해줘야해서 귀찮음, 물론 어느 정도 자동화하는 방법은 찾을 수 있음
    *     - 외부 파일의 변경 이력을 소스코드 변경 이력과 연결하여 확인하기 어려움
    *   - 단점에도 불구하고 이 방법을 사용해야하는 경우가 있을 수 있음
    * - (방법 2) 내부 파일
    *   - 설정 파일을 프로젝트 내부에 포함, 빌드 시점에 소스코드와 함께 빌드, 배포 시 설정 정보도 함께 배포
    *     - jar 파일에 설정 데이터 파일까지 포함해서 관리
    *     - profile 개념 → Spring Boot의 도움을 받아, 각 환경마다 다른 설정 파일을 사용하도록 구성 가능함
    *     - 설정 파일 변경 이력을 소스코드 변경 이력과 함께 관리 가능
    * - (방법 2-1) 분리된 내부 파일
    *   - 작성 시
    *     - 프로젝트 src/main/resources 경로에 application-[profile 이름].properties(혹은 yml) 파일 작성
    *       - ex. application-prod.properties에 prod 환경에서 필요한 설정값 작성
    *       - ex. application-dev.properties에 dev 환경에서 필요한 설정값 작성
    *   - 사용 시
    *     - 실행 시 외부 설정 spring.profiles.active key의 value로 profile 이름을 넘기면, 해당 profile의 내부 파일 정보를 설정값으로 사용
    *       - ex. Java System property로 "-Dspring.profiles.active=dev"를 넘김
    *       - ex. commandline option argument로 "--spring.profiles.active=dev"를 넘김
    *   - 설정이 여러 파일로 분리되어 한 눈에 들어오지 않을 수 있음
    * - (방법 2-2) 하나의 내부 파일
    *   - 작성 시
    *     - 프로젝트 src/main/resources 경로에 application.properties(혹은 yml) 파일 작성
    *     - 논리적인 영역 구분
    *       - 물리적인 하나의 파일 안에서 논리적으로 각 profile별 설정 문서를 분리
    *       - application.properties의 경우 #--- 혹은 !--- 사용
    *       - application.yml의 경우 --- 사용
    *       - (주의사항)
    *         - 논리 문서 구분 기호에는 선행 공백이 없어야 하며, 정확히 3개의 -가 있어야 함(2개도 x, 4개도 x)
    *         - 구분 기호 바로 앞과 뒤의 줄은 주석 줄(주석 접두사 #로 시작하는 줄)이 아니어야 함
    *     - 각 영역마다 spring.config.activate.on-profile key로 profile 값 지정
    *       - 어떤 profile에서 해당 영역 논리 문서가 설정 데이터로 활성화되는지 지정하는 것
    * */
    /*
    * 파일 형태 설정에서의 우선 순위
    * - default profile과 파일 형태 설정의 기본값
    *   - spring.profiles.active에 아무 값도 넘기지 않으면(profile을 지정하지 않으면) default profile 적용
    *     - 특정 spring.config.activate.on-profile이 명시된 파일들만 있다면 파일에서 설정한 설정값들은 모두 null
    *   - 보통 local에서 손쉽게 사용할 때는 default profile과 파일 형태 설정의 기본값을 사용함
    *   - 파일 형태 설정에서 기본값을 지정할 수 있고, 이 지정된 profile(spring.profiles.active 값)과는 무관하게 항상 사용
    *   - 정리하면
    *     - 설정 파일의 기본값 지정 → profile(spring.profiles.active 값)과 무관하게 항상 동작 → default profile에서도 동작
    * - Spring이 파일 형태 설정을 읽을 때의 동작 방식
    *   - 파일 형태 설정(application.properties 혹은 yml)을 읽을 때 위에서 아래로 순서대로 읽으면서 설정
    *   - 설정 파일의 기본값은 무조건 읽음
    *     - profile이 지정된 경우 해당하는 논리 문서(spring.config.activate.on-profile의 값과 활성화된 profile 값이 일치)도 읽음
    *     - 앞서 말했듯 읽는 순서는 위에서 아래
    *     - 중복된 key=value 설정은 덮어씀
    *   - 따라서 기본값 설정 논리 문서 영역은 profile 지정 설정 논리 문서 영역보다 아래에 두면 안 됨
    *     - 기본값 설정은 활성화된 profile에 관계 없이 항상 읽히고
    *     - 특정 profile을 지정했더라도 위에서부터 아래로 읽기 때문에 아래에 있는 기본값 설정이 읽힘
    * - cf. 다수 프로파일 활성화 가능
    *   - ex. Java system property 사용 시 VM 옵션으로 "-Dspring.profiles.active=dev,prod" 넘김
    *   - ex. commandline option argument 사용 시 args로 "--spring.profiles.active=dev,prod" 넘김
    * - 통상적인 설정 문서 구성 방식
    *   - 위에 로컬 개발 환경에서 사용할 기본값 논리 문서를 두고
    *   - 아래에 특정 profile 설정 논리 문서를 두어, profile마다 변경해야할 설정값을 덮어쓰도록 함
    * */
    /*
    * 외부 설정 간의 우선 순위
    * - 공식 문서 Externalized Configuration https://docs.spring.io/spring-boot/reference/features/external-config.html
    *   - 번호가 더 큰 것이 더 높은 우선 순위
    * - 일반 원칙으로 생각해볼 것
    *   - 더 유연한 것이 우선 → ex. 변경하기 어려운 설정 데이터 파일보다 실행 시 원하는 값을 줄 수 있는 Java system properties가 우선
    *   - 범위가 넓은 것보다 좁은 것이 우선 → ex. OS 환경 변수보다 Java system properties가 우선
    * - 자주 사용하는 것들의 우선순위만 살펴보면(아래가 더 높은 우선 순위)
    *   - 설정 데이터 파일(application.properties 또는 yml)
    *     - 설정 데이터 간의 우선순위 - https://docs.spring.io/spring-boot/reference/features/external-config.html#features.external-config.files
    *       - jar 내부 application.properties
    *       - jar 내부 특정 profile에 적용되는 application-[profile 이름].properties
    *       - jar 외부 application.properties
    *       - jar 외부 특정 profile에 적용되는 application-[profile 이름].properties
    *     - 외부 jar가 더 유연하므로 우선 순위 높음, profile이 정해진 파일이 범위가 더 좁으므로 우선 순위 높음
    *   - OS environment variables
    *   - Java system properties(System.getProperties())
    *   - commandline arguments(commandline option arguments 포함)
    *   - @TestPropertySource(테스트에서 사용)
    * - 조회 시 실제 동작 방식
    *   - 설정값이 덮어씌워지는 것이 아니라
    *   - Environment를 통해 조회 시 우선순위가 높은 설정의 key에 대한 value가 조회될 뿐(먼저 조회되어 return)
    * - 권장하는 방식
    *   - 기본적으로 설정 데이터 파일(application.properties)에 설정을 보관하고
    *   - 일부 속성을 변경할 필요가 있을 때
    *     - Java system properties 혹은 commandline option arguments를 사용하여 조회되도록 함
    *     - 혹은 jar 외부 설정 데이터 파일을 두어 일부 속성만 변경하여 조회되도록 함
    * */
}
