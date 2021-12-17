package com.example.demo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer id;
    @Column(name = "name", unique = true)
    public String name;
    @Column(name = "description")
    public String description;
    @Column(name = "isAvailable")
    public boolean isAvailable;
    @Column(name = "product_price")
    public String product_price;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "createddate")
    public Date createddate;
    @Column(name = "quantity")
    public int quantity;
    @Column(name = "total_sold")
    public int total_sold;
    @Column(name = "image")
    public String image;
    @Column(name = "extraImage")
    public String extraImage;
    @Column(name = "extraImage2")
    public String extraImage2;
    @Column(name = "extraImage3")
    public String extraImage3;

    @ManyToMany(mappedBy = "product_category")
    private List<category> category;

    @Transient
    public String getImage() {
        if (image == null || id == null) return null;
        return "/Product_Image/" + id + "/" + image;
    }

    @Transient
    public String getExtraImagePath() {
        if (extraImage == null || id == null) return null;
        return "/Product_Image/" + id + "/" + extraImage;
    }

    @Transient
    public String getExtraImagePath2() {
        if (extraImage2 == null || id == null) return null;
        return "/Product_Image/" + id + "/" + extraImage2;
    }

    @Transient
    public String getExtraImagePath3() {
        if (extraImage3 == null || id == null) return null;
        return "/Product_Image/" + id + "/" + extraImage3;
    }
}

