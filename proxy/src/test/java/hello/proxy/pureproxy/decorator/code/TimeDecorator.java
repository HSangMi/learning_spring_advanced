package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeDecorator implements Component{

    private Component component;

    // 데코레이터가 여러개일 때, 중복되는 코드들이 있을 때, 추상클래스로만들어서 뺼수있음
    // => 이것까지 고려한게 Gof디자인패턴의 데코레이터 패턴
    public TimeDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");
        long startTime = System.currentTimeMillis();

        String result = component.operation();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeDecorator 종료 resultTime={}ms", resultTime);

        return result;
    }
}
