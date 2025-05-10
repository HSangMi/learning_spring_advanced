package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.ContextV1;
import hello.advanced.trace.strategy.code.strategy.Strategy;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

    @Test
    void strategyV0() {
        logic1();
        logic2();

    }

    private void logic1(){
        // 핵심로직과 부가로직이 섞여있는 상황
        long startTime = System.currentTimeMillis();
        log.info("비즈니스 로직1 실행");
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime:{}",resultTime);
    }

    private void logic2(){
        // 핵심로직과 부가로직이 섞여있는 상황
        long startTime = System.currentTimeMillis();
        log.info("비즈니스 로직2 실행");
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime:{}",resultTime);
    }

    /**
     * 전략패턴사용
     *
     * : Context안에 원하는 전략을 주입하고(원하는 모양으로 조립)
     * 전략에서 excute호출하여 실행
     *
     * 1. Context(변하지 않는부분)에 원하는 Strategy 구현체 주입
     * 2. 클라이언트는 context를 실행
     * 3. context는 로직 중간에, strategy.call()을 호출하여 주입받은 strategy로직을 실행
     * 4. context는 나머지 로직 실행
     *
     *
     * * 템플릿 메소드의 경우는 상속으로 처리했기 떄문에 부모클래스가 바뀌면(공통로직) 자식도 영향을 받음
     * * 전략패턴은 인터페이스에만 의존하기 때문에, 부모(context)의 로직이 바뀌어도 영향을 받지 않는다
     */
    @Test
    void strategyV1() {
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        ContextV1 contextV1 = new ContextV1(strategyLogic1);
        contextV1.execute();
    }

    /**
     * 익명 내부 클래스 사용해서 구현
     */
    @Test
    void strategyV2() {
        // 익명 내부 클래스 생성/정의
        Strategy strategyLogic1 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        };
        // 컨텍스트에 주입
        ContextV1 context1 = new ContextV1(strategyLogic1);
        log.info("strategyLogic1={}", strategyLogic1.getClass());
        context1.execute();


        Strategy strategyLogic2 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        };
        // 컨텍스트에 주입
        ContextV1 context2 = new ContextV1(strategyLogic2);
        log.info("strategyLogic1={}", strategyLogic2.getClass());
        context2.execute();
    }

    /**
     * 익명 내부클래스 더 편하게 사용하는 법
     *  : 생명한 익명 내부클래스를 바로 context에 주입
     */
    @Test
    void strategyV3() {
        // 익명 내부 클래스 생성/정의, 컨텍스트에 주입
        ContextV1 context1 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });
        context1.execute();


        // 컨텍스트에 주입
        ContextV1 context2 = new ContextV1( new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        });
        context2.execute();
    }

    /**
     * 익명 내부클래스 더 편하게 사용하는 법
     *  : 람다식 사용
     *  => 인터페이스 메서드가 한개만 있을경우 가능!
     */
    @Test
    void strategyV4() {
        // 익명 내부 클래스 생성/정의, 컨텍스트에 주입
        ContextV1 context1 = new ContextV1(()-> log.info("비즈니스 로직1 실행"));
        context1.execute();

        // 컨텍스트에 주입
        ContextV1 context2 = new ContextV1(() -> log.info("비즈니스 로직2 실행"));
        context2.execute();
    }

    /**
     * 선 조립 후 실행! => 한번 조립해두면 이후엔 실행만하면됨
     * => 스프링 애플리케이션을 개발할 때, 로딩시점에 어플리케이션 주입을 끝내놓고 실행하는 것과 같은 원리!
     *
     * 단점 : 한번 조립 후 변경하기가 어럽다,
     * => 전략setter를 제공하게 되면 바꿀 수는 있지만, 싱글톤으로 사용하는경우(스프링 빈) 변경하는 시점에 동시성 이슈가 발생하거나, 고려할 점이 많음
     * => 전략을 실시간으로 변경해야하는 경우, 새 Context를 생성하고 주입하는 것이 더 나은 선택일 수 있다
     */

}
