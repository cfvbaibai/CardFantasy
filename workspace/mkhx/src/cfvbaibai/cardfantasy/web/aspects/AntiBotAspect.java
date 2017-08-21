package cfvbaibai.cardfantasy.web.aspects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import cfvbaibai.cardfantasy.web.beans.Logger;

@Aspect
public class AntiBotAspect {

    @Autowired
    private Logger logger;
    
    @Pointcut("execution(public * cfvbaibai.cardfantasy.web.controller.AutoBattleController.play*(" +
            "javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, ..))")
    public void outputWebResponse() {}
    
    @Around("cfvbaibai.cardfantasy.web.aspects.AntiBotAspect.outputWebResponse()")
    public void setEncoding(ProceedingJoinPoint pjp) throws Throwable {
        try {
            logger.info("AntiBot advice activated on: " + pjp.getSignature().toShortString());
            Object[] args = pjp.getArgs();
            HttpServletRequest request = (HttpServletRequest)args[0];
            if (request.getHeader("AntiBot") == null) {
                logger.info("Botting request from " + request.getRemoteAddr() + " found. Banned!");
                HttpServletResponse response = (HttpServletResponse)args[1];
                response.setStatus(403);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pjp.proceed();
        }
    }
}
