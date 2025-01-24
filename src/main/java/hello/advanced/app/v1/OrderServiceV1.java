package hello.advanced.app.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//final이 붙은 애들을 매개변수로 갖는 생성자를 자동으로 만들어줘서 의존관계주입 하게해줌
public class OrderServiceV1 {

    private final OrderRepositoryV1 orderRepository;

    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
