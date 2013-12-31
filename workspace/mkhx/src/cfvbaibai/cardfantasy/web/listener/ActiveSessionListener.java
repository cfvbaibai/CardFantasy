package cfvbaibai.cardfantasy.web.listener;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cfvbaibai.cardfantasy.web.beans.Logger;

public class ActiveSessionListener implements HttpSessionListener, ServletContextListener {

    @Autowired
    @Qualifier("active-session-count")
    private AtomicInteger activeSessionCount;
    
    @Autowired
    private Logger logger;
    
    public ActiveSessionListener() {
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        int activeSessionCountValue = activeSessionCount.incrementAndGet();
        if (activeSessionCountValue < 1) {
            activeSessionCount.set(1);
            activeSessionCountValue = 1;
        }
        logger.info("Session created! Active session count: " + activeSessionCountValue);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        int activeSessionCountValue = activeSessionCount.decrementAndGet();
        if (activeSessionCountValue < 1) {
            activeSessionCount.set(1);
            activeSessionCountValue = 1;
        }
        logger.info("Session destroyed! Active session count: " + activeSessionCountValue);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Context destroyed!");
/*
        FileWriter writer = null;
        try {
            writer = new FileWriter(event.getServletContext().getContextPath() + "/activeSessionCount.txt", true);
            writer.append(String.valueOf(this.activeSessionCount.intValue()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) { try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            } }
        }
*/
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("Context initialized");
        WebApplicationContextUtils
            .getRequiredWebApplicationContext(event.getServletContext())
            .getAutowireCapableBeanFactory()
            .autowireBean(this);
        
        System.out.println("runtime.env.suffix = " + System.getProperty("runtime.env.suffix"));
/*
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(event.getServletContext().getContextPath() + "/activeSessionCount.txt"));
            String activeSessionCountText = reader.readLine();
            if (activeSessionCountText != null) {
                int activeSessionCountValue = Integer.parseInt(activeSessionCountText);
                if (activeSessionCountValue >= 1) {
                    this.activeSessionCount.set(activeSessionCountValue);
                }
            }
        } catch (NumberFormatException e) {
            // Do nothing...
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { if (reader != null) { reader.close(); } }
            catch (IOException e) { e.printStackTrace(); }
        }
*/
    }
}
