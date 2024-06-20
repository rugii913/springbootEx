package hello.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
