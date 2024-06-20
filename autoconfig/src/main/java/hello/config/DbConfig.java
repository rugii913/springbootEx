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
@Configuration
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
