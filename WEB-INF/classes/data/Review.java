/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author delianaescobari
 */
public class Review {
    String productName;
    String username;
    String productType;
    int rating;
    String reviewDate;
    String reviewText;
    
    public Review(String productName, String username, String productType, 
            int rating, String reviewDate, String reviewText){
        this.productName = productName;
        this.username = username;
        this.productType = productType;
        this.rating = rating;
        this.reviewDate = reviewDate;
        this.reviewText = reviewText;
    }
    
    public void setProductName(String productName){
        this.productName = productName;
    }
    
    public String getProductName(){
        return productName;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getUsername(){
        return username;
    }
    
    public void setProductType(String productType){
        this.productType = productType;
    }
    
    public String getProductType(){
        return productType;
    }
    
    public void setRating(int rating){
        this.rating = rating;
    }
    
    public int getRating(){
        return rating;
    }
    
    public void setReviewDate(String reviewDate){
        this.reviewDate = reviewDate;
    }
    
    public String getReviewDate(){
        return reviewDate;
    }
    
    public void setReviewText(String reviewText){
        this.reviewText = reviewText;
    }
    
    public String getReviewText(){
        return reviewText;
    }
}
