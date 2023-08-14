package com.example.carinventory.models;

public class CarModel {
    public String make , model , condition , engine_cylinder , year , no_of_doors , price , color , image  ,sold_date;
    public boolean is_sold;
    public CarModel(String make, String model , String condition , String engine_cylinder , String year , String no_of_doors, String price , String color , String image , boolean is_sold , String sold_date){
        this.make = make;
        this.model = model;
        this.condition = condition;
        this.engine_cylinder = engine_cylinder;
        this.year = year;
        this.no_of_doors = no_of_doors;
        this.price = price;
        this.color = color;
        this.image = image;
        this.is_sold = is_sold;
        this.sold_date = sold_date;

    }
}
