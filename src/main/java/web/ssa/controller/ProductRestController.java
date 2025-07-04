package web.ssa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import web.ssa.dto.PageResponse;
import web.ssa.dto.products.SimpleProductDTO;
import web.ssa.entity.products.ProductImg;
import web.ssa.entity.products.ProductMaster;
import web.ssa.service.products.ProductService;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/pdr/*")
public class ProductRestController {
    @Autowired
    private ProductService productService;

    @GetMapping("list")
    public PageResponse<SimpleProductDTO> listProducts(@RequestParam("cid") int cid, Pageable pageable) {
        Page<ProductMaster> page = this.productService.findByCategoryId(cid, pageable);
        List<SimpleProductDTO> dtoList = page.getContent().stream().map(SimpleProductDTO::from).toList();
        return new PageResponse<>(
                dtoList,
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    @GetMapping("img/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") int id) {
        ProductImg productImg = this.productService.findByImgId(id);
        if (productImg == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            String imageUrl = "https://web.hyproz.myds.me/ssa_shop/img/"+productImg.getImgPath();
            URL url = new URL(imageUrl);
            InputStream in = url.openStream();
            byte[] imageBytes = StreamUtils.copyToByteArray(in);

            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf("image/webp"))
                    .body(imageBytes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
