package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * - auto configuration의 필요성 → 반복되는 bean 구성 코드 최소화
 *   - 매 프로젝트마다 반복되는 Spring bean이 있음, bean 구성 시 관계가 복잡한 부분 있음 → 이런 코드를 직접 작성하지 않아 생산성 향상
 *     - 라이브러리를 개발하는 입장이 되면 이 필요성을 더 잘 느낄 수 있음
 *   - 그러면 라이브러리를 사용하는 클라이언트 개발자 입장에서 auto configuration은 메커니즘은 몰라도 되고 사용할 줄만 알면 되는가?
 *     - 자동 구성된 bean에 문제가 있는 경우, 문제를 해결하기 위해서라도 대략적인 메커니즘은 알 필요가 있음
 * */
/*
 * Spring Boot auto configuration의 핵심
 * - (1) @Conditional
 *   - Condition 인터페이스에서 구현한 matches() return value에 따라 @Configuration이 붙은 클래스의 설정 정보 등록 여부 판단
 *   - @Conditional, Condition은 Spring에서도 사용할 수 있고, 이를 확장한 @ConditionalOnXxx는 Spring Boot 필요
 * - (2) @AutoConfiguration
 *   - 자동 구성할 config 클래스에 @AutoConfiguration 어노테이션을 붙임
 *   - 더불어 [프로젝트 루트]/src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports라는 파일에
 *     - @AutoConfiguration을 붙인 클래스의 "패키지명.클래스명"이 명시되어야 함
 *   - 라이브러리를 빌드할 때 위 파일이 jar 파일의 META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports로 그대로 들어감
 *     - Spring Boot는 라이브러리 jar 파일에 있는 해당 위치, 해당 파일을 모두 검사하고
 *     - 명시된 클래스를 자동 구성을 위한 클래스로 사용함
 * */
@SpringBootApplication
public class AutoConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoConfigApplication.class, args);
    }
}
/*
 * Spring Boot auto configuration 동작 이해
 * - 위에서 보았듯 Spring Boot는
 *   - 각 라이브러리 jar 파일의 META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports 파일을 읽어서
 *   - Spring Boot 자동 구성으로 사용
 * - spring-boot-autoconfigure 라이브러리를 살펴보면
 *   - META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports에
 *   - 수많은 auto configuration 클래스들을 명시해둔 것을 확인할 수 있음
 * - 개발하는 프로젝트의 main()이 있는 클래스를 열어보면
 *   - run()에 해당 클래스를 넘김 → 해당 클래스를 설정 정보로 이용하겠다는 것
 *   - 해당 클래스에는 @SpringBootApplication 어노테이션이 붙어있음 → 이 부분에 여러 설정 정보들이 붙어 있음
 * - @SpringBootApplication에는
 *   - @EnableAutoConfiguration이 붙어 있음 → auto configuration을 활성화하는 기능
 * - @EnableAutoConfiguration에는
 *   - @Import(AutoConfigurationImportSelector.class)가 붙어 있음
 *     - @Import는 보통 @Configuration이 붙은 Spring 설정 정보를 포함할 때 사용함(물론 일반적인 bean도 가져올 수 있음)
 *     - 그런데 AutoConfigurationImportSelector.class에는 @Configuration이 붙어있지 않음
 *     - type 계층을 보면 AutoConfigurationImportSelector는 ImportSelector의 subtype
 * - ImportSelector 인터페이스
 *   - @Import(..)로 설정 정보를 추가하는 방법 2가지
 *     - (1) (정적인 방법) @Import의 value로 Xxx.class 평범한 클래스 정보를 넘김 → 동적으로 대상 변경 불가
 *     - (2) (동적인 방법) ImportSelector의 subtype인 Xxx.class를 넘김 → 동적으로 구성 대상 선택 가능
 *       - 좀 더 유연하게 열어둔 것
 *   - 즉 AutoConfigurationImportSelector(ImportSelector의 구현 클래스)에서
 *     - call stack을 보면 selectImports() → getAutoConfigurationEntry() → getCandidateConfigurations()
 *     - 위 getCandidateConfigurations() 호출 후 변수 configurations에 auto configuration 대상 클래스 이름 문자열들이 담김
 *       - ImportCandidates.load(AutoConfiguration.class, getBeanClassLoader()) → String.format(LOCATION, annotation.getName())
 *         - 여기서 ImportCandidates.LOCATION은 "META-INF/spring/%s.imports"이고
 *         - annotation.getName()은 org.springframework.boot.autoconfigure.AutoConfiguration이므로
 *         - 결과로 나오는 formatted string은 META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
 *       - class loader를 이용하여 각 라이브러리의 META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports 파일의 텍스트 확인 작업을 진행하는 것
 *     - 이 외에도 복잡한 로직으로 다른 작업을 진행(중복 제거 등등)
 * - 정리하면 다음의 과정을 통해 auto configuration 작업이 진행되는 것
 *   - @SpringBootApplication → @EnableAutoConfiguration → @Import(AutoConfigurationImportSelector.class)
 *     → META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports 파일을 열어서 구성 정보 클래스들이 선택됨
 *     → 해당 클래스들의 설정 정보가 spring container에 자동으로 등록되고 사용됨
 * */
/*
* 참고 사항
* - @AutoConfiguration은 component scan 대상이 되어선 안 됨
*   - @AutoConfiguration은 분명 config 정보이므로 @Configuration이 붙어있지만
*   - 앞에서 보았듯 별도 파일로 명시하여 설정 정보가 되도록 해야하지 component scan 대상이 되면 안 됨
*     - 일반 Spring 설정과 lifecycle이 다르기 때문, 이를 고려하지 않으면 중복 등록될 수도 있을 것
*   - 이를 위해 @SpringBootApplication에서
*     - @ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })와 같이
*     - @AutoConfiguration을 component scan 대상에서 제외하는 AutoConfigurationExcludeFilter 필터를 붙여두었음
*   - Spring Boot에서만 문제가 되는 이유는 auto configuration 자체가 Spring Boot에서만 동작하는 것이기 때문임
* - auto configuration 동작을 알아두어야 하는 이유
*   - 라이브러리를 만드는 경우가 아니면 @AutoConfiguration을 사용할 일이 거의 없지만
*   - 개발 진행 시 특정 빈들이 어떻게 등록되는지 파악해야 하는 경우가 있고 ex. 원하지 않는 bean이 등록된 경우 등
*     - 이 때 auto configuration 관련 코드를 읽을 수 있고 메커니즘을 알아야
*     - 문제에 대처가 가능하기 때문
* */
