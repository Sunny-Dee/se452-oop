
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import data.Review;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author delianaescobari
 */
public class MongoDBDataStoreUtilities {

    
    static DBCollection productReviews;

    public static void getConnection() {
        MongoClient mongo;
        mongo = new MongoClient("localhost", 27017);
        DB db = mongo.getDB("CustomerReviews");
        productReviews = db.getCollection("productReviews");
    }
    
    public boolean insertReview(String productName, String username, 
            String productType, 
            String rating, String reviewDate, String reviewText){
        getConnection();
        BasicDBObject doc = new BasicDBObject(productName, "productReviews")
                .append("username", username)
                .append("product_name", productName)
                .append("product_type", productType)
                .append("rating", rating)
                .append("review_date", reviewDate)
                .append("review_text", reviewText);
        WriteResult wr = productReviews.insert(doc);
        return wr.wasAcknowledged();
    }
    
    public HashMap<String, ArrayList<Review>> selectReview(){
        getConnection();
        HashMap<String, ArrayList<Review>> reviews = 
                new HashMap<String, ArrayList<Review>>();
        
        DBCursor cursor = productReviews.find();
        while (cursor.hasNext()){
            BasicDBObject obj = (BasicDBObject) cursor.next();
            if(!reviews.containsKey(obj.getString("product_name"))){
                ArrayList<Review> arr = new ArrayList<>();
                reviews.put(obj.getString("product_name"), arr);
            }
            
            ArrayList<Review> listReview = reviews.get(obj.getString("product_name"));
            
            int rating;
            try {
                rating = Integer.parseInt(obj.getString("rating"));
            } catch(NumberFormatException e){
                rating = 3;
            }
            
            Review review = new Review(obj.getString("product_name"), 
                    obj.getString("username"), 
                    obj.getString("product_category"),
                    rating, 
                    obj.getString("reviewDate"),
                    obj.getString("review_text")
            );
            
            listReview.add(review);
        }
        
        return reviews;
    }
    
}
