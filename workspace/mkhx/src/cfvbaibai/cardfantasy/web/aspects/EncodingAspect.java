package cfvbaibai.cardfantasy.web.aspects;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import cfvbaibai.cardfantasy.web.beans.Logger;

@Aspect
public class EncodingAspect {

    @Autowired
    private Logger logger;
    
    @Pointcut("execution(public * cfvbaibai.cardfantasy.web.controller.AutoBattleController.*(" +
            "*, javax.servlet.http.HttpServletResponse, ..))")
    public void outputWebResponse() {}
    
    @Around("cfvbaibai.cardfantasy.web.aspects.EncodingAspect.outputWebResponse()")
    public void setEncoding(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("setEncoding advice activated on: " + pjp.getSignature().toShortString());
        Object[] args = pjp.getArgs();
        HttpServletResponse response = (HttpServletResponse)args[1];
        response.setCharacterEncoding("UTF-8");
        
        pjp.proceed();
    }
}
