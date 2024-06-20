plugins {
    java
    id("org.springframework.boot") version "3.3.0"
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
    // - Spring Web MVC
    implementation("org.springframework:spring-webmvc:6.1.9")
    // - embedded Tomcat → spring-boot-starter-tomcat에 포함됨
    implementation("org.apache.tomcat.embed:tomcat-embed-core:10.1.24")
    // - JSON 처리 → spring-boot-starter-json에 포함됨
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
    // - Spring Boot 관련 → spring-boot-starter 등에 포함됨
    implementation("org.springframework.boot:spring-boot:3.3.0")
    implementation("org.springframework.boot:spring-boot-autoconfigure:3.3.0")
    // - log 관련 → spring-boot-starter-logging에 포함됨
    implementation("ch.qos.logback:logback-classic:1.5.6")
    implementation("org.apache.logging.log4j:log4j-to-slf4j:2.23.1")
    implementation("org.slf4j:jul-to-slf4j:2.0.13")
    // - yml 관련 → spring-boot-starter에 포함됨
    implementation("org.yaml:snakeyaml:2.2")

//    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
