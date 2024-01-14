// 강의와는 다르게 Kotlin DSL을 사용해봄
plugins {
    id("java")
    id("war")
}

/*
group = "hello" // 없어도 동작하는 듯함
version = "1.0-SNAPSHOT" // 없어도 동작하는 듯함
sourceCompatibility = "17" // 아래 java { } 블럭 참고
*/

repositories {
    mavenCentral()
}

dependencies {
    // 서블릿
    implementation("jakarta.servlet:jakarta.servlet-api:6.0.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

tasks.test {
    useJUnitPlatform()
}
