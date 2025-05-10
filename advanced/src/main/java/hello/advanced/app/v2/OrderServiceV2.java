package hello.advanced.app.v2;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//final이 붙은 애들을 매개변수로 갖는 생성자를 자동으로 만들어줘서 의존관계주입 하게해줌
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;
    private final HelloTraceV2 trace; // 의존관계주입을 받음

    public void orderItem(TraceId traceId, String itemId) {
        TraceStatus status = null;
        try{
            status = trace.beginSync(traceId,"OrderServiceV1.orderItem()");
            orderRepository.save(status.getTraceId(), itemId);
            trace.end(status);
        }catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }

    }
}
