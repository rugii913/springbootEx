package hello.datasource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data // lombok 활용 getter, setter - 외부 설정에 대한 기본 주입 방식은 Java bean property 방식으로 getter, setter 필요
@ConfigurationProperties("my.datasource") // 외부 설정을 주입 받는 객체임을 표시, value로 외부 설정 key의 묶음 시작점을 넘김
public class MyDataSourcePropertiesV1 {

    private String url;
    private String username;
    private String password;
    private Etc etc;

    @Data
    public static class Etc {
        private int maxConnection;
        private Duration timeout;
        private List<String> options = new ArrayList<>();
    }
}
