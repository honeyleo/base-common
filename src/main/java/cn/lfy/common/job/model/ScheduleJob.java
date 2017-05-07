package cn.lfy.common.job.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.quartz.Trigger;


public class ScheduleJob implements Serializable {
	
	/**  */
	private static final long serialVersionUID = -981477913876467545L;
	
	/**状态：NONE*/
	public static final String STATUS_NONE = Trigger.TriggerState.NONE.name();
	/**状态：NORMAL*/
	public static final String STATUS_NORMAL = Trigger.TriggerState.NORMAL.name();
	/**状态：PAUSED*/
	public static final String STATUS_PAUSED = Trigger.TriggerState.PAUSED.name();
	public static final String STATUS_COMPLETE = Trigger.TriggerState.COMPLETE.name();
	public static final String STATUS_ERROR = Trigger.TriggerState.ERROR.name();
	public static final String STATUS_BLOCKED = Trigger.TriggerState.BLOCKED.name();
	public static final Integer CONCURRENT_YES = 1;
	public static final Integer CONCURRENT_NO = 0;

    /** 任务id */
    private Long jobId;
 
    /** 任务名称 */
    private String jobName;
 
    /** 任务分组 */
    private String jobGroup;
 
    /** 任务状态 NONE：初始化　NORMAL：正常,　PAUSED：暂停,　COMPLETE：完成,　ERROR：错误,　BLOCKED：堵塞 */
    private String jobStatus;
 
    /** 任务运行时间表达式 */
    private String cronExpression;
 
    /** 任务描述 */
    private String desc;

    private Long operator;
    
    private Long opTime;

    /** 并行，串行标志位 */
    private boolean concurrent;
    
    private Boolean isRunning = Boolean.FALSE;
    
    private String idc;
    
    private boolean isBoot;
	
	public Long getJobId() {
		return jobId;
	}

	
	public void setJobId( Long jobId ) {
		this.jobId = jobId;
	}

	
	public String getJobName() {
		return jobName;
	}

	
	public void setJobName( String jobName ) {
		this.jobName = jobName;
	}

	
	public String getJobGroup() {
		return jobGroup;
	}

	
	public void setJobGroup( String jobGroup ) {
		this.jobGroup = jobGroup;
	}

	
	public String getJobStatus() {
		return jobStatus;
	}

	
	public void setJobStatus( String jobStatus ) {
		this.jobStatus = jobStatus;
	}

	
	public String getCronExpression() {
		return cronExpression;
	}

	
	public void setCronExpression( String cronExpression ) {
		this.cronExpression = cronExpression;
	}

	
	public String getDesc() {
		return desc;
	}

	
	public void setDesc( String desc ) {
		this.desc = desc;
	}


	
	public Long getOperator() {
		return operator;
	}


	
	public void setOperator( Long operator ) {
		this.operator = operator;
	}


	
	public Long getOpTime() {
		return opTime;
	}


	
	public void setOpTime( Long opTime ) {
		this.opTime = opTime;
	}
	
	public String getJobKey() {
		return jobGroup + "_" + jobName;
	}


	public boolean isConcurrent() {
		return concurrent;
	}


	public void setConcurrent(boolean concurrent) {
		this.concurrent = concurrent;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString( this,
				ToStringStyle.MULTI_LINE_STYLE );
	}


	
	public Boolean isRunning() {
		return isRunning;
	}


	
	public void setIsRunning( Boolean isRunning ) {
		this.isRunning = isRunning;
	}


	public String getIdc() {
		return idc;
	}


	public void setIdc(String idc) {
		this.idc = idc;
	}


	public boolean getIsBoot() {
		return isBoot;
	}


	public void setBoot(boolean isBoot) {
		this.isBoot = isBoot;
	}
	
}

