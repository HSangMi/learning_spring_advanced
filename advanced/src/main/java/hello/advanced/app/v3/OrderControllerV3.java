package hello.advanced.app.v3;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
public class OrderControllerV3 {

    private final OrderServiceV3 orderService;
    private final LogTrace trace; // 의존관계주입을 받음

    @GetMapping("/v3/request")
    public String request(String itemId){
        TraceStatus status = null;
        try{
            status = trace.begin("OrderControllerV3.request()");
            orderService.orderItem(itemId);
            trace.end(status);
            return "ok";
        }catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
