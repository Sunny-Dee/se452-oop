
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
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
            String rating, String reviewDate, String reviewText) {
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

    public void storeReview(String productname, String producttype,
            String reviewrating, String reviewdate, String reviewtext,
            String username) {
        HashMap<String, ArrayList<Review>> reviews
                = new HashMap<String, ArrayList<Review>>();

        try {
            reviews = selectReview();
        } catch (Exception e) {
        }

        if (!reviews.containsKey(productname)) {
            ArrayList<Review> arr = new ArrayList<>();
            reviews.put(productname, arr);
        }
        ArrayList<Review> listReview = reviews.get(productname);

        Review review = new Review(productname, username, producttype, reviewrating, reviewdate, reviewtext);
        listReview.add(review);
        try {
            insertReview(productname, username, producttype, reviewrating, reviewdate, reviewtext);
        } catch (Exception e) {
        }
    }

    public HashMap<String, ArrayList<Review>> selectReview() {
        getConnection();
        HashMap<String, ArrayList<Review>> reviews
                = new HashMap<>();

        DBCursor cursor = productReviews.find();
        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            if (!reviews.containsKey(obj.getString("product_name"))) {
                ArrayList<Review> arr = new ArrayList<>();
                reviews.put(obj.getString("product_name"), arr);
            }

            ArrayList<Review> listReview = reviews.get(obj.getString("product_name"));

            Review review = new Review(obj.getString("product_name"),
                    obj.getString("username"),
                    obj.getString("product_category"),
                    obj.getString("rating"),
                    obj.getString("reviewDate"),
                    obj.getString("review_text")
            );

            listReview.add(review);
        }

        return reviews;
    }

    public boolean submitOtUpdateReview(String productId, String producttype,
            String rating, String reviewdate, String reviewtext,
            String username) {

        getConnection();
        //call below function and if arraylist size == 0 then just insert 
        //by calling function above. If > 0 then update. 
        BasicDBObject query = new BasicDBObject();
        query.append("product_name", productId);
        query.append("username", username);
        
        DBCursor cursor = productReviews.find(query);

        if (cursor.hasNext()) {
          
            BasicDBObject newDocument = new BasicDBObject();
            newDocument.put("username", username);
            newDocument.put("product_name", productId);
            newDocument.put("product_type", producttype);
            newDocument.put("rating", rating);
            newDocument.put("review_date", reviewdate);
            newDocument.put("review_text", reviewtext);
            
            WriteResult wr = productReviews.update(query, newDocument);
            
            return wr.isUpdateOfExisting();
        } else {
            return insertReview(productId, username,
            producttype,
            rating, reviewdate, reviewtext);
        }
    }

    public ArrayList<Review> getReviewsByProduct(String productId) {
        getConnection();
        
        BasicDBObject query = new BasicDBObject();
        query.append("product_name", productId);
        
        DBCursor cursor = productReviews.find(query);
        
        ArrayList<Review> reviews = new ArrayList<>();
        while (cursor.hasNext()){
            
            BasicDBObject obj = (BasicDBObject) cursor.next();            
            Review review = new Review(obj.getString("product_name"), 
                    obj.getString("username"), obj.getString("product_type"), 
                    obj.getString("rating"), obj.getString("review_date"),
                    obj.getString("review_text"));
            
            reviews.add(review);
        }
        
        return reviews;
    }

}
