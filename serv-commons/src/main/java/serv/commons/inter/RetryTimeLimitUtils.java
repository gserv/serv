package serv.commons.inter;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RetryTimeLimitUtils {
	
	/**
	 * 单位时间限制
	 */
	private int limit = 10;
	
	/**
	 * 时间长度
	 */
	private int timeLength = 30;
	
	/**
	 * 时间单位
	 */
	private TimeUnit unit = TimeUnit.MINUTES;
	
	private Map<String, Integer> cacheTime = new LinkedHashMap<String, Integer>();
	
	private Map<String, Date> cacheLastReq = new LinkedHashMap<String, Date>();
	
	
	
	
	
	public RetryTimeLimitUtils(int limit, int timeLength, TimeUnit unit) {
		super();
		this.limit = limit;
		this.timeLength = timeLength;
		this.unit = unit;
	}


	public static void main(String[] arges) throws InterruptedException {
		RetryTimeLimitUtils test = new RetryTimeLimitUtils(10, 10, TimeUnit.SECONDS);
		System.out.println("aaa >> " + test.check("aaa"));
		for (int i=0; i<10; i++) {
			test.logRequest("aaa");
		}
		System.out.println("aaa >> " + test.check("aaa"));
		System.out.println("bbb >> " + test.check("bbb"));
		Thread.sleep(1000 * 11);
		System.out.println("aaa >> " + test.check("aaa"));
	}
	
	
	public void logRequest(String key) {
		Date lastReq = cacheLastReq.get(key);
		if (lastReq == null) {
			cacheLastReq.put(key, new Date());
			cacheTime.put(key, 1);
			return;
		}
		System.out.println(unit.convert(new Date().getTime() - lastReq.getTime(), TimeUnit.MILLISECONDS));
		if (unit.convert(new Date().getTime() - lastReq.getTime(), TimeUnit.MILLISECONDS) > timeLength) {
			cacheLastReq.put(key, new Date());
			cacheTime.put(key, 1);
			return;
		} else {
			cacheLastReq.put(key, new Date());
			cacheTime.put(key, cacheTime.get(key) + 1);
			return;
		}
	}
	
	public boolean check(String key) {
		return cacheTime.get(key) == null || 
				unit.convert(new Date().getTime() - cacheLastReq.get(key).getTime(), TimeUnit.MILLISECONDS) > timeLength
				||
				(
					unit.convert(new Date().getTime() - cacheLastReq.get(key).getTime(), TimeUnit.MILLISECONDS) <= timeLength
					&&
					cacheTime.get(key) < limit
				);
	}

}
