package web.ssa.service.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import web.ssa.cache.ProductImgCache;
import web.ssa.dto.products.ProductCreateDTO;
import web.ssa.dto.products.ProductDTO;
import web.ssa.entity.products.ProductImg;
import web.ssa.entity.products.ProductMaster;
import web.ssa.entity.products.ProductVariant;
import web.ssa.mapper.ConvertToEntity;
import web.ssa.repository.products.ProductImgRepository;
import web.ssa.repository.products.ProductRepository;
import web.ssa.repository.products.ProductVariantRepository;
import web.ssa.service.WebDAVService;
import web.ssa.util.FileUtil;
import web.ssa.util.FormatUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private ProductImgRepository imgRepository;

    @Autowired
    private ProductVariantService productVariantService;

    @Autowired
    private ProductImgCache productImgCache;

    @Autowired
    private WebDAVService webDAVService;

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
        List<ProductMaster> products = this.repository.findById(id);
        return products.isEmpty() ? null : products.get(0);
    }

    @Override
    public List<ProductMaster> findByName(String name) {
        return this.repository.findByName(name);
    }

    @Override
    public List<ProductMaster> findById(int id) {
        return this.repository.findById(id);
    }

    @Override
    public void delete(int id) {
        this.repository.deleteById(id);
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
        return repository.findByAmountNot(-1, pageable);
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
}
