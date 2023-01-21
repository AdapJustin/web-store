/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Sam Louis AF
 */
public class InventoryObject {
    private String Product,Type,Image,Price,Stock,Brand;

    public InventoryObject(String Product, String Type, String Image, String Price, String Stock, String Brand) {
        this.Product = Product;
        this.Type = Type;
        this.Image = Image;
        this.Price = Price;
        this.Stock = Stock;
        this.Brand = Brand;
    }

    public InventoryObject() {
       
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String Brand) {
        this.Brand = Brand;
    }

    
    
    public String getProduct() {
        return Product;
    }

    public void setProduct(String Product) {
        this.Product = Product;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String Stock) {
        this.Stock = Stock;
    }
}
