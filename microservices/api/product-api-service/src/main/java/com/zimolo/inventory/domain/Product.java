package com.zimolo.inventory.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A Product.
 */
@Document(collection = "PRODUCT")
public class Product implements Serializable {

    @Id
    protected String id;

    @NotNull
    @Field("sku")
    protected int sku;

    @NotNull
    @Field("name")
    protected String name;

    @Field("category")
    protected String category;

    @Field("height")
    protected Integer height = 0;

    @Field("width")
    protected Integer width = 0;

    @Field("unit_price")
    protected String unit_price = new String("0.00");

    @Field("price")
    protected String price = new String("0.00");


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String p){price=p;}

    public void calculatePrice() {
        BigDecimal unit_price=new BigDecimal(this.unit_price);
        BigDecimal onefourfour=new BigDecimal("144.0");
        BigDecimal area=new BigDecimal((this.getWidth()*this.getHeight()));
        area=area.divide(onefourfour,2, RoundingMode.HALF_UP);
        this.price=area.multiply(unit_price).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (sku != product.sku) return false;
        if (id != null ? !id.equals(product.id) : product.id != null) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (category != null ? !category.equals(product.category) : product.category != null) return false;
        if (height != null ? !height.equals(product.height) : product.height != null) return false;
        if (width != null ? !width.equals(product.width) : product.width != null) return false;
        if (unit_price != null ? !unit_price.equals(product.unit_price) : product.unit_price != null) return false;
        return !(price != null ? !price.equals(product.price) : product.price != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + sku;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (unit_price != null ? unit_price.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id='" + id + '\'' +
            ", sku=" + sku +
            ", name='" + name + '\'' +
            ", category=" + category +
            ", height=" + height +
            ", width=" + width +
            ", unit_price='" + unit_price + '\'' +
            ", price='" + price + '\'' +
            '}';
    }

}
