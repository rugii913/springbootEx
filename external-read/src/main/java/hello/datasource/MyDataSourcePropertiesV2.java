package hello.datasource;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.Duration;
import java.util.List;

@Getter // 외부 설정 주입 방식으로 Java bean property 방식이 아닌 생성자 주입을 사용하더라도 getter는 필요
@ConfigurationProperties("my.datasource") // 외부 설정을 주입 받는 객체임을 표시, value로 외부 설정 key의 묶음 시작점을 넘김
public class MyDataSourcePropertiesV2 {

    /*
    * 외부 설정 주입 시 Java bean property 방식이 아닌 생성자 주입을 사용
    * - 생성자가 하나일 경우 Spring 3.0 이후로는 @ConstructorBinding 생략 가능
    * - 생성자 파라미터에 @DefaultValue를 붙여 외부 설정이 없는 경우 사용할 기본값 지정 가능
    * - 의도와 다르게 setter를 사용하여 값을 변경하는 것을 막을 수 있음 
    * */
    private final String url;
    private final String username;
    private final String password;
    private final Etc etc;

    @ConstructorBinding // Spring 3.0 이후 생략 가능
    public MyDataSourcePropertiesV2(String url, String username, String password, @DefaultValue Etc etc) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.etc = etc;
    }

    @Getter
    public static class Etc {
        private final int maxConnection;
        private final Duration timeout;
        private final List<String> options;

        @ConstructorBinding // Spring 3.0 이후 생략 가능
        public Etc(int maxConnection, Duration timeout, @DefaultValue("DEFAULT") List<String> options) {
            this.maxConnection = maxConnection;
            this.timeout = timeout;
            this.options = options;
        }
    }
}
