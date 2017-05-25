/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.Catalogue;
import data.Product;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author delianaescobari
 */
public class AjaxUtility {
    public StringBuffer readdata(String searchId) {
        StringBuffer sb = new StringBuffer();
        HashMap<String, Product> data;
        data = getData();
        for (Map.Entry pi : data.entrySet()) {
            Product p = (Product) pi.getValue();
            if (p.getName().toLowerCase().startsWith(searchId)) {
                sb.append("<product>");
                sb.append("<id>").append(p.getId()).append("</id>");
                sb.append("<productName>").append(p.getName()).append("</productName>");
                sb.append("</product>");
            }
        }
        return sb;

    }

    public static HashMap<String, Product> getData() {
        return Catalogue.allProducts;
    }

}
