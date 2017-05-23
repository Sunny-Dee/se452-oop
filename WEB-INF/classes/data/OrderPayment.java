package data;

import java.io.*;


/* 
	OrderPayment class contains class variables username,ordername,price,image,address,creditcardno.

	OrderPayment  class has a constructor with Arguments username,ordername,price,image,address,creditcardno
	  
	OrderPayment  class contains getters and setters for username,ordername,price,image,address,creditcardno
 */
public class OrderPayment implements Serializable {

    private int orderId;
    private String userName;
    private String userAddress;
    private String creditCardNo;
    private String orderDate;
    private String zipcode;

    public OrderPayment(int orderId, String userName, String userAddress, String creditCardNo, String zipcode) {
        this.orderId = orderId;
        this.userName = userName;
        this.userAddress = userAddress;
        this.creditCardNo = creditCardNo;
        this.zipcode = zipcode;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    
    public String getOrderDate(){
        return orderDate;
    }
    
    public void setOrderDate(String orderDate){
        this.orderDate = orderDate;
    }

    public String getZipcode(){
        return zipcode;
    }
    
    public void setZipcode(String zipcode){
        this.zipcode = zipcode;
    }
}
