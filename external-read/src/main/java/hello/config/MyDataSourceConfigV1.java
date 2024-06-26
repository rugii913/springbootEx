package hello.config;

import hello.datasource.MyDataSource;
import hello.datasource.MyDataSourcePropertiesV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@Slf4j
// @EnableConfigurationProperties({MyDataSourcePropertiesV1.class}) // 예제에서 흔히 사용하는 방법
// 사용 대상인 @ConfigurationProperties 붙은 클래스를 value로 넘김 → 대상 configuration properties 클래스가 bean으로 등록
public class MyDataSourceConfigV1 {

    private final MyDataSourcePropertiesV1 properties;

    public MyDataSourceConfigV1(MyDataSourcePropertiesV1 properties) {
        this.properties = properties;
    }

    @Bean // cf. @Bean은 @Configuration 붙은 클래스 안에 있지 않더라도 동작함 → api docs의 @Bean Lite Mode 침고
    public MyDataSource dataSource() {
        return new MyDataSource(
                properties.getUrl(),
                properties.getUsername(),
                properties.getPassword(),
                properties.getEtc().getMaxConnection(),
                properties.getEtc().getTimeout(),
                properties.getEtc().getOptions()
        );
    }
}
