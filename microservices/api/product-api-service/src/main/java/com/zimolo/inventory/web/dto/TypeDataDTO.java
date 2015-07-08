package com.zimolo.inventory.web.dto;


public class TypeDataDTO<T> {
    boolean type;
    String error;
    T data;

    private TypeDataDTO(){}
    private TypeDataDTO(boolean type, T data, String err){
        this.type=type;
        this.data=data;
        this.error=err;
    }

    public static TypeDataDTO SuccessInstance(Object d){
        return new TypeDataDTO(true,d,null);
    }

    public static TypeDataDTO ErrorInstance(String err){
        return new TypeDataDTO (false,null,err);
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
