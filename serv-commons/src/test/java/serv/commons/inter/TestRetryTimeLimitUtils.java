package serv.commons.inter;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by shiying on 2016/3/18.
 */
public class TestRetryTimeLimitUtils {

    @Test
    public void test() throws InterruptedException {
        int limit = 10;         // 单位时间最大不能超过10次
        int timeLength = 10;    // 单位时间长度，这里的10表示10秒
        TimeUnit unit = TimeUnit.SECONDS;   // 单位时间单位，表示秒，更多单位参考java.util.concurrent.TimeUnit类
        // 每十秒内，每个客户端（key）调用次数不能超过10次
        RetryTimeLimitUtils test = new RetryTimeLimitUtils(limit, timeLength, unit);
        System.out.println("aaa >> " + test.check("aaa"));
        for (int i=0; i<10; i++) {
            test.logRequest("aaa");
        }
        System.out.println("aaa >> " + test.check("aaa"));
        System.out.println("bbb >> " + test.check("bbb"));
        Thread.sleep(1000 * 11);
        System.out.println("aaa >> " + test.check("aaa"));
    }

}
