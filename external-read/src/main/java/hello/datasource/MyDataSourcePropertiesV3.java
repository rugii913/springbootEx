package hello.datasource;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.validator.constraints.time.DurationMax;
import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.util.List;

@Getter // 외부 설정 주입 방식으로 Java bean property 방식이 아닌 생성자 주입을 사용하더라도 getter는 필요
@ConfigurationProperties("my.datasource") // 외부 설정을 주입 받는 객체임을 표시, value로 외부 설정 key의 묶음 시작점을 넘김
@Validated // 검증을 위해 필요
public class MyDataSourcePropertiesV3 {

    /*
    * 외부 설정 주입 시 Java bean validation을 이용해 검증
    * - @ConfigurationProperties가 붙은 클래스가 bean으로 등록되므로 Java bean validation 사용 가능
    * - jakarta.validation으로 시작하는 것은 Java 표준 검증기에서 지원하는 기능
    *   - org.hibernate.validator로 시작하는 것은 표준은 아니고 hibernate 검증기라는 표준의 구현체에서 제공하는 기능이나, 많은 경우 hibernate 검증기를 사용하므로 크게 문제 없음
    * - application loading 시점에 예외를 확인할 수 있는 장점
    * */
    @NotEmpty private final String url;
    @NotEmpty private final String username;
    @NotEmpty private final String password;
    private final Etc etc;

    @ConstructorBinding // Spring 3.0 이후 생략 가능
    public MyDataSourcePropertiesV3(String url, String username, String password, Etc etc) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.etc = etc;
    }

    @Getter
    public static class Etc {
        @Min(1) @Max(999) private final int maxConnection;
        @DurationMin(seconds = 1) @DurationMax(seconds = 60) private final Duration timeout;
        private final List<String> options;

        @ConstructorBinding // Spring 3.0 이후 생략 가능
        public Etc(int maxConnection, Duration timeout, @DefaultValue("DEFAULT") List<String> options) {
            this.maxConnection = maxConnection;
            this.timeout = timeout;
            this.options = options;
        }
    }
}
