package hello.selector;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

public class ImportSelectorTest {

    /*
    * - @Import(..)로 설정 정보를 추가하는 방법 중 정적인 방법
    * */
    @Test
    void staticConfig() {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(StaticConfig.class);
        HelloConfig bean = appContext.getBean(HelloConfig.class);
        Assertions.assertThat(bean).isNotNull();
    }

    @Import(HelloConfig.class)
    @Configuration
    public static class StaticConfig {
    }

    /*
     * - @Import(..)로 설정 정보를 추가하는 방법 중 동적인 방법
     * */
    @Test
    void selectorConfig() {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(SelectorConfig.class);
        HelloConfig bean = appContext.getBean(HelloConfig.class);
        Assertions.assertThat(bean).isNotNull();
    }

    @Configuration
    @Import(HelloImportSelector.class)
    public static class SelectorConfig {
    }
}
