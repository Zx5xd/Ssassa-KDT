package web.ssa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import web.ssa.cache.CategoriesCache;
import web.ssa.cache.ProductImgCache;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.products.ProductMaster;
import web.ssa.service.products.ProductService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Controller
public class IndexController {

    @Autowired
    private CategoriesCache categoriesCache;

    @Autowired
    private ProductImgCache productImgCache;

    @Autowired
    private ProductService productService;

    @GetMapping({ "/", "/index" })
    public String index(Model model) {
        List<Categories> categories = categoriesCache.getCachedCategories();
        Map<Integer, List<ProductMaster>> categoryProducts = new HashMap<>();
        Map<Integer, String> categoryBanners = new HashMap<>();

        // 각 카테고리별로 랜덤 1개 상품과 배너 이미지 추가
        for (Categories category : categories) {
            int categoryId = category.getId();

            System.out.println("=== 카테고리 " + categoryId + " (" + category.getName() + ") 처리 중 ===");

            // 카테고리별 상품 조회 (최대 10개)
            Page<ProductMaster> productPage = productService.getPagedProductsByCategory(categoryId, 0, 10);
            List<ProductMaster> products = new ArrayList<>(productPage.getContent());

            System.out.println("카테고리 " + categoryId + " 전체 상품 수: " + products.size());

            // 랜덤으로 1개 선택
            if (products.size() > 1) {
                java.util.Collections.shuffle(products);
                products = products.subList(0, 1);
                if (products.get(0).getPrice() == 0) {
                    products.get(0).setPrice(productService.getProductPrice(products.get(0).getId()));
                }
                System.out.println("카테고리 " + categoryId + " 랜덤 1개 선택 완료");
            } else if (products.size() > 0) {
                System.out.println("카테고리 " + categoryId + " 전체 " + products.size() + "개 사용");
            } else {
                System.out.println("카테고리 " + categoryId + " 상품 없음!");
            }

            categoryProducts.put(categoryId, products);

            // 카테고리별 배너 이미지 URL 설정
            String bannerUrl = getBannerUrlForCategory(categoryId);
            categoryBanners.put(categoryId, bannerUrl);
        }

        System.out.println("=== 최종 결과 ===");
        for (Entry<Integer, List<ProductMaster>> entry : categoryProducts.entrySet()) {
            System.out.println("카테고리 " + entry.getKey() + " 상품 수: " + entry.getValue().size());
            for (ProductMaster product : entry.getValue()) {
                System.out.println("  - " + product.getName() + " (ID: " + product.getId() + ")");
            }
        }

        model.addAttribute("categories", categories);
        model.addAttribute("categoryProducts", categoryProducts);
        model.addAttribute("categoryBanners", categoryBanners);
        model.addAttribute("productImgCache", productImgCache);

        return "shop/index";
    }

    // 카테고리별 배너 이미지 URL 반환
    private String getBannerUrlForCategory(int categoryId) {
        String webUrl = "https://web.hyproz.myds.me/ssa_shop/";
        String airImg = "kalisha-airfryer.jpg";
        String washImg = "planetcare-washing.jpg";
        String refImg = "lisa-anna-refrigerator.jpg";

        switch (categoryId) {
            case 1: // 에어프라이어
                return webUrl + airImg;
            case 2: // 건조기
                return webUrl + washImg;
            case 3: // 세탁기
                return webUrl + washImg;
            case 4: // 올인원 세탁기
                return webUrl + washImg;
            case 5: // 세탁+건조기
                return webUrl + washImg;
            case 6: // 냉장고
                return webUrl + refImg;
            case 7: // 김치냉장고
                return webUrl + refImg;
            case 8: // PC주변기기
                return "https://images.unsplash.com/photo-1547082299-de196ea013d6?w=600&h=400&fit=crop&crop=center";
            case 9: // PC부품
                return "https://images.unsplash.com/photo-1593640408182-31c70c8268f5?w=600&h=400&fit=crop&crop=center";
            case 10: // 핸드폰
                return "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=600&h=400&fit=crop&crop=center";
            case 11: // 태블릿PC
                return "https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=600&h=400&fit=crop&crop=center";
            case 12: // 노트북
                return "https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=600&h=400&fit=crop&crop=center";
            case 13: // TV
                return "https://images.unsplash.com/photo-1593359677879-a4bb92f829d1?w=600&h=400&fit=crop&crop=center";
            default:
                return "https://images.unsplash.com/photo-1441986300917-64674bd600d8?w=600&h=400&fit=crop&crop=center";
        }
    }

    // 405 Method Not Allowed 에러 핸들러
    @RequestMapping("/error/405")
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String handleMethodNotAllowed(Model model) {
        model.addAttribute("error", "지원하지 않는 HTTP 메서드입니다.");
        model.addAttribute("message", "GET 요청이 필요한 페이지에 POST 요청을 보내거나, 그 반대의 경우입니다.");
        return "error/405";
    }
}