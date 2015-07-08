package com.zimolo.inventory.web.dto;

/**
 * Created by sophiaweng on 15-06-01.
 */
public class CategoryImportDTO {
    String category_id;
    Integer import_quantity;

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public Integer getImport_quantity() {
        return import_quantity;
    }

    public void setImport_quantity(Integer import_quantity) {
        this.import_quantity = import_quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryImportDTO that = (CategoryImportDTO) o;

        if (category_id != null ? !category_id.equals(that.category_id) : that.category_id != null) return false;
        return !(import_quantity != null ? !import_quantity.equals(that.import_quantity) : that.import_quantity != null);

    }

    @Override
    public int hashCode() {
        int result = category_id != null ? category_id.hashCode() : 0;
        result = 31 * result + (import_quantity != null ? import_quantity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CategoryImportDTO{" +
            "category_id='" + category_id + '\'' +
            ", import_quantity=" + import_quantity +
            '}';
    }
}
