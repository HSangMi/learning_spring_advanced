package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component * 컴포넌트 스캔 안쓰고, 직접 스프링 빈으로 등록할 것
public class ThreadLocalLogTrace implements LogTrace {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    // TraceId를 어딘가는 들고있어야함!!(파라미터로 넘기지않고)
//    private TraceId traceIdHolder; // traceId 동기화  => 동시성이슈발생
    private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();

    private void syncTraceId(){
        TraceId traceId = traceIdHolder.get();
        if(traceId == null){
            traceIdHolder.set(new TraceId());
        }else{
            traceIdHolder.set(traceId.createNextId());
        }
    }

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceIdHolder.get();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs, e.toString());
        }

        // 끝날때, holder에 들고있는 traceId를 제거함
        releaseTraceId();
    }

    private void releaseTraceId() {
        TraceId traceId = traceIdHolder.get();
        // 첫번째 레벨 == 다 처리하고 나온 상태
        if(traceId.isFistLevel()){
            traceIdHolder.remove(); //destory
            // 사용한 ThreadLocal 해제해줘야 메모리 누수가 발생하지 않음
        }else{
            // 마지막이 아니고, 중간 처리단계이면, 하나씩 전단계 level로 돌아감
            traceIdHolder.set(traceId.createPreviousId());
        }
    }

    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}
