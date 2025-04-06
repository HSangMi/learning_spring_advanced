package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryV5 {
    private final TraceTemplate traceTemplate;

    public OrderRepositoryV5(LogTrace logTrace) {
        this.traceTemplate = new TraceTemplate(logTrace);
    }

    public void save(String itemId){

        traceTemplate.execute("OrderRepositoryV4.save()", () -> {
            if(itemId.equals("ex")){
                throw new IllegalStateException("예외 발생");
            }
            sleep(1000);
            return null;
        });
    }

    private void sleep(int millis){
        try{
            Thread.sleep(millis);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
/**
 * 템플릿 메서드패턴은 상속에서 오는 단점을 그대로 안고감
 * 자식 클래스들이 부모클래스에 강하게 결합되어, 의존한다.
 * 부모클래스의 기능을 사용하지 않지만, 부모클래스를 알아한다.
 * 부모클래스가 변경되면 자식클래스들도 영향을 받을 수 있다 => 좋은 설계 x
 * 상속을 사용하기 위해, 별도 클래스나, 익명 내부클래스를 만들어야함
 *
 * => 이부분을 어떻게 개선할 수 있을까?
 * => 템플릿 메서드 패턴과 비슷한 역할을 하면서, 상속의 단점을 제거할 수 있는 전략패턴 사용!
 * 이펙티브 자바에서도 "상속보단 위임" 이란 말이 있음
 */