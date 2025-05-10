package hello.advanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * V2
 * 전략을 파라미터로 전달받는 방식
 *
 * => 전략은 execute가 호출될 때 마다 파라미터로 전달받는다
 */

@Slf4j
public class ContextV2 {

    //
    public void execute(Strategy strategy){
        long startTime = System.currentTimeMillis();
        // 비즈니스 로직 실행
        strategy.call();    // 위임
        log.info("비즈니스 로직1 실행");
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime:{}",resultTime);
    }
}
