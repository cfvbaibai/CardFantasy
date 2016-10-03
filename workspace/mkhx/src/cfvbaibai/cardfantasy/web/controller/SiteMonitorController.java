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
        try {
            PrintWriter writer = response.getWriter();
            writer.println("<!DOCTYPE html>");
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>");
            writer.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
            writer.println("<style>body { font-size: 9px; font-family: \"Helvetica Neue\",Helvetica,\"Hiragino Sans GB\",\"Microsoft YaHei\",Arial,sans-serif; }");
            writer.println("table { border-collapse: collapse; } table td { border: solid 1px #006699; padding: 2px; }</style>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<table width='100%'>");
            writer.println("<tr style='color: #cccccc; background-color: #003366; font-weight: bold'>");
            writer.println("<td>TimeStamp</td><td>User</td><td>Action</td><td>Content</td></tr>");
            
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
