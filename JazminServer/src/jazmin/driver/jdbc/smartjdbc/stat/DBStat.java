package jazmin.driver.jdbc.smartjdbc.stat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * @author skydu
 *
 */
public class DBStat {
	//
	public static boolean isOpen=true;
	public static int maxSqlNum=500;
	//
	public static class SqlStat{
		public long invokeCount;
		public long totalInvokeTime;
		public long avgInvokeTime;
		public long maxInvokeTime;
		public long minInvokeTime;
		public long lastInvokeTime;
		public long exceptionCount;
	}
	//
	private static AtomicLong maxInvokeTime = new AtomicLong(0);
	private static AtomicLong minInvokeTime = new AtomicLong(Integer.MAX_VALUE);
	private static AtomicLong lastInvokeTime = new AtomicLong();
	private static AtomicLong totalInvokeTime = new AtomicLong();
	private static AtomicLong invokeCount = new AtomicLong();
	private static AtomicLong exceptionCount = new AtomicLong();
	private static Map<String,SqlStat> sqlMap=new ConcurrentHashMap<>();
	
	/**
	 * 
	 * @param sql
	 * @param time
	 * @param exception
	 */
	public static void stat(String sql,long time,boolean exception) {
		if(!isOpen){
			return;
		}
		lastInvokeTime.set(time);
		totalInvokeTime.addAndGet(time);
		//reset counter.
		if(totalInvokeTime.longValue()<0){
			totalInvokeTime.set(0);
		}
		if(invokeCount.longValue()<0){
			invokeCount.set(0L);
		}
		invokeCount.incrementAndGet();
		if (minInvokeTime.longValue() > time) {
			minInvokeTime.set(time);
		}
		if (maxInvokeTime.longValue() < time) {
			maxInvokeTime.set(time);
		}
		if (exception) {
			if(exceptionCount.longValue()<0){
				exceptionCount.set(0L);
			}
			exceptionCount.incrementAndGet();
		}
		//
		SqlStat sqlStat=sqlMap.get(sql);
		if(sqlStat==null){
			sqlStat=new SqlStat();
			sqlStat.lastInvokeTime=time;
			sqlStat.minInvokeTime=time;
			sqlStat.maxInvokeTime=time;
			sqlStat.avgInvokeTime=time;
			sqlStat.invokeCount=1;;
			sqlStat.totalInvokeTime=time;
			if(sqlMap.size()<maxSqlNum) {
				sqlMap.put(sql, sqlStat);
			}
		}else{
			sqlStat.invokeCount++;
			sqlStat.lastInvokeTime=time;
			sqlStat.totalInvokeTime+=time;
			if (sqlStat.minInvokeTime> time) {
				sqlStat.minInvokeTime=time;
			}
			if (sqlStat.maxInvokeTime< time) {
				sqlStat.maxInvokeTime=time;
			}
			if (exception) {
				sqlStat.exceptionCount++;
			}
		}	
	}
	//
	/**
	 * @return the maxInvokeTime
	 */
	public static AtomicLong getMaxInvokeTime() {
		return maxInvokeTime;
	}
	/**
	 * @return the minInvokeTime
	 */
	public static AtomicLong getMinInvokeTime() {
		return minInvokeTime;
	}
	/**
	 * @return the lastInvokeTime
	 */
	public static AtomicLong getLastInvokeTime() {
		return lastInvokeTime;
	}
	/**
	 * @return the totalInvokeTime
	 */
	public static AtomicLong getTotalInvokeTime() {
		return totalInvokeTime;
	}
	/**
	 * @return the invokeCount
	 */
	public static AtomicLong getInvokeCount() {
		return invokeCount;
	}
	/**
	 * @return the exceptionCount
	 */
	public static AtomicLong getExceptionCount() {
		return exceptionCount;
	}
	/**
	 * @return the sqlMap
	 */
	public static Map<String, SqlStat> getSqlMap() {
		return sqlMap;
	}
	//
	//
	public static String dump() {
		StringBuilder stat=new StringBuilder();
		String format="%-20s: %-20s\n";
		stat.append(String.format(format, "InvokeCount",DBStat.invokeCount));
		stat.append(String.format(format, "ExceptionCount",DBStat.exceptionCount));
		stat.append(String.format(format, "LastInvokeTime(ms)",DBStat.lastInvokeTime));
		stat.append(String.format(format, "MinInvokeTime(ms)",DBStat.minInvokeTime));
		stat.append(String.format(format, "MaxInvokeTime(ms)",DBStat.maxInvokeTime));
		if(DBStat.invokeCount.get()>0){
			double avgInvokeTime=(double)(DBStat.totalInvokeTime.get()/DBStat.invokeCount.get());
			stat.append(String.format(format, "AvgInvokeTime(ms)",avgInvokeTime));
		}
		//
		format="%-5s %-8s %-8s %-15s %-15s " +
				"%-15s %-15s %-100s\n";
		stat.append(String.format(format,
				"#","COUNT","AVG","LAST TIME",
				"MAX TIME","MIN_TIME",
				"EXCEPTION","SQL"));
		//
		int i=0;
		for(Map.Entry<String,DBStat.SqlStat> entry:DBStat.sqlMap.entrySet()){
			SqlStat v=entry.getValue();
			v.avgInvokeTime=v.totalInvokeTime/v.invokeCount;
			String sql=entry.getKey();
			if(sql.length()>100){
				sql=sql.substring(0, 100);
			}
			stat.append(String.format(format,
					++i,
					v.invokeCount,
					v.avgInvokeTime,
					v.lastInvokeTime,
					v.maxInvokeTime,
					v.minInvokeTime,
					v.exceptionCount,
					sql
					));
		}
		//
		return stat.toString();
	}
}
