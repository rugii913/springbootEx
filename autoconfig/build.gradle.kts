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
    // Spring Initializr에서 Spring Web, JDBC API, H2 Database, Lombok을 선택하면 비슷하게 구성해줌
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    runtimeOnly("com.h2database:h2")
    annotationProcessor("org.projectlombok:lombok")

    // memory project를 가져옴
//    implementation(project(":memory")) // → Gradle multimodule로 구성했을 때 사용할 수 있는 방식
    implementation(files("libs/memory-0.0.1-SNAPSHOT.jar")) // → jar 파일을 직접 가져올 수 있는 방식

    // test 관련
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testCompileOnly("org.projectlombok:lombok") // test에서도 Lombok 사용
    testAnnotationProcessor("org.projectlombok:lombok") // test에서도 Lombok 사용
}

tasks.test {
    useJUnitPlatform()
}
