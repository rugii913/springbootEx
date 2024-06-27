package hello.config;

import hello.datasource.MyDataSource;
import hello.datasource.MyDataSourcePropertiesV1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MyDataSourceConfigV1 {

    @Bean // @Configuration 붙은 클래스에서 @Bean으로 Spring bean 등록 → 예제에서 주로 보이는 방식은 아님
    public MyDataSourcePropertiesV1 myDataSourcePropertiesV1() {
        return new MyDataSourcePropertiesV1();
    }


    @Bean
    public MyDataSource dataSource() {
        MyDataSourcePropertiesV1 properties = myDataSourcePropertiesV1();

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
