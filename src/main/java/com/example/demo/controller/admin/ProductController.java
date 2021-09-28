package com.example.demo.controller.admin;

import com.example.demo.entity.FileUploadUtil;
import com.example.demo.entity.product;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.dto.productDTO;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/trang-chu/ao-clothes/{page}")
    public String showProduct(@PathVariable("page") Integer page, Model model, product product) {
        Page<product> products = productRepository.findAll(PageRequest.of(page, 8));
        model.addAttribute("currentPage", page);
        model.addAttribute("TotalPage", products.getTotalPages());
        model.addAttribute("totalItem", products.getTotalElements());
        model.addAttribute("product", product);
        model.addAttribute("products", products);
        return "admin/ao-clothes";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/trang-chu/tao-san-pham")
    public String CreateProduct(Model model, productDTO productDTO) {
        model.addAttribute("productDTO", new productDTO());
        model.addAttribute("PageTittle", "Thêm mới sản phẩm");
        return "admin/tao-san-pham";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/trang-chu/tao-san-pham")
    public String CreateProduct(@Valid productDTO productDTO, BindingResult br,
                                Model model,
//                                @RequestParam("mainImage") MultipartFile multipartFile,
                                @RequestParam("extraImage") MultipartFile[] extraImageFiles) throws IOException {
//        String mainImage = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        productDTO.setImage(mainImage);

        int count = 0;
        for (MultipartFile extraImagefile : extraImageFiles) {
            String extraImageName = StringUtils.cleanPath(extraImagefile.getOriginalFilename());
            if (count == 0) {
                productDTO.setExtraImageThumbnail(extraImageName);
            }
            if (count == 1) {
                productDTO.setExtraImageThumbnail2(extraImageName);
            }
            if (count == 2) {
                productDTO.setExtraImageThumbnail3(extraImageName);
            }
            if (count == 3) {
                productDTO.setImage(extraImageName);
            }
            count++;
        }
        if (br.hasErrors()) {
            model.addAttribute("msg", br);
            return "admin/tao-san-pham";
        }
        product product = productService.createProduct(productDTO);
        String uploadDir = "Product_Image/" + product.getId();
        for (MultipartFile fileImage : extraImageFiles) {
            String fileName = StringUtils.cleanPath(fileImage.getOriginalFilename());
            FileUploadUtil.saveImage(uploadDir, fileImage, fileName);
        }
        model.addAttribute("success", "Tạo sản phẩm thành công");
        return "redirect:/trang-chu/ao-clothes/0";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/trang-chu/edit-san-pham/{id}")
    public String UpdateProduct(Model model, productDTO productDTO, @PathVariable(name = "id") Long id) {
        Optional<product> EditProduct = productRepository.findById(id);
        if (EditProduct.isPresent()) {
            model.addAttribute("productDTO", EditProduct);
        } else {
            model.addAttribute("productDTO", productDTO);
        }
        return "admin/tao-san-pham";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/trang-chu/xoa-san-pham/{id}")
    public String DeleteProduct(@PathVariable Long id, Model model) throws IOException {
        product product = productService.getProductById(id);
        File file = new File("Product_Image/" + product.getId());
        FileUtils.deleteDirectory(file);
        if (file.delete()) {
            model.addAttribute("Xóa folder thành công", "msgDelete");
        } else {
            model.addAttribute("Xóa folder không thành công", "errorMsg");
        }
        productService.DeleteProduct(id);
        return "redirect:/trang-chu/ao-clothes/0";
    }
}
