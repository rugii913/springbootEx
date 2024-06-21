plugins {
    `java-library`
    /*
    * Spring Boot 플러그인을 제외한 이유
    * - Spring Boot 플러그인을 이용할 경우 executable JAR를 만드는 task를 기본적으로 세팅함
    *   - 라이브러리로 사용할 프로젝트이므로 executable JAR로 만들면 안 됨
    * */
    /*
    * java 플러그인과 java-library 플러그인
    * - url을 정확하게 지정하지 않고, 이렇게 간단하게 지정할 수 있는 플러그인은 Gradle의 core plugin(built-in plugin)
    *   - https://docs.gradle.org/current/userguide/plugin_reference.html
    *   - core 플러그인은 https://plugins.gradle.org/ 에서 찾을 수 없음
    * - 각 java 플러그인 및 java-library 플러그인에 대한 자세한 설명 공식 문서도 있음
    *   - java 플러그인 - https://docs.gradle.org/current/userguide/java_plugin.html#java_plugin
    *   - java-library 플러그인 - https://docs.gradle.org/current/userguide/java_library_plugin.html
    * - java, java-library 플러그인에 대한 기타 개인 블로그
    *   - https://ones1kk.github.io/posts/gradle-java-library-plugin/
    *   - https://robin00q.tistory.com/15
    * */
}

group = "memory"
version = "0.0.2-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.3.0")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.3.0")
}
