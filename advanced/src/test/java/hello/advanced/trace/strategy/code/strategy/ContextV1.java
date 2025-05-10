package hello.advanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * V1
 * 필드에 전략을 보관하는 방식
 *
 * // Context : 변하지않는 로직을 가지고 있는 템플릿 역할을 하는 코드!(문맥)
 * // => 문맥을 크게 변하지 않지만, 문맥속에서 전략을 변경
 *
 * 전략패턴의 핵심은 Context는 Strategy 인터페이스에만 의존한다
 * => Strategy구현체를 변경해도 Context코드에는 영향을 주지않는다
 *
 * => 스프링에서 의존관계 주입시 사용하는 방식이 바로 전략패턴!!
 */

@Slf4j
public class ContextV1 {

    // 필드에 보관
    private Strategy strategy;

    public ContextV1(Strategy strategy) {
        this.strategy = strategy;
    }

    // 기본적인 큰 문맥에 대한 로직 작성
    public void execute(){
        long startTime = System.currentTimeMillis();
        // 비즈니스 로직 실행
        strategy.call();    // 위임
        log.info("비즈니스 로직1 실행");
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime:{}",resultTime);
    }
}
