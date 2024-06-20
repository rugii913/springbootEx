plugins {
    java
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "hello"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(annotationProcessor.get()) // dependencies에서 annotationProcessor에 지정된 라이브러리가 compileOnly 구성 시에도 포함되도록 하는 설정
    }
}

repositories {
    mavenCentral()
}

dependencies {
    /*
    * 웹 애플리케이션에서 라이브러리 관리의 어려움 → 처음 프로젝트를 세팅하는 데에도 많은 시간이 소요됨
    * - 라이브러리 선택 문제 → 어떤 라이브러리를 사용할 것인지
    * - 각 라이브러리의 버전 문제 → 각 버전의 특징
    * - 라이브러리 간 혹은 라이브러리의 버전 간 호환성 문제 → 바로 드러나지 않는 가장 어려운 문제
    * */
    
    /*
    * 1. 라이브러리 직접 지정
    * - 아래는 spring-boot-starter-web을 지정할 경우 연관된 종속성으로 들어오는 라이브러리들
    *   - 이들 외에도 다른 라이브러리들도 조금 더 들어옴
    * */
//    // - Spring Web MVC
//    implementation("org.springframework:spring-webmvc:6.1.9")
//    // - embedded Tomcat → spring-boot-starter-tomcat에 포함됨
//    implementation("org.apache.tomcat.embed:tomcat-embed-core:10.1.24")
//    // - JSON 처리 → spring-boot-starter-json에 포함됨
//    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
//    // - Spring Boot 관련 → spring-boot-starter 등에 포함됨
//    implementation("org.springframework.boot:spring-boot:3.3.0")
//    implementation("org.springframework.boot:spring-boot-autoconfigure:3.3.0")
//    // - log 관련 → spring-boot-starter-logging에 포함됨
//    implementation("ch.qos.logback:logback-classic:1.5.6")
//    implementation("org.apache.logging.log4j:log4j-to-slf4j:2.23.1")
//    implementation("org.slf4j:jul-to-slf4j:2.0.13")
//    // - yml 관련 → spring-boot-starter에 포함됨
//    implementation("org.yaml:snakeyaml:2.2")

    /*
    * 2. Spring Boot의 io.spring.dependency-management 플러그인을 이용한 라이브러리 버전 관리
    * - 특정 라이브러리에 대해서는 버전을 지정하지 않아도 호환성을 고려하여 자동으로 최적화된 버전을 선택해줌
    *   - 즉 Spring Boot 버전만 지정하면 나머지 라이브러리는 안전하게 가져올 수 있음
    * - build 시 io.spring.dependency-management 플러그인이 spring-boot-dependencies에 있는 BOM 정보를 참고함
    *   - cf. BOM(Bill Of Materials): 자재 명세서, 제품을 구성하는 모든 부품들에 대한 목록
    *   - build 시에만 사용되므로 IDE에서 Gradle dependency tree로 확인할 수는 없음
    *   - 다음 링크의 build.gradle 파일의 bom 항목에서 각 라이브러리 버전이 관리됨을 확인할 수 있음
    *     - https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-dependencies/build.gradle
    *   - 프로젝트에서 지정한 Spring Boot 버전을 참고하여 최적화된 라이브러리를 가져옴
    * - Spring 공식 문서에서 Spring Boot에서 관리하는 dependency versions 확인 가능
    *   - https://docs.spring.io/spring-boot/appendix/dependency-versions/coordinates.html
    * - Spring Boot에서 관리하지 않는 외부 라이브러리의 경우 버전을 직접 적어줘야 함 - ex. MyBatis
    * */
    // - Spring Web MVC
    implementation("org.springframework:spring-webmvc")
    // - embedded Tomcat → spring-boot-starter-tomcat에 포함됨
    implementation("org.apache.tomcat.embed:tomcat-embed-core")
    // - JSON 처리 → spring-boot-starter-json에 포함됨
    implementation("com.fasterxml.jackson.core:jackson-databind")
    // - Spring Boot 관련 → spring-boot-starter 등에 포함됨
    implementation("org.springframework.boot:spring-boot")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    // - log 관련 → spring-boot-starter-logging에 포함됨
    implementation("ch.qos.logback:logback-classic")
    implementation("org.apache.logging.log4j:log4j-to-slf4j")
    implementation("org.slf4j:jul-to-slf4j")
    // - yml 관련 → spring-boot-starter에 포함됨
    implementation("org.yaml:snakeyaml")

//    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
