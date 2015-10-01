package cfvbaibai.cardfantasy.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cfvbaibai.cardfantasy.web.Cnzz;
import cfvbaibai.cardfantasy.web.beans.Logger;

@Controller
public class UsageController {
    @Autowired
    private Cnzz cnzz;

    @Autowired
    private Logger logger;

    @RequestMapping(value = "/Usage/{key}", method = RequestMethod.PUT)
    public void recordUsage(HttpServletRequest request, HttpServletResponse response, @PathVariable("key") String key) {
        try {
            cnzz.recordUsage(key);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
