package hello.advanced.trace.threadlocal;

import hello.advanced.trace.threadlocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class FieldServiceTest {
    private FieldService fieldService = new FieldService();

    @Test
    void field(){
        log.info("main start");
        Runnable userA = ()->{
            fieldService.logic("userA");
        };
        Runnable userB = ()->{
            fieldService.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("threadA"); // 로그에 이름으로 찍힘
        Thread threadB = new Thread(userB);
        threadB.setName("threadB");

        // A가 완전히 끝난 후 B가 실행하는 경우 -> 동시성문제가 발생x
//        threadA.start();
//        sleep(2000);
//        threadB.start();

        //동시성문제가 발생o
        threadA.start();
        sleep(100);
        threadB.start();

        sleep(3000); // 메인스레드 종료 대기
        log.info("main exit");
    }

    private void sleep(int millis) {
        try{
            Thread.sleep(millis);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
