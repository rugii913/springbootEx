package external;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

@Slf4j
public class JavaSystemProperties {

    public static void main(String[] args) {
        Properties properties = System.getProperties();
        for (Object key : properties.keySet()) {
            log.info("prop {} = {}", key, System.getProperty(String.valueOf(key)));
        }

        System.setProperty("url", "dev_db");
        System.setProperty("password", "12345");

        log.info("url = {}", System.getProperty("url"));
        log.info("username = {}", System.getProperty("username"));
        log.info("password = {}", System.getProperty("password"));
    }
}
