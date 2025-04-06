package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceCallback;
import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + @ResponseBody
//@RequiredArgsConstructor
public class OrderControllerV5 {

    private final OrderServiceV5 orderService;
    private final TraceTemplate traceTemplate;
//    private final LogTrace trace; // 의존관계주입을 받음

    @Autowired
    public OrderControllerV5(OrderServiceV5 orderService, LogTrace trace){
        this.orderService = orderService;
        this.traceTemplate = new TraceTemplate(trace);
    }

    /**
     * 템플릿 콜백 패턴 (전략패턴의 하나)
     */
    @GetMapping("/v5/request")
    public String request(String itemId){
        return traceTemplate.execute("OrderControllerV5.request()", new TraceCallback<>() {
            @Override
            public String call() {
                orderService.orderItem(itemId);
                return "ok";
        }});
    }
}
