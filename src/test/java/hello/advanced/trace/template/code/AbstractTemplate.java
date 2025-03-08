package hello.advanced.trace.template.code;

import lombok.extern.slf4j.Slf4j;

/**
 * 템플릿 메서드 패턴은 부모 클래스에 변하지 않는 템플릿 코드를 두고,
 * 자식클래스에 변하는 부분을 두고, 상속과 오버라이딩을 사용해서 처리한다 => 다형성
 */
@Slf4j
public abstract class AbstractTemplate {

    public void execute(){
        // 공통로직(변하지 않는부분)
        long startTime = System.currentTimeMillis();
        // 비즈니스 로직 실행
        call(); // 변하는 부분을 상속으로 풀어서, 자식클래스에서 정의한대로 바뀜
        // 비즈니스 로직 종료

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime:{}",resultTime);

    }

    protected abstract void call();
}
