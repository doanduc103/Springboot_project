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
//@SqlResultSetMappings(value = {
//        @SqlResultSetMapping(
//                name = "product",
//                classes = @ConstructorResult(
//                        targetClass = product.class,
//                        columns = {
//                                @ColumnResult(name = "id", type = Integer.class),
//                                @ColumnResult(name = "name", type = String.class),
//                                @ColumnResult(name = "description", type = String.class),
//                                @ColumnResult(name = "isAvailable", type = boolean.class),
//                                @ColumnResult(name = "product_price", type = String.class),
//                                @ColumnResult(name = "createddate", type = Date.class),
//                                @ColumnResult(name = "total_sold", type = int.class),
//                                @ColumnResult(name = "image", type = String.class),
//                                @ColumnResult(name = "extraImage", type = String.class),
//                                @ColumnResult(name = "extraImage2", type = String.class),
//                                @ColumnResult(name = "extraImage3", type = String.class),
//                        }
//                ))
//})
//
//@NamedNativeQuery(
//        name = "select4Product",
//        resultSetMapping = "product",
//        query = "select top 5 * from product s order by s.product_price ASC"
//)
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
    public String getExtraImage() {
        if (extraImage == null || id == null) return null;
        return "/Product_Image/" + id + "/" + extraImage;
    }

    @Transient
    public String getExtraImage2() {
        if (extraImage2 == null || id == null) return null;
        return "/Product_Image/" + id + "/" + extraImage2;
    }

    @Transient
    public String getExtraImage3() {
        if (extraImage3 == null || id == null) return null;
        return "/Product_Image/" + id + "/" + extraImage3;
    }
}

