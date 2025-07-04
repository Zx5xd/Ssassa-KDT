package web.ssa.service.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.ssa.dto.products.SimpleProductDTO;
import web.ssa.entity.products.ProductImg;

import web.ssa.cache.ProductImgCache;
import web.ssa.dto.products.ProductCreateDTO;
import web.ssa.dto.products.ProductDTO;
import web.ssa.dto.products.ProductVariantDTO;
import web.ssa.entity.products.ProductImg;
import web.ssa.entity.products.ProductMaster;
import web.ssa.repository.products.ProductImgRepository;
import web.ssa.entity.products.ProductVariant;
import web.ssa.mapper.ConvertToEntity;
import web.ssa.repository.products.ProductImgRepository;
import web.ssa.repository.products.ProductRepository;
import web.ssa.repository.products.ProductVariantRepository;
import web.ssa.service.WebDAVService;
import web.ssa.util.FileUtil;
import web.ssa.util.FormatUtil;
import web.ssa.repository.products.ProductRepositoryCustom;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private ProductImgRepository productImgRepository;
    @Autowired
    private ProductImgRepository imgRepository;

    @Autowired
    private ProductVariantService productVariantService;

    @Autowired
    private ProductImgCache productImgCache;

    @Autowired
    private WebDAVService webDAVService;

    @Autowired
    private ProductRepositoryCustom productRepositoryCustom;

    public ProductServiceImpl(ProductRepository repository, ProductImgRepository imgRepository) {
        this.repository = repository;
        this.imgRepository = imgRepository;
    }

    @Override
    public List<ProductMaster> getAllProducts() {
        return this.repository.findAll();
    }

    @Override
    public ProductMaster getProductById(int id) {
        return this.repository.findById(id);
    }

    @Override
    public List<ProductMaster> findByName(String name) {
        return this.repository.findByName(name);
    }

    @Override
    public ProductMaster findById(int id) {
        return this.repository.findById(id);
    }

    @Override
    public String findNameById(int id) {
        return this.repository.findNameById(id);
    }

    @Override
    public int delete(int id) {
        return this.repository.deleteById(id);
    }

    @Override
    public void softDeleteProduct(int productId) {
        ProductMaster product = getProductById(productId);
        System.out.println("softDeleteProduct : " + product.toString());
        product.setAmount(-1);
        this.repository.save(product);
    }

    @Override
    public void saveProduct(ProductCreateDTO createDto) {

        System.out.println("saveProduct : " + createDto.toString());

        String simpleImg = "";
        String detailImg = "";

        if (createDto.getSimpleImgFileName() != null) {
            // simpleImg =
            // FileUtil.changeFileNameToHash(createDto.getSimpleImg().getOriginalFilename());
            simpleImg = createDto.getSimpleImgFileName();
            this.saveProductImg(
                    simpleImg, 0);
        }

        if (createDto.getDetailImgFileName() != null) {
            // detailImg =
            // FileUtil.changeFileNameToHash(createDto.getDetailImg().getOriginalFilename());
            detailImg = createDto.getDetailImgFileName();
            this.saveProductImg(
                    detailImg, 0);
        }

        if (!FormatUtil.isValidJson(createDto.getDetail())) {
            createDto.setDetail("{}");
        }

        this.productImgCache.reload();

        ProductMaster product = ConvertToEntity.toProductCreateEntity(
                createDto,
                this.productImgCache.getImageIdByUrl(simpleImg),
                this.productImgCache.getImageIdByUrl(detailImg));

        repository.save(product);
    }

    public Page<ProductMaster> getPagedProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.repository.findAll(pageable);
    }

    @Override
    public Page<ProductMaster> findByCategoryId(int categoryId, Pageable pageable) {
        return this.repository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public Page<SimpleProductDTO> findBySimpleCategoryId(int categoryId, Pageable pageable) {
        Page<ProductMaster> page = this.repository.findByCategoryId(categoryId, pageable);
        return page.map(SimpleProductDTO::from);
    }

    @Override
    public Page<ProductMaster> findByCategoryChildId(int categoryChildId, Pageable pageable) {
        return this.repository.findByCategoryChildId(categoryChildId, pageable);
    }

    @Override
    public ProductImg findByImgId(int productImgId) {
        return this.productImgRepository.findById(productImgId);
//        return repository.findByAmountNot(-1, pageable);
    }

    public Page<ProductMaster> getPagedProductsByCategory(int categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByCategoryIdAndAmountNot(categoryId, -1, pageable);
    }

    @Override
    public Page<ProductMaster> searchProducts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByNameContainingAndAmountNot(keyword, -1, pageable);
    }

    @Override
    public void saveProductImg(String img, int imgId) {
        ProductImg productImg = new ProductImg();

        if (imgId != 0) {
            productImg.setId(imgId);
        }

        productImg.setImgPath(img);

        this.imgRepository.save(productImg);
    }

    @Override
    public int getProductPrice(int productId) {
        ProductMaster product = getProductById(productId);
        if (product == null) {
            return 0;
        }

        int price = product.getPrice();

        if (price == 0 && product.getDefaultVariantId() != null) {
            ProductVariant variant = this.productVariantService.getVariantById(product.getDefaultVariantId());
            if (variant != null) {
                price = variant.getPrice();
            }
        }
        return price;
    }

    @Override
    public String getProductSimpleImg(ProductMaster product) {
        return productImgCache.getImageUrl(product.getSimpleImg());
    }

    @Override
    public void updateProduct(int id, ProductCreateDTO editProduct, ProductDTO originalProduct) {

        try {
            if (!editProduct.getSimpleImg().getOriginalFilename()
                    .equals(this.productImgCache.getImageUrl(originalProduct.getSimpleImg()))) {
                System.out.println(
                        "delete simpleImg : " + this.productImgCache.getImageUrl(originalProduct.getSimpleImg()));
                // webDAVService.deleteFile(this.productImgCache.getImageUrl(originalProduct.getSimpleImg()));
                String simpleImgUrl = webDAVService.uploadFile(editProduct.getSimpleImg());
                String simpleImgFileName = simpleImgUrl.substring(simpleImgUrl.lastIndexOf("/") + 1);
                this.saveProductImg(simpleImgFileName,
                        originalProduct.getSimpleImg());

            }
            if (!editProduct.getDetailImg().getOriginalFilename()
                    .equals(this.productImgCache.getImageUrl(originalProduct.getDetailImg()))) {
                System.out.println(
                        "delete detailImg : " + this.productImgCache.getImageUrl(originalProduct.getDetailImg()));
                // webDAVService.deleteFile(this.productImgCache.getImageUrl(originalProduct.getDetailImg()));
                String detailImgUrl = webDAVService.uploadFile(editProduct.getDetailImg());
                String detailImgFileName = detailImgUrl.substring(detailImgUrl.lastIndexOf("/") + 1);
                this.saveProductImg(detailImgFileName,
                        originalProduct.getDetailImg());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.productImgCache.reload();

        ProductMaster product = ConvertToEntity.toProductCreateEntity(editProduct,
                originalProduct.getSimpleImg(),
                originalProduct.getDetailImg());

        this.repository.save(product);

    }

    @Override
    public int uploadImg(String img) {
        ProductImg productImg = ProductImg.builder()
                .imgPath(img)
                .build();

        productImgRepository.save(productImg);

        return productImg.getId();
    }

    @Override
    public void saveProductWithVariants(ProductCreateDTO createDto) {
        System.out.println("saveProductWithVariants : " + createDto.toString());
        createDto.getVariants().forEach(variant -> {
            System.out.println("variant : " + variant.toString());
        });

        String simpleImg = "";

        // 메인 상품 이미지 처리
        if (createDto.getSimpleImgFileName() != null) {
            simpleImg = createDto.getSimpleImgFileName();
            this.saveProductImg(simpleImg, 0);
        }

        // JSON 유효성 검사
        if (!FormatUtil.isValidJson(createDto.getDetail())) {
            createDto.setDetail("{}");
        }

        this.productImgCache.reload();

        // 메인 상품 저장
        ProductMaster product = ConvertToEntity.toProductCreateEntity(
                createDto,
                this.productImgCache.getImageIdByUrl(simpleImg),
                0); // detailImg는 0으로 설정

        ProductMaster savedProduct = repository.save(product);

        // 상품 변형들 저장
        if (createDto.getVariants() != null && !createDto.getVariants().isEmpty()) {
            for (ProductVariantDTO variantDto : createDto.getVariants()) {
                // 변형 상세 이미지 처리
                String detailImg = "";
                if (variantDto.getDetailImgFileName() != null) {
                    detailImg = variantDto.getDetailImgFileName();
                    System.out.println("detailImg : " + detailImg);
                    this.saveProductImg(detailImg, 0);
                }

                this.productImgCache.reload();

                // 변형 엔티티 생성 및 저장
                ProductVariant variant = new ProductVariant();
                variant.setMasterId(savedProduct);
                variant.setName(variantDto.getName());
                variant.setPrice(variantDto.getPrice());
                variant.setAmount(variantDto.getAmount());
                variant.setSimpleImg(savedProduct.getSimpleImg()); // 메인 상품의 simpleImg ID 사용
                variant.setDetailImg(this.productImgCache.getImageIdByUrl(detailImg));

                // 상세 정보 JSON 처리
                if (variantDto.getDetail() != null && !variantDto.getDetail().isEmpty()) {
                    try {
                        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                        String detailJson = mapper.writeValueAsString(variantDto.getDetail());
                        variant.setDetail(detailJson);
                    } catch (Exception e) {
                        variant.setDetail("{}");
                        e.printStackTrace();
                    }
                } else {
                    variant.setDetail("{}");
                }

                productVariantService.saveVariant(variant);
            }
        }
    }

    @Override
    public void updateProductWithVariants(int id, ProductCreateDTO editProduct, ProductDTO originalProduct) {
        System.out.println("updateProductWithVariants : " + editProduct.toString());

        try {
            // 메인 상품 이미지 처리
            if (editProduct.getSimpleImg() != null && !editProduct.getSimpleImg().isEmpty()) {
                String simpleImgUrl = webDAVService.uploadFile(editProduct.getSimpleImg());
                String simpleImgFileName = simpleImgUrl.substring(simpleImgUrl.lastIndexOf("/") + 1);
                this.saveProductImg(simpleImgFileName, originalProduct.getSimpleImg());
            }

            // JSON 유효성 검사
            if (!FormatUtil.isValidJson(editProduct.getDetail())) {
                editProduct.setDetail("{}");
            }

            this.productImgCache.reload();

            // 메인 상품 수정
            ProductMaster product = ConvertToEntity.toProductCreateEntity(editProduct,
                    originalProduct.getSimpleImg(),
                    0); // detailImg는 0으로 설정

            product.setId(id);
            ProductMaster savedProduct = this.repository.save(product);

            // 기존 변형들 수정
            if (editProduct.getVariants() != null && !editProduct.getVariants().isEmpty()) {
                for (ProductVariantDTO variantDto : editProduct.getVariants()) {
                    if (variantDto.getId() > 0) {
                        // 기존 변형 수정
                        ProductVariant existingVariant = productVariantService.getVariantById(variantDto.getId());
                        if (existingVariant != null) {
                            // 변형 상세 이미지 처리
                            if (variantDto.getDetailImgFile() != null && !variantDto.getDetailImgFile().isEmpty()) {
                                String detailImgUrl = webDAVService.uploadFile(variantDto.getDetailImgFile());
                                String detailImgFileName = detailImgUrl.substring(detailImgUrl.lastIndexOf("/") + 1);
                                this.saveProductImg(detailImgFileName, existingVariant.getDetailImg());
                            }

                            // 기존 변형 정보 업데이트
                            existingVariant.setName(variantDto.getName());
                            existingVariant.setPrice(variantDto.getPrice());
                            existingVariant.setAmount(variantDto.getAmount());
                            existingVariant.setSimpleImg(savedProduct.getSimpleImg());

                            // 상세 정보 JSON 처리
                            if (variantDto.getDetail() != null && !variantDto.getDetail().isEmpty()) {
                                try {
                                    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                                    String detailJson = mapper.writeValueAsString(variantDto.getDetail());
                                    existingVariant.setDetail(detailJson);
                                } catch (Exception e) {
                                    existingVariant.setDetail("{}");
                                    e.printStackTrace();
                                }
                            } else {
                                existingVariant.setDetail("{}");
                            }

                            productVariantService.saveVariant(existingVariant);
                        }
                    } else {
                        // 새로운 변형 추가
                        String detailImg = "";
                        if (variantDto.getDetailImgFileName() != null) {
                            detailImg = variantDto.getDetailImgFileName();
                            this.saveProductImg(detailImg, 0);
                        }

                        ProductVariant newVariant = new ProductVariant();
                        newVariant.setMasterId(savedProduct);
                        newVariant.setName(variantDto.getName());
                        newVariant.setPrice(variantDto.getPrice());
                        newVariant.setAmount(variantDto.getAmount());
                        newVariant.setSimpleImg(savedProduct.getSimpleImg());
                        newVariant.setDetailImg(this.productImgCache.getImageIdByUrl(detailImg));

                        if (variantDto.getDetail() != null && !variantDto.getDetail().isEmpty()) {
                            try {
                                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                                String detailJson = mapper.writeValueAsString(variantDto.getDetail());
                                newVariant.setDetail(detailJson);
                            } catch (Exception e) {
                                newVariant.setDetail("{}");
                                e.printStackTrace();
                            }
                        } else {
                            newVariant.setDetail("{}");
                        }

                        productVariantService.saveVariant(newVariant);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ProductMaster> searchByDynamicFilter(Map<String, List<String>> filterMap) {
        return productRepositoryCustom.searchByDynamicFilter(filterMap);
    }
}
