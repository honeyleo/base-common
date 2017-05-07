package cn.lfy.common.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

public interface TaskInterface extends Job {

	/**
	 * run before the execute(...)<br>
	 * throw other non-declared exception will cause a task abortion
	 * @param context
	 * @throws GameTaskException
	 */
	public void before(JobExecutionContext context) throws Exception;

	/**
	 * run after the execute(...)<br>
	 * if execute only throw the declared "GameTaskException", this after() will still be executed;
	 * @param context
	 * @throws GameTaskException
	 */
	public void after(JobExecutionContext context) throws Exception;
	
	public boolean isRunning();
	
	public void setInterrupt( boolean interrupt );
}
