package hello.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HelloServlet extends HttpServlet {
    // 프로그래밍 방식으로 등록할 서블릿
    // 프로그래밍 방식으로 서블릿을 등록하는 이유(vs. @WebServlet 어노테이션을 통한 서블릿 등록)
    // - 상황에 따라 mapping 경로를 바꾸어 외부 설정을 읽어내서 등록
    // - 조건에 따라 if 문으로 분기하여 서블릿을 등록하거나 뺄 수 있음
    // - 직접 서블릿을 생성하므로, 생성 시 필요한 정보를 같이 넘겨줄 수 있음

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("HelloServlet.service");
        resp.getWriter().println("hello servlet!");
    }
}
