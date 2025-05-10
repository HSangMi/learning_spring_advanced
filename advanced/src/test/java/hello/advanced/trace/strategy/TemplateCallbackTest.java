package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.template.Callback;
import hello.advanced.trace.strategy.code.template.TimeLogTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 콜백 : call한 뒤 인수로 전달받은 코드를 back에서 실행
 *
 * 템플릿 콜백패턴 : Gof디자인 패턴은 아니지만, 스프링에서 자주사용되는 패턴으로
 * `JdbcTemplate`, `RestTemplate`등 xxxTemplate으로 명명된 것들은 이 패턴으로 보면 됨
 * call : context
 * back : Strategy
 */

@Slf4j
public class TemplateCallbackTest {

    /**
     * 템플릿 콜백 패턴 - 익명 내부 클래스
     */

    @Test
    void callbackV1(){
        TimeLogTemplate template = new TimeLogTemplate();
        template.execute(new Callback(){
            @Override
            public void call(){
                log.info("비즈니스로직1 실행");
            }
        });

        template.execute(new Callback(){
            @Override
            public void call(){
                log.info("비즈니스로직2 실행");
            }
        });
    }

    /**
     * 템플릿 콜백 패턴 - 람다표현식
     */
    @Test
    void callbackV2(){
        TimeLogTemplate template = new TimeLogTemplate();
        template.execute(() -> log.info("비즈니스로직1 실행"));
        template.execute(() -> log.info("비즈니스로직2 실행"));
    }
}
