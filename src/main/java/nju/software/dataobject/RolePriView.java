package nju.software.dataobject;

import java.util.List;
public class RolePriView
{
    private List<RolePriView> item;
    private String id;
    private String pid;
    private String text;
	public List<RolePriView> getItem() {
		return item;
	}
	public void setItem(List<RolePriView> item) {
		this.item = item;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
    
}
