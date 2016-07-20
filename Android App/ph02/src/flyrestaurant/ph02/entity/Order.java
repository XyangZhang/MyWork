package flyrestaurant.ph02.entity;

import java.sql.Date;
import java.util.List;

public class Order {
	public int id;
	public String code;
	public int typeId;
	public int waiterId;
	public Date orderTime;
	public int customers;
	public int status;
	public String description;
	
	public List<OrderDetail> orderDetails;
}
