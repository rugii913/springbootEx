package external;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
// OS 환경 변수
public class OsEnv {

    public static void main(String[] args) {
        Map<String, String> envMap = System.getenv();
        for (String key : envMap.keySet()) {
            log.info("env {} = {}", key, System.getenv(key));
        }
    }
}
