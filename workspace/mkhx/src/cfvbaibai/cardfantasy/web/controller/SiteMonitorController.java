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
            writer.println("<style>td { font-size: 7pt; font-family: 微软雅黑, Calibri, Consolas, Tahoma, Arial; }</style>");
            writer.println("<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>");
            writer.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<table width='100%' border='0' cellspacing='1' bgcolor='#AAAAFF'>");
            writer.println("<tr bgcolor='blue' style='color: white; font-weight: bold; text-align: center'>");
            writer.println("<td>TimeStamp</td><td>User</td><td>Action</td><td>Content</td></tr>");
            
            for (UserAction action : userActionRecorder.getAllActions()) {
                String line = String.format("<tr bgcolor='white'><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
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
