package cz.muni.fi.pa165;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import javax.inject.Named;

@Aspect
@Named
public class TimingAspect {

    @Around("execution(public * *(..)) && within(cz.muni.fi.pa165.currency.*)")
    public Object logMethodDuration(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;

        System.out.println("Duration of " + joinPoint.getSignature().getName() + ": " + duration);

        return result;
    }
}
