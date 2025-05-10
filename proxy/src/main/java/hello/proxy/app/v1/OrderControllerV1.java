package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 스프링 MVC는 `@Controller`or`@RequestMapping`이 타입(Class, interface..)에 있어야, 스프링 컨트롤러로 인식
 => 스프링 컨트롤러로 인식되어야, HTTP URL이 매핑됨

 `@RequesetBody`: HTTP 메세지 컨버터를 사용해서 응답하게 함. 마찬가지로 인터페이스에도 사용가능

 `/request` : 로그트레이서 적용대상
 `/no-log` : 로그트레이서 적용x

 인터페이스일 경우, `@RequesetParam` 어노테이션을 명시해주는게 실행시점에 인식 못하는 문제가 발생하지 않음
 */
@RequestMapping // 스프링은 @Controller 또는 @RequestMapping이 있어야 스프링 컨트롤러로 인식할 수 있음
@ResponseBody
public interface OrderControllerV1 {

    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/v1/no-log")
    String noLog();
}
