package hello.advanced;

import hello.advanced.trace.logtrace.FieldLogTrace;
import hello.advanced.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// `@Configuration`안에 `@Component`가 있어서, 컴포넌트 스캔 대상이 되고,
@Configuration
public class LogTraceConfig {

    //`@Bean`이 있으면, 스프링컨테이너가 빈으로 등록함
    // 싱글톤으로! => 인스턴스가 딱 하나만 등록됨!
    @Bean
    public LogTrace logTrace() {
        return new FieldLogTrace();
    }
}
