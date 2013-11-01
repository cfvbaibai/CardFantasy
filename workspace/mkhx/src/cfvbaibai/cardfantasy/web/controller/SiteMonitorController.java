package cfvbaibai.cardfantasy.web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cfvbaibai.cardfantasy.web.Utils;
import cfvbaibai.cardfantasy.web.beans.UserAction;
import cfvbaibai.cardfantasy.web.beans.UserActionRecorder;

@Controller
public class SiteMonitorController {

    public SiteMonitorController() {
        // TODO Auto-generated constructor stub
    }

    @Autowired
    private UserActionRecorder userActionRecorder;
    
    @RequestMapping(value = "/monitor")
    public void monitor(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.println("<html>");
            writer.println("<head><style>td { font-size: 7pt; font-family: 微软雅黑, Calibri, Consolas, Tahoma, Arial; }</style></head>");
            writer.println("<body>");
            writer.println("<table width='100%' border='1'><tr><td>TimeStamp</td><td>User</td><td>Action</td><td>Content</td></tr>");
            for (UserAction action : userActionRecorder.getAllActions()) {
                String line = String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                        Utils.toString(action.getTimeStamp()), action.getUserId(), action.getActionType(), action.getActionContent());
                writer.print(line);
            }
            writer.println("</table>");
            writer.println("</body></html>");
        } catch (IOException e) {
            System.err.println("Cannot get writer...");
            e.printStackTrace();
        }
        
    }
}
