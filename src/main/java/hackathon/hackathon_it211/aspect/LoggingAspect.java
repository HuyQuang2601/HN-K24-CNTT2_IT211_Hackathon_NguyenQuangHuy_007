package hackathon.hackathon_it211.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @AfterReturning(pointcut = "execution(* hackathon.hackathon_it211.service.RoomService.addRoom(..)) || " +
                         "execution(* hackathon.hackathon_it211.service.RoomService.updateRoom(..)) || " +
                         "execution(* hackathon.hackathon_it211.service.RoomService.partialUpdateRoom(..))")
    public void logMethodAfterReturning(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Method executed: " + methodName);
    }
}
