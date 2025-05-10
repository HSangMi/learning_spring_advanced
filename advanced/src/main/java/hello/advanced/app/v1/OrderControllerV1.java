package hello.advanced.app.v1;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
public class OrderControllerV1 {

    private final OrderServiceV1 orderService;
    private final HelloTraceV1 trace; // 의존관계주입을 받음

    @GetMapping("/v1/request")
    public String request(String itemId){
        TraceStatus status = null;
        try{
            status = trace.begin("OrderControllerV1.request()");
            orderService.orderItem(itemId);
            trace.end(status); // 위에 서비스에서 예외가 터져버리면 end호출이 안됨!! =>try catch로 감싸기
            return "ok";
        }catch (Exception e) {
            trace.exception(status, e);
            //로그가 어플리케이션 흐름을 바꾸면 안되기 때문에, 예외는 그대로 던짐
            throw e;
        }
    }
}
