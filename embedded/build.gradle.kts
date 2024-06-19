plugins {
    id("java")
    // id("war") // embedded tomcat을 사용하므로 war로 빌드하지 않음
}

group = "hello"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    // Jakarta servlet - https://projects.eclipse.org/projects/ee4j.servlet
    // embedded Tomcat 라이브러리에 servlet 종속성이 포함됨
    // implementation("jakarta.servlet:jakarta.servlet-api:6.1.0")
    
    // Spring Web MVC → Spring Core 등 포함(cf. Web MVC가 아니라 Web이면 context가 빠져있음)
    implementation("org.springframework:spring-webmvc:6.1.9")

    // embedded Tomcat
    implementation("org.apache.tomcat.embed:tomcat-embed-core:10.1.18")
}

tasks {
    test {
        useJUnitPlatform()
    }

    // Jar 생성
    register<Jar>("buildJar") { // type인 Jar::class는 task의 type
        manifest {
            attributes("Main-Class" to "hello.embedded.EmbeddedTomcatSpringMain")
        }
        with(jar.get()) // Jar가 CopySpec의 subtype
    }

    // FatJar 생성
    register<Jar>("buildFatJar") {
        manifest {
            attributes("Main-Class" to "hello.embedded.EmbeddedTomcatSpringMain")
        }
        duplicatesStrategy = DuplicatesStrategy.WARN
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
        with(jar.get())
    }
}
