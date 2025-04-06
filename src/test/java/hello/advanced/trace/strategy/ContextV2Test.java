package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.ContextV2;
import hello.advanced.trace.strategy.code.strategy.Strategy;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {

    /**
     * 전략패턴 - 호출 시  매번주입
     *
     *  실행 시점에 원하는 전략을 유연하게 주입할 수 있는 장점이 있음!!
     *  컨텍스트도 하나만 생성해도 되는 장점이 있음
     */
    @Test
    void strategyV1(){
        ContextV2 context = new ContextV2();
        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
    }

    /**
     * 익명 내부 클래스 사용
     */
    @Test
    void strategyV2(){
        ContextV2 context = new ContextV2();
        // 익명 내부클래스는, execute 안에서 실행할 코드 조각을 넘긴다고 생각하면 좋음
        context.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });
        context.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        });
    }

    /**
     * 익명 내부 클래스 사용2 - 람다
     */
    @Test
    void strategyV3(){
        ContextV2 context = new ContextV2();
        // 익명 내부클래스는, execute 안에서 실행할 코드 조각을 넘긴다고 생각하면 좋음
        context.execute(() -> log.info("비즈니스 로직1 실행"));
        context.execute(() -> log.info("비즈니스 로직2 실행"));
    }

}
