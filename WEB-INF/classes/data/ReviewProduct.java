package data;

/**
 *
 * @author delianaescobari
 */
public class ReviewProduct extends Product {
    
    double totalRatings;
    int totalReviews;
    String productType;
    
    
    public ReviewProduct(){
        super();
        this.totalRatings = 0.0;
        this.totalReviews = 0;
    }
    
    public void setTotalRatings(int totalRatings){
        this.totalRatings = totalRatings;
    }
    
    public void addToTotalRatings(double rating){
        totalRatings += rating;
        totalReviews++;
    }
    
    public double getAverageRating(){
        return totalRatings/(double) totalReviews;
    }
    
    public double getTotalRatings(){
        return totalRatings;
    }
    
    public int getTotalReviews(){
        return totalReviews;
    }
    
    public void setProductType(String productType){
        this.productType = productType;
    }
    
    public String getProductType(){
        return productType;
    }
}
