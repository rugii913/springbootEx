package hello.config;

import hello.datasource.MyDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Slf4j
@Configuration
public class MyDataSourceValueConfig {

    /*
    * (1) 필드 주입 방식
    * - component의 field에 @Value를 붙임
    * - 필드 주입이므로 private이어도 가능(CGLIB 변조)
    * - 다만 final field에는 주입 불가능 - final 붙일 경우 컴파일 오류(final은 생성자에서 초기화 되어야 함)
    * */
    @Value("${my.datasource.url}")
    private String url;
    @Value("${my.datasource.username}")
    private String username;
    @Value("${my.datasource.password}")
    private String password;
    @Value("${my.datasource.etc.max-connection}")
    private int maxConnection;
    @Value("${my.datasource.etc.timeout}")
    private Duration timeout;
    @Value("${my.datasource.etc.options}")
    private List<String> options;

    @Bean
    public MyDataSource myDataSource1() {
        log.info("this = {}", this); // 출력 → this = hello.config.MyDataSourceValueConfig$$SpringCGLIB$$0@8dfe921
        return new MyDataSource(url, username, password, maxConnection, timeout, options);
    }

    /*
     * (2) 파라미터 주입 방식
     * - component의 method의 parameter에 @Value를 붙임
     * */
    @Bean
    public MyDataSource myDataSource2(
            @Value("${my.datasource.url}") String url,
            @Value("${my.datasource.username}") String username,
            @Value("${my.datasource.password}") String password,
            @Value("${my.datasource.etc.max-connection2:10}") int maxConnection, // 기본값은 :[기본값]을 외부 설정 key 뒤에 붙여줌
            @Value("${my.datasource.etc.timeout}") Duration timeout,
            @Value("${my.datasource.etc.options}") List<String> options
    ) {
        return new MyDataSource(url, username, password, maxConnection, timeout, options);
    }
}
