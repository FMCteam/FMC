package nju.software.dataobject;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 专员订单的来源方式，分为专员下单，客户指定专员下单，市场秘书分配的订单，专员认领的订单，市场主管更换专员后被分配的订单五种
 * @author margine
 *
 */
@Entity
@Table(name = "order_source", catalog = "fmc")
public class OrderSource implements Serializable{
	
	/**来自专员自己下的订单*/
	public static final String SOURCE_SELF = "自己下单";
	/**来自客户指定专员的订单*/
	public static final String SOURCE_CUSTOMER = "客户指定";
	/**来自市场秘书分配的订单*/
	public static final String SOURCE_ALLOCATE ="市场秘书分配";
	/**专员认领的客户订单*/
	public static final String SOURCE_CALIM = "认领订单";
	/**来自市场主管同意变更申请后，指定专员的订单*/
	public static final String SOURCE_ALTER ="主管指定";
	
	private Integer sourceId;
	private Integer orderId;
	private String source;
	
	public OrderSource(){
		
	}
	
	public OrderSource(Integer sourceId, Integer orderId, String source){
		this.sourceId = sourceId;
		this.orderId = orderId;
		this.source = source;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="source_id", unique = true, nullable = false)
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	
	@Column(name="order_id", nullable = false)
	public Integer getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	@Column(name="source", nullable = true)
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

}
