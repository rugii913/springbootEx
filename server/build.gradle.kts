// 강의와는 다르게 Kotlin DSL을 사용해봄
plugins {
    id("java")
    id("war") // 빌드 시 war 파일로 만들어주는 plugin
    /*
    * 생성된 war 파일은 jar -xvf 명령어로 압축 해제 가능
    * - WAR(Web application ARchive) WAS에 배포할 때 사용하는 파일
    *   - WAS 위에서 실행
    *   - HTML 같은 정적 리소스와 클래스 파일을 모두 포함하여 JAR보다 구조가 복잡 → 특정한 구조를 지켜야 함
    * - war 파일을 압축 해제하면 볼 수 있는 구조
    *   - 루트에 src/main/webapp 디렉토리에 넣어둔 정적 리소스 파일들
    *   - META-INF 디렉토리
    *   - WEB-INF 디렉토리 → class 파일, 라이브러리, 설정 정보 위치
    *     - classes 디렉토리(컴파일된 코드 - class 파일)
    *     - lib 디렉토리(라이브러리 jar 파일들)
    *     - web.xml (생략 가능) 웹 서버 배치 설정 파일
    * - ./gradlew build로 생성된 war 파일을 [Tomcat 디렉토리]/webapps 디렉토리에 복사하고(기존 예제 파일은 모두 삭제)
    *   - war 파일의 이름을 ROOT.war로 변경(ROOT는 모두 대문자)
    *   - [Tomcat 디렉토리]/bin의 startup 파일을 실행
    *   - cf. Windows 명령 프롬프트 한글 깨짐 해결 참고 → https://m.blog.naver.com/gyul611/222194321084
    *   - cf. Tomcat 실행 시 알아서 war 압축 해제하여 실행
    *   - cf. 작성한 클래스에 main()이 없어도 잘 실행됨을 알 수 있음 → main()은 Tomcat에서 갖고 있기 때문
    * */
    /*
    * war 파일을 생성하고 Tomcat이 이를 실행하는 과정을 IDE에서 간편하게 해결하기(IntelliJ Ultimate 버전)
    * - 메뉴 → Run → Edit Configurations(구성 편집) 화면 → + 클릭 → Tomcat Server 클릭
    * - Server 탭 → Application server 옆 Configure 클릭 → 설치해둔 [Tomcat 디렉토리]로 설정
    * - Deployment 탭 → + 클릭 → Artifact 클릭 → 배포할 war 파일 선택 후 OK 클릭
    *   - Application context는 모두 지워서 공란으로 둠
    *   - Before launch 부분에는 build 작업과 artifact 빌드 작업이 알아서 잘 들어가 있을 것
    * - 이후에는 저장된 실행 구성 그대로 선택 후 IDE에서 실행하면 됨
    * */
}

/*
group = "hello" // 없어도 동작하는 듯함 → build까지도 별 문제 없음
version = "1.0-SNAPSHOT" // 없어도 동작하는 듯함 → 문제는 없지만 build 시 생성되는 파일에 버전 정보 없음
sourceCompatibility = "17" // 아래 java { } 블럭 참고
*/

repositories {
    mavenCentral()
}

dependencies {
    // Jakarta servlet - https://projects.eclipse.org/projects/ee4j.servlet
    implementation("jakarta.servlet:jakarta.servlet-api:6.1.0")

    // Spring Web MVC → Spring Core 등 포함(cf. Web MVC가 아니라 Web이면 context가 빠져있음)
    implementation("org.springframework:spring-webmvc:6.1.9")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

/*
// war 압축 해제하기 위한 Gradle 태스크, IntelliJ 무료버전인 경우에는 이 방법만 가능
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
 */

tasks.test {
    useJUnitPlatform()
}
