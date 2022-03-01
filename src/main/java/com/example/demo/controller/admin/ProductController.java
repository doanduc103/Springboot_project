package com.example.demo.controller.admin;

import com.example.demo.entity.FileUploadUtil;
import com.example.demo.entity.product;
import com.example.demo.model.dto.productDTO;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
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
                                @RequestParam("extraImage") MultipartFile[] extraImageFiles) throws IOException {


        int count = 0;
        for (MultipartFile extraImagefile : extraImageFiles) {
            String extraImageName = StringUtils.cleanPath(extraImagefile.getOriginalFilename());
            if (count == 0) {
                productDTO.setExtraImageThumbnails(extraImageName);
            }
            if (count == 1) {
                productDTO.setExtraImageThumbnails2(extraImageName);
            }
            if (count == 2) {
                productDTO.setExtraImageThumbnails3(extraImageName);
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

    @GetMapping("/trang-chu/edit-san-pham/{id}")
    public String UpdateProduct(Model model,
                                productDTO productDTO,
                                @PathVariable(name = "id") Integer id) throws IOException {
        try {
            productService.UpdateProduct(id,productDTO);
            model.addAttribute("PageTittle","Update Product " + id );
        } catch (Exception e) {
            model.addAttribute("UpdateError","Update Product không thành công");
            return "redirect:/trang-chu/ao-clothes/0";

        }
        return "admin/tao-san-pham";
    }


    @GetMapping("/trang-chu/edit-san-pham/image/{id}")
    public String UpdateImageProduct(@PathVariable("id") Integer id, productDTO productDTO,Model model) {
        productService.UpdateImageProduct(id, productDTO);
        return "admin/tao-san-pham";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/trang-chu/xoa-san-pham/{id}")
    public String DeleteProduct(@PathVariable Integer id, Model model) throws IOException {
        product product = productService.getProductById(id);
        model.addAttribute("msDelete", "Xóa sản phẩm với " + id + "thành công" );
        File file = new File("Product_Image/" + product.getId());
        if (file.exists() & !file.isFile()) {
            FileUtils.deleteDirectory(file);
            if (file.delete()) {
                model.addAttribute("Xóa folder thành công", "msgDelete");
            } else {
                model.addAttribute("Xóa folder không thành công", "errorMsg");
            }
        }
        productService.DeleteProduct(id);
        return "redirect:/trang-chu/ao-clothes/0";
    }

    @GetMapping("/search")
    public String Search(String keyword, Model model) {
        List<product> products = productService.Search(keyword);
        model.addAttribute("listProduct", products);
        model.addAttribute("keyword", keyword);
        return "admin/search_result";
    }
}
