package hello;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EnvironmentCheck {

    // Java system property(VM 옵션)로 "-Durl=proddb -Dredis-url=redis -Dredis-password=12345"을 넘긴다고 가정
    // args로 "--url=devdb --url=devdb2 --username=dev_user --password=dev_pw --activate mode=on"을 넘긴다고 가정
    private final Environment env;

    public EnvironmentCheck(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void init() {
        String redisUrl = env.getProperty("redis-url");
        String redisPassword = env.getProperty("redis-password");
        String url = env.getProperty("url"); // 더 범위가 좁은 command line option 인수가 우선순위를 가짐
        // 같은 key의 command line option 인수가 여러 개(List<String>)일 경우 "value1,value2" 형태 String으로 들어옴
        String username = env.getProperty("username");
        String password = env.getProperty("password");

        log.info("env(Java system property) redisUrl = {}", redisUrl);
        log.info("env(Java system property) redisPassword = {}", redisPassword);
        log.info("env(command line option argument) url = {}", url);
        log.info("env(command line option argument) username = {}", username);
        log.info("env(command line option argument) password = {}", password);
    }
}
