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

/*
* 자동 구성된 라이브러리 버전 변경하기
* - 버전 변경이 필요한 경우
*   - 완벽한 것은 없기 때문에 라이브러리 충돌이 있을 수도 있음
*   - 혹은 버그, 보안 이슈 등으로 인해 버전을 꼭 바꿔야하는 경우도 있을 수 있음
*   - 이런 경우 자동 구성된 라이브러리 버전을 ext[".."] = "버전"을 이용해 변경 지정할 수 있음
*     - 직접 지정한 버전 값이 우선 적용
* - ext[".."] = "버전"에서 첫번째 대괄호에 들어갈 property 값은 다음의 Spring Boot 공식 문서에서 확인 가능
*   - https://docs.spring.io/spring-boot/appendix/dependency-versions/properties.html
* */
// ext["querydsl.version"] = "5.0.0"

dependencies {
    /*
    * - 웹 애플리케이션에서 라이브러리 관리의 어려움 → 처음 프로젝트를 세팅하는 데에도 많은 시간이 소요됨
    *   - 라이브러리 선택 문제 → 어떤 라이브러리를 사용할 것인지
    *   - 각 라이브러리의 버전 문제 → 각 버전의 특징
    *   - 라이브러리 간 혹은 라이브러리의 버전 간 호환성 문제 → 바로 드러나지 않는 가장 어려운 문제
    * - Spring Boot의 해결책
    *   - 버전 및 호환성 문제는 Spring에서 dependency management 플러그인 제공
    *   - 라이브러리 선택 문제는 Spring Boot에서 starter들을 제공하여 고민을 덜 수 있음
    *   - 위처럼 편리하게 사용할 수 있는데, 유연하게 커스터마이징할 수 있는 확장 포인트도 제공함
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
//    // - Spring Web MVC
//    implementation("org.springframework:spring-webmvc")
//    // - embedded Tomcat → spring-boot-starter-tomcat에 포함됨
//    implementation("org.apache.tomcat.embed:tomcat-embed-core")
//    // - JSON 처리 → spring-boot-starter-json에 포함됨
//    implementation("com.fasterxml.jackson.core:jackson-databind")
//    // - Spring Boot 관련 → spring-boot-starter 등에 포함됨
//    implementation("org.springframework.boot:spring-boot")
//    implementation("org.springframework.boot:spring-boot-autoconfigure")
//    // - log 관련 → spring-boot-starter-logging에 포함됨
//    implementation("ch.qos.logback:logback-classic")
//    implementation("org.apache.logging.log4j:log4j-to-slf4j")
//    implementation("org.slf4j:jul-to-slf4j")
//    // - yml 관련 → spring-boot-starter에 포함됨
//    implementation("org.yaml:snakeyaml")

    /*
    * 3. Spring Boot starter
    * - 특정 목적으로 프로젝트를 구성할 때 일반적으로 필요한 라이브러리를 세트로 가져오는 spring-boot-starter 제공
    *   - Spring Initializr로 프로젝트를 구성할 때도 주로 spring-boot-starter들을 이용하여 build.gradle을 만들어줌
    *   - starter가 다른 starter를 의존할 수도 있음
    * - Spring Boot starter의 이름 패턴
    *   - (공식) spring-boot-starter-[particular type of application]
    *   - (비공식) [third party project 이름]-spring-boot-starter
    *     - ex. mybatis-spring-boot-starter
    * - starter의 목록도 공식 문서에서 확인 가능
    *   - https://docs.spring.io/spring-boot/reference/using/build-systems.html#using.build-systems.starters
    *   - 자주 사용할만한 starter로는 spring-boot-starter, jdbc, data-jpa, data-mongodb, data-redis, thymeleaf, web, validation, batch 정도가 있음
    * - 어떤 프로젝트 구성 시 필요한 라이브러리가 있으면 일단 Spring Boot starter에서 찾아보는 것도 괜찮은 방법
    * */
    implementation("org.springframework.boot:spring-boot-starter-web")

//    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
