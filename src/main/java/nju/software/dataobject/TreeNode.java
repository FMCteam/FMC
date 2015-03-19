package nju.software.dataobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TreeNode implements Serializable
{
    private static final long serialVersionUID = 8271695167134077473L;

    private String id;
    
    private String pId;
    
    private String name;
    
    private String url;
    
    private String sort;
    
    private String iconcls;
    
    private String isUsed;
    
    private String myid;
    
    private String target = "main";
    private String description;
    
    private List<TreeNode> child = new ArrayList<TreeNode>();
    
    
    
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

	public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getPId()
    {
        return pId;
    }
    
    public void setPId(String id)
    {
        pId = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public String getSort()
    {
        return sort;
    }
    
    public void setSort(String sort)
    {
        this.sort = sort;
    }
    
    public List<TreeNode> getChild()
    {
        return child;
    }
    
    public void setChild(List<TreeNode> child)
    {
        this.child = child;
    }
    
    public String getIconcls()
    {
        return iconcls;
    }
    
    public void setIconcls(String iconcls)
    {
        this.iconcls = iconcls;
    }
    
    public String getTarget()
    {
        return target;
    }
    
    public void setTarget(String target)
    {
        this.target = target;
    }

	public String getMyid() {
		return myid;
	}

	public void setMyid(String myid) {
		this.myid = myid;
	}
    
    

}
