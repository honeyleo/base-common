package cn.lfy.common.job;

import java.util.List;

import org.quartz.SchedulerException;

import cn.lfy.common.job.model.ScheduleJob;

public interface QuartzJobController {

	void loadAndStartTaskFromDB() throws SchedulerException;
	
	void scheduleAllJobs( List<ScheduleJob> jobList );
	
	void scheduleSingleJob( ScheduleJob scheduleJob );
	
	List<ScheduleJob> getScheduledJobs() throws SchedulerException;
	
	List<ScheduleJob> getExecutingJobs() throws SchedulerException;
	
	void pauseJob( ScheduleJob scheduleJob ) throws SchedulerException;
	
	void resumeJob( ScheduleJob scheduleJob ) throws SchedulerException;
	
	void deleteJob( ScheduleJob scheduleJob ) throws SchedulerException;
	
	void triggerJob( ScheduleJob scheduleJob ) throws SchedulerException;
	
	void rescheduleJob( ScheduleJob scheduleJob ) throws SchedulerException;
	
	Boolean isJobRunning( ScheduleJob scheduleJob ) throws SchedulerException;
}
