package memory;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/*
* 아래와 같은 클래스 작성 후 반드시 자동 구성 대상을 지정하는 파일에 내용을 채워줘야 함
* [프로젝트 루트]/src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
* - 빌드 후에도 이 파일이 빌드된 jar의 META-INF 디렉토리 하위로 들어가 자동 구성 정보를 넘김
* */
@AutoConfiguration
@ConditionalOnProperty(name = "memory", havingValue = "on")
public class MemoryAutoConfig {

    @Bean
    public MemoryController memoryController() {
        return new MemoryController(memoryFinder()); // cf. @Configuration이 붙은 클래스에서는 CGLIB 이용하여 싱글톤 보장
    }

    @Bean
    public MemoryFinder memoryFinder() {
        return new MemoryFinder();
    }
}
