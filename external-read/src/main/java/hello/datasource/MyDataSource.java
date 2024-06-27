package hello.datasource;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

@Slf4j
public class MyDataSource {

    private final String url;
    private final String username;
    private final String password;
    private final int maxConnection; // 소스 코드에서는 camelCase 사용, 설정 데이터 파일에서는 kebeb-case로 표기 권장 → max-connection
    private final Duration timeout;
    private final List<String> options;

    public MyDataSource(String url, String username, String password, int maxConnection, Duration timeout, List<String> options) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.maxConnection = maxConnection;
        this.timeout = timeout;
        this.options = options;
    }

    @PostConstruct
    public void init() {
        log.info("url = {}", url);
        log.info("username = {}", username);
        log.info("password = {}", password);
        log.info("maxConnection = {}", maxConnection);
        log.info("timeout = {}", timeout);
        log.info("options = {}", options);
    }
}
