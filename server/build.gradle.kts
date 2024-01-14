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

task("explodedWar", type = Copy::class) {
    into(layout.buildDirectory.dir("/exploded"))
    with(tasks.war.get())
}
/*
// https://jolly-sally.tistory.com/63 - [Gradle] war 실행 시 Exploded war 생성하기 (Groovy, Kotlin DSL)
// 위 블로그에는 아래처럼 나와있지만 현재 작성한 것처럼 작성해도 실행되는 듯하다.
val explodedWar by tasks.register<Copy>("explodedWar") {
    into("$buildDir/libs/exploded")
    with(tasks.war.get())
}
tasks.war {
    finalizedBy(explodedWar)
}
 */

tasks.test {
    useJUnitPlatform()
}
