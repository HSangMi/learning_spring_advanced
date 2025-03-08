package hello.advanced.trace.template;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;

public abstract class AbstractTemplate<T> {

    private final LogTrace trace;

    public AbstractTemplate(LogTrace trace) {
        this.trace = trace;
    }

    // 반환타입을 제네릭으로!
    // 제네릭 : 타입이 정해지는 시점을 뒤로 미룸!(객체를 생성할때나..)
    public T execute(String message){

        TraceStatus status = null;
        try{
            status = trace.begin(message);

            //로직 호출
            T result = call();

            trace.end(status);
            return result;
        }catch (Exception e){
            trace.exception(status, e);
            throw e;
        }
    }
    protected abstract T call();
}
