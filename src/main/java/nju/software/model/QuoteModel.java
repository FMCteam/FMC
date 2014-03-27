package nju.software.model;

import nju.software.dataobject.Quote;

public class QuoteModel {

	public Quote quote;
	public long taskId;
	public long processInstanceId;
	
	public QuoteModel(Quote quote,long taskId,Long processInstanceId)
	{
		this.quote=quote;
		this.taskId=taskId;
		this.processInstanceId=processInstanceId;
	}

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
}
