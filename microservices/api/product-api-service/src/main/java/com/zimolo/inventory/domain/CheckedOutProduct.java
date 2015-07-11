package com.zimolo.inventory.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zimolo.inventory.domain.util.CustomDateTimeSerializer;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * A Product.
 */
@Document(collection = "CHECKED_OUT_PRODUCT")
public class CheckedOutProduct extends Product{



    @Field("checkout_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private DateTime checkout_date=new DateTime();

    public CheckedOutProduct(Product product){
        this.price=product.getPrice();
        this.unit_price=product.getUnit_price();
        this.height=product.getHeight();
        this.width=product.getWidth();
        this.category=product.getCategory();
        this.name=product.getName();
        this.setSku(product.getSku());
    }

    public CheckedOutProduct(){
        super();
    }

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    public DateTime getCheckout_date() {
        return checkout_date;
    }

    public void setCheckout_date(DateTime checkout_date) {
        this.checkout_date = checkout_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheckedOutProduct product = (CheckedOutProduct) o;

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
            ", checkout_date='" + checkout_date + '\'' +
            '}';
    }

}
