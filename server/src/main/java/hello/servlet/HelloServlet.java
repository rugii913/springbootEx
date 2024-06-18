package hello.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HelloServlet extends HttpServlet { // 프로그래밍 방식으로 등록하는 servlet
    /*
    * - 프로그래밍 방식으로 servlet을 등록하는 경우
    *   - 상황에 따라 mapping 경로를 바꾸어 외부 설정을 읽어내서 등록
    *   - 조건에 따라 if 문으로 분기하여 서블릿을 등록하거나 뺄 수 있음
    *   - 직접 서블릿을 생성하므로, 생성 시 필요한 정보를 같이 넘겨줄 수 있음
    * - vs. @WebServlet annotation을 통한 서블릿 등록(ex. 이 프로젝트의 TestServlet)
     *   - 편리함, 하지만 엔드포인트가 하드코딩되어 있으므로 유연함이 떨어짐
    * */

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("HelloServlet.service");
        resp.getWriter().println("hello servlet!");
    }
}
