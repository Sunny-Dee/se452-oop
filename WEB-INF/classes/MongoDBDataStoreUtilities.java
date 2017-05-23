
import data.Product;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import data.Review;
import data.ReviewProduct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
            String productType, String rating, String reviewDate,
            String reviewText, String zipcode) {
        getConnection();
        BasicDBObject doc = new BasicDBObject(productName, "productReviews")
                .append("username", username)
                .append("product_name", productName)
                .append("product_type", productType)
                .append("rating", rating)
                .append("review_date", reviewDate)
                .append("zipcode", zipcode)
                .append("review_text", reviewText);
        WriteResult wr = productReviews.insert(doc);
        return wr.wasAcknowledged();
    }

    public void storeReview(String productname, String producttype,
            String reviewrating, String reviewdate, String reviewtext,
            String username, String zipcode) {
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

        Review review = new Review(productname, username, producttype,
                reviewrating, reviewdate, reviewtext, zipcode);
        listReview.add(review);
        try {
            insertReview(productname, username, producttype,
                    reviewrating, reviewdate, reviewtext, zipcode);
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
                    obj.getString("product_type"),
                    obj.getString("rating"),
                    obj.getString("reviewDate"),
                    obj.getString("review_text"),
                    obj.getString("zipcode")
            );

            listReview.add(review);
        }

        return reviews;
    }

    public boolean submitOtUpdateReview(String productId, String producttype,
            String rating, String reviewdate, String reviewtext,
            String username, String zipcode) {

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
            newDocument.put("zipcode", zipcode);

            WriteResult wr = productReviews.update(query, newDocument);

            return wr.isUpdateOfExisting();
        } else {
            return insertReview(productId, username,
                    producttype,
                    rating, reviewdate, reviewtext, zipcode);
        }
    }

    public ArrayList<Review> getReviewsByProduct(String productId) {
        getConnection();

        BasicDBObject query = new BasicDBObject();
        query.append("product_name", productId);

        DBCursor cursor = productReviews.find(query);

        ArrayList<Review> reviews = new ArrayList<>();
        while (cursor.hasNext()) {

            BasicDBObject obj = (BasicDBObject) cursor.next();
            Review review = new Review(obj.getString("product_name"),
                    obj.getString("username"), obj.getString("product_type"),
                    obj.getString("rating"), obj.getString("review_date"),
                    obj.getString("review_text"),
                    obj.getString("zipcode"));

            reviews.add(review);
        }

        return reviews;
    }

    public ArrayList<ReviewProduct> getTopFiveProducts() {
        HashMap<String, ArrayList<Review>> reviews = selectReview();
        ArrayList<ReviewProduct> reviewRatings = new ArrayList<>();

        for (Map.Entry<String, ArrayList<Review>> entry : reviews.entrySet()) {
            ArrayList<Review> allReviews = entry.getValue();
            ReviewProduct prod = new ReviewProduct();
            if (allReviews.size() > 0) {
                Review r = allReviews.get(0);
                prod.setProductType(r.getProductType());
                prod.setId(r.getProductName());

            }

            for (Review review : allReviews) {
                double rating = Double.parseDouble(review.getRating());
                prod.addToTotalRatings(rating);
            }

            reviewRatings.add(prod);
        }

        Collections.sort(reviewRatings, new Comparator<ReviewProduct>() {
            @Override
            public int compare(ReviewProduct r1, ReviewProduct r2) {
                double val1 = r1.getAverageRating();
                double val2 = r2.getAverageRating();
                if (val1 == val2) return 0;
                if( val1 - val2  > 0)
                    return -1;
                else 
                    return 1;
            }
        });

        return reviewRatings;
    }
}
