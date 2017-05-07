package cn.lfy.common.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.lfy.common.job.model.ScheduleJob;

/**
 * 本类用于运行指定的JOB，并在通过GameCenterTaskInterface在execute的前后增加before(), after()方法
 * <br/>
 * StatefulJob接口已经不推荐使用了，换成了注解的方式，只需要给你实现的Job类加上注解@DisallowConcurrentExecution即可实现有状态
 * @author liao.leo
 * @date 2014-12-8 下午4:38:55
 */
@DisallowConcurrentExecution
public class QuartzJobFactory extends QuartzJobBean {

	/** 日志对象 */
	public static final Logger LOG = LoggerFactory.getLogger( QuartzJobFactory.class );

	private ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		ScheduleJob scheduleJob = ( ScheduleJob )context.getMergedJobDataMap().get( "scheduleJob" );
		LOG.info( "Quartz触发任务：JobName[{}]", scheduleJob.getJobName() );
		TaskInterface gameCenterTask = ( TaskInterface )applicationContext.getBean( scheduleJob.getJobName() );
		
		try {
			gameCenterTask.before( context);
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
		if( !gameCenterTask.isRunning() ) {
			throw new JobExecutionException( scheduleJob.getJobName() + " 未设置 isRunning" );
		}

		try {
			LOG.info( "任务[{}]开始执行", scheduleJob.getJobName() );
			gameCenterTask.execute( context );
			LOG.info( "任务[{}]执行完毕", scheduleJob.getJobName() );
		} catch( Throwable e ) {
			LOG.error( "任务执行异常：任务名称[{}]，异常 {}", scheduleJob.getJobName(), e.getStackTrace() );
		} finally {
			try {
				gameCenterTask.after( context );
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}
