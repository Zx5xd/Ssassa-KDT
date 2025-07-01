package web.ssa.mapper;

import web.ssa.dto.products.ProductCreateDTO;
import web.ssa.dto.products.ProductDTO;
import web.ssa.entity.products.ProductMaster;
import web.ssa.util.DTOUtil;

public class ConvertToEntity {
    private static final DTOUtil dtoUtil = new DTOUtil();

    public static ProductMaster toProductCreateEntity(ProductCreateDTO cDTO, int simpleImg, int detailImg) {
        ProductMaster product = new ProductMaster();
        product.setName(cDTO.getName());
        product.setCategoryId(cDTO.getCategoryId());
        product.setCategoryChildId(cDTO.getCategoryChildId());
        product.setSimpleImg(simpleImg);
        product.setDetailImg(detailImg);
        product.setPrice(cDTO.getPrice());
        product.setDetail(cDTO.getDetail());
        product.setDefaultVariantId(cDTO.getDefaultVariant());
        product.setAmount(cDTO.getAmount());
        product.setReg(cDTO.getReg());
        product.setCount(cDTO.getCount());
        return product;
    }
}
