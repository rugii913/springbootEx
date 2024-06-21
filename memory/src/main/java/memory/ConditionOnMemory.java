package memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Slf4j
public class ConditionOnMemory implements Condition { // org.springframework.context.annotation 패키지의 Condition임

    private static final String MEMORY_ON = "on";

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        
        // -Dmemory=[..] 옵션으로 들어온 값을 가져옴 → JVM 시스템 property 중 memory property를 가져오는 것
        String memoryProperty = context.getEnvironment().getProperty("memory");
        log.info("memory={}", memoryProperty);
        return MEMORY_ON.equals(memoryProperty);
    }
}
