package com.zimolo.inventory.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * A Category.
 */
@Document(collection = "CATEGORY")
public class Category implements Serializable {

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("product_quantity")
    private Integer product_quantity=0;

    @Field("height")
    private Integer height=0;

    @Field("width")
    private Integer width=0;

    @Field("unit_price")
    private String unit_price=new String("0.00");

    @Field("image_id")
    private String image_id;

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

    public Integer getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(Integer product_quantity) {
        this.product_quantity = product_quantity;
    }

    public void reduceProduct_quantity(int minus) {this.product_quantity-=minus;}

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != null ? !id.equals(category.id) : category.id != null) return false;
        if (name != null ? !name.equals(category.name) : category.name != null) return false;
        if (product_quantity != null ? !product_quantity.equals(category.product_quantity) : category.product_quantity != null)
            return false;
        if (height != null ? !height.equals(category.height) : category.height != null) return false;
        if (width != null ? !width.equals(category.width) : category.width != null) return false;
        if (unit_price != null ? !unit_price.equals(category.unit_price) : category.unit_price != null) return false;
        return !(image_id != null ? !image_id.equals(category.image_id) : category.image_id != null);

    }

    @Override
    public String toString() {
        return "Category{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", product_quantity=" + product_quantity +
            ", height=" + height +
            ", width=" + width +
            ", unit_price='" + unit_price + '\'' +
            ", image_id='" + image_id + '\'' +
            '}';
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (product_quantity != null ? product_quantity.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (unit_price != null ? unit_price.hashCode() : 0);
        result = 31 * result + (image_id != null ? image_id.hashCode() : 0);
        return result;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }


}
