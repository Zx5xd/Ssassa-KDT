package web.ssa.cache;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;
import web.ssa.entity.products.ProductImg;
import web.ssa.repository.products.ProductImgRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductImgCache {

    private final ProductImgRepository productImgRepository;

    @Getter
    private Map<Integer, ProductImg> imgCache = new HashMap<>();

    public ProductImgCache(ProductImgRepository productImgRepository) {
        this.productImgRepository = productImgRepository;
    }

    @PostConstruct
    public void init() {
        System.out.println("ProductImg 캐시 초기화 중...");
        List<ProductImg> allImages = productImgRepository.findAll();
        allImages.forEach(img -> imgCache.put(img.getId(), img));
        System.out.println("ProductImg 캐시 완료! 항목 수: " + imgCache.size());
    }

    public ProductImg getImageById(int id) {
        return imgCache.get(id);
    }

    public String getImageUrl(int id) {
        ProductImg img = imgCache.get(id);
        return img != null ? "https://web.hyproz.myds.me/ssa_shop/img/" + img.getImgPath() : null;
    }

    public void reload() {
        this.imgCache.clear();
        this.init();
    }

    public void addImage(ProductImg img) {
        imgCache.put(img.getId(), img);
    }

    public void updateImage(ProductImg img) {
        imgCache.put(img.getId(), img);
    }

    public void removeImage(int id) {
        imgCache.remove(id);
    }
}