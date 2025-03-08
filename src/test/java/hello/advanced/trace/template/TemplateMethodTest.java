package hello.advanced.trace.template;

import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    /**
     * 템플릿 메소드를 왜 써야하는지 하는 기반코드
     */
    @Test
    void templateMethodV0() {

        logic1();
        logic2();

        // 템플릿 메소드 패턴을 사용하면, 변하는 부분(핵심 비즈니스 로직)과 변하지 않는 부분(부가 로직)을 분리해서 모듈화 할 수 있음!!
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
     * 템플릿 메서드 패턴을 적용
     */
    @Test
    void templateMethodV1() {
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();

        AbstractTemplate template2 = new SubClassLogic2();
        template2.execute();
    }

    /**
     * 템플릿 메서드 응용 1 : 익명 내부클래스를 활용해, 클래스 생성없이 상속해서 사용
     * * 참고 : 익명 내부클래스 이름은 호출한클래스이름$1,2,3,, 이런식으로 자동으로 이름을 만들어줌
     * ex) TemplateMethodTest$1, TemplateMethodTest$2
     * (부모 추상클래스이름이 아님!)
     */
    @Test
    void templateMethodV2() {
        AbstractTemplate template1 = new AbstractTemplate(){
            // new [Class](){} : 객체를 생성하면서 동시에 구현체를 만들 수 있음
            @Override
            protected void call(){
                log.info("비즈니스 로직1 실행");
            }
        };
        log.info("클래스 이름1={}", template1.getClass());
        template1.execute();

        AbstractTemplate template2 = new AbstractTemplate() {
            @Override
            protected void call(){
                log.info("비즈니스 로직2 실행");
            }
        };
        log.info("클래스 이름2={}", template2.getClass().getName());
        template2.execute();
    }

}
