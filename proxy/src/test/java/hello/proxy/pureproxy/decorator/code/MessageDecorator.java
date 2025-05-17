package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component {

    // 데코레이터도 프록시를 사용한 패턴이므로, 실제 객체를 알고있어야함
    private Component component;

    public MessageDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("MesageDecorator 실행");

        // data -> *****data*****
        String result = component.operation();
        String decoResult ="*****" + result + "*****";
        log.info("MessageDecorator 꾸미기 적용 전={}, 적용 후={}", result, decoResult);
        return decoResult;
     }
}
