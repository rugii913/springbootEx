package hello.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

@Slf4j
//@Configuration
/*
 * - @Configuration을 주석 처리하고 DbConfigTest.checkBean() 테스트를 돌려보면
 *   - "dataSource bean 등록" 등은 출력되지 않음
 *   - 그런데 DbConfigTest.checkBean() 테스트는 성공하며 객체가 출력됨 → null이 아님
 * - DataSource, TransactionManager, JdbcTemplate는 Spring Boot가 자동으로 구성해줌을 알 수 있음
 *   - 이 때문에 MemberRepositoryTest.memberTest()도 성공함
 * */
/*
* - auto configuration의 필요성 → 반복되는 bean 구성 코드 최소화
*   - 매 프로젝트마다 반복되는 Spring bean이 있음, bean 구성 시 관계가 복잡한 부분 있음 → 이런 코드를 직접 작성하지 않아 생산성 향상
* */
/*
* - spring-boot-autoconfigure 프로젝트(라이브러리)
*   - spring-boot-starter 라이브러리는 spring-boot-autoconfigure 라이브러리 종속성을 가짐
*   - spring-boot-autoconfigure-x.x.x.jar 내의 org.springframework.boot.autoconfigure 패키지 하위로 수 많은 자동 구성 설정을 확인할 수 있음
*     - ex. jdbc 패키지의 DataSourceAutoConfiguration, JdbcTemplateAutoConfiguration, DataSourceTransactionManagerAutoConfiguration
*     - ex. transaction 패키지의 TransactionAutoConfiguration
*   - auto configuration 대상 클래스를 Spring Boot 공식 문서에서 확인할 수 있음
*     - https://docs.spring.io/spring-boot/appendix/auto-configuration-classes/core.html
* - auto configuration 예시(JdbcTemplateAutoConfiguration, JdbcTemplateConfiguration)
*   - @Autoconfiguration → 자동 구성 bean이 갖고 있어야 하는 어노테이션, 자동 구성 실행 순서 등 지정
*   - @ConditionalOnClass → if처럼 생각, value로 받은 클래스가 classpath에 있는 경우에만 설정이 동작하도록 함
*     - @ConditionalXxx들에 대해서는 뒤에서 더 살펴볼 것
*   - @Import → @Configuration 붙은 설정 정보 클래스를 가져옴 - cf. XML 혹은 non-@Configuration 클래스는 @ImportResource 사용
*     - @Import로 가져오고 있는 JdbcTemplateConfiguration에 실제 JdbcTemplate bean을 등록하는 코드가 있음
*       - @ConditionalOnMissingBean(JdbcOperations.class) → 해당 type bean이 등록되어 있지 않은 경우 설정 동작
*       - 그런데 JdbcTemplate이 JdbcOpertaion의 subtype → 즉 개발자가 등록한 JdbcTemplate이 없는 경우에 해당 자동 구성이 동작한다는 것
* */
/*
* - Spring Boot auto configuration의 핵심
*   - (1) @Conditional
*   - (2) @AutoConfiguration
* */
public class DbConfig {
    /*
    * - Spring bean 직접 등록
    *   - DataSource, TransactionManager, JdbcTemplate을 Spring bean으로 직접 등록
    * */

    @Bean
    public DataSource dataSource() {
        log.info("dataSource bean 등록");
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setJdbcUrl("jdbc:h2:mem:test");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }
    
    @Bean
    public TransactionManager transactionManager(DataSource dataSource) {
        log.info("transactionManager bean 등록");
        return new JdbcTransactionManager(dataSource);
        // cf. JdbcTransactionManager는 DataSourceTransactionManager에 예외 처리 기능 보강된 것
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        log.info("jdbcTemplate 빈 등록");
        return new JdbcTemplate(dataSource);
    }
}
