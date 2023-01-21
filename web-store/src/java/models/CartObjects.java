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
public class CartObjects {
    private String Email,Product,Type,Price,Stock,Image,CartId;



    public CartObjects(String Email, String Product, String Type, String Price, String Stock,String Image,String CartId) {
        this.Email = Email;
        this.Product = Product;
        this.Type = Type;
        this.Price = Price;
        this.Stock = Stock;
        this.Image = Image;
        this.CartId = CartId;
    }

    public String getCartId() {
        return CartId;
    }

    public void setCartId(String CartId) {
        this.CartId = CartId;
    }
    
    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
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
    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }
}
