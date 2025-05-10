package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
//final이 붙은 애들을 매개변수로 갖는 생성자를 자동으로 만들어줘서 의존관계주입 하게해줌
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepository;
    private final TraceTemplate traceTemplate;
//    private final LogTrace trace; // 의존관계주입을 받음


    public OrderServiceV5(OrderRepositoryV5 orderRepository, LogTrace logTrace) {
        this.orderRepository = orderRepository;
        this.traceTemplate = new TraceTemplate(logTrace);
    }

    public void orderItem(String itemId) {
        traceTemplate.execute("OrderServiceV5.orderItem()", () -> {
            orderRepository.save(itemId);
            return null;
        });
    }
}
