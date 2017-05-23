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
public class PopProduct extends Product {

    int numTimesBought;
    String productType;

    public PopProduct() {
        super();
    }

    public int getNumTimesBought() {
        return numTimesBought;
    }

    public void setNumTimesBought(int numTimesBought) {
        this.numTimesBought = numTimesBought;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductType() {
        return productType;
    }
}
