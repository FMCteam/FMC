package nju.software.service;

public interface QuoteService {

	public boolean updateQuote(float innerPrice,float outPrice,int id,long taskId,long processId,String actor);
}
