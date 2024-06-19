package hello;

import hello.boot.MySpringApplication;
import hello.boot.MySpringBootApplication;

@MySpringBootApplication
// - @ComponentScan에 의해 hello 패키지를 base package로 하여 component들을 scan 함
// - 또한 @ComponentScan이 있으므로, 이 MySpringBootMain을 configClass로 이용할 수 있음
public class MySpringBootMain {

    public static void main(String[] args) {
        System.out.println("MySpringBootMain.main");
        MySpringApplication.run(MySpringBootMain.class, args);
    }
}
