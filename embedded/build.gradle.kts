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

    /*
    * - jar 빌드 task
    *   - Java의 main()를 실행하기 위해서는 jar 형식으로 빌드해야 함
    *   - jar 안에는 META-INF/MANIFEST.MF 파일이 있고,
    *     - 여기에 main()의 클래스를 지정해줘야 함
    *   - Gradle을 이용한 build 시 META-INF/MANIFEST.MF 파일에 내용을 작성하는 작업을 쉽게 처리할 수 있음
    * - task 실행
    *   - 내 경우에는 ./gradlew clean :embedded:buildJar로 실행함
    * - buildJar task의 문제점
    *   - library들을 포함하지 않아, 결과물 jar 파일을 java -jar로 실행 시 오류 발생하여 실행 불가능
    *   - 문제 발생 이유
    *     - jar 파일 내부에는 jar 파일이 포함될 수 없음 - 포함되어도 인식할 수 없음
    *     - 반면에 war 파일에는 jar 파일이 포함됨, 하지만 war는 WAS 위에서만 실행할 수 있음
    *   - 해결 방법
    *     - (1) MANIFEST 파일에 라이브러리 jar 파일 경로를 모두 적어주기 → 라이브러리 jar 파일도 함께 배포해야 하므로 피곤함
    *     - (2) fat jar(혹은 uber jar) 방식 사용 → 아래 buildFatJar 위에 설명
    * */
    // Jar 생성
    register<Jar>("buildJar") { // type인 Jar::class는 task의 type
        manifest {
            attributes("Main-Class" to "hello.embedded.EmbeddedTomcatSpringMain")
        }
        with(jar.get()) // Jar가 CopySpec의 subtype
    }

    /*
    * - buildFatJar task
    *   - library로 사용하는 jar를 모두 압축 해제하여 나온 class들을 전부 포함시켜 jar로 빌드하는 방식
    *   - library class 파일을 포함하므로 fat한 jar가 됨
    *   - from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }) 부분에서
    *     - library의 class를 모두 포함시킴
    * - fat jar
    *   - 생성된 jar 파일을 "jar -xvf" 명령어로 압축 해제해보면 library의 class 파일이 함께 있는 것 확인 가능
    *     - war와 달리 다른 디렉토리 구조 없이, 바로 각 패키지들이 있음
    *     - 당연히 embedded Tomcat library class 파일 역시 포함됨
    *   - 장점(war와 비교했을 때) → 하나의 jar 파일만으로 배포, WAS 설치, 실행까지 한 번에 진행 가능
    *     - WAS 별도 설치 필요 없음, 내부 library로 포함
    *     - 복잡한 IDE 개발 환경 설정(plugin 사용 등) 없이 단순 Java 프로그램처럼 main()만 실행하도록 하면 됨
    *     - 배포 과정 단순 → jar만 전송하고 실행하면 됨
    *     - Tomcat 버전 변경 필요 시, Gradle에서 버전 변경 후 다시 빌드하면 됨
    *   - 단점
    *     - 모두 class 파일로 압축 해제되어 있으므로 어떤 라이브러리가 사용되고 있는지 추적하기 어려움
    *     - 파일명 중복 해결 불가능
    *       - class 혹은 resource 이름이 같은 경우, 하나를 포기해야 함
    *       - ex. jakarta.servlet.ServletContainerInitializer 파일이 여러 library에 있는 경우 충돌 발생, 하나의 파일만 선택됨, 정상적으로 동작하지 않음
    *       - 이 때문에 duplicatesStrategy를 설정하지만, 근본적인 해결책을 찾으려면 어려움
    *   - 위 단점들을 별도의 boot class를 작성하여 해결할 수 있음
    * - task 실행
    *   - 내 경우에는 ./gradlew clean :embedded:buildFatJar로 실행함
    * */
    // FatJar 생성
    register<Jar>("buildFatJar") {
        manifest {
            attributes("Main-Class" to "hello.embedded.EmbeddedTomcatSpringMain")
        }
        duplicatesStrategy = DuplicatesStrategy.WARN // 파일명 중복 시 경고 발생하게 해둔 것
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
        with(jar.get())
    }
}
