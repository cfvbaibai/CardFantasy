package cfvbaibai.cardfantasy.jettyserver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class GameHttpServlet extends HttpServlet {

    private static final long serialVersionUID = 1091675668736811637L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            String resultJson = getResultJson(new FormData(req));
            resp.getWriter().println(resultJson);
        } catch (IllegalArgumentException e) {
            resp.getWriter().println(e.getMessage());
            resp.setStatus(400);
        }
    }

    protected abstract String getResultJson(FormData formData);
}
