package com.example.demo.model.dto;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class productDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @NotBlank(message = "Tên hàng hóa không được để trống")
    public String name;
    @NotBlank(message = "Không được để trống")
    public String description;
    public Integer product_price;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    public boolean isAvailable;
    public Date createddate;
    @NotNull(message = "Thêm số lương hàng hóa")
    @Range(min = 1, max = 500)
    public int quantity;
    public String image;
    public String extraImageThumbnails;
    public String extraImageThumbnails2;
    public String extraImageThumbnails3;


}
