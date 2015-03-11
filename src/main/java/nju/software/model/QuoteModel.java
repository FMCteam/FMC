package nju.software.model;

import nju.software.dataobject.Quote;

public class QuoteModel {

	public Quote quote;
	public String taskId;
	public String processInstanceId;
	
	public QuoteModel(Quote quote,String taskId,String processInstanceId)
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

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
}
