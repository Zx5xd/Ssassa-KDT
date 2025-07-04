package web.ssa.mapper;

import web.ssa.dto.categories.CategoriesChildDTO;
import web.ssa.dto.categories.CategoriesDTO;
import web.ssa.dto.categories.CategoryFieldsDTO;
import web.ssa.dto.products.ProductCreateDTO;
import web.ssa.dto.products.ProductDTO;
import web.ssa.dto.products.ProductImgDTO;
import web.ssa.dto.products.ProductReviewDTO;
import web.ssa.dto.products.ProductVariantDTO;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoriesChild;
import web.ssa.entity.categories.CategoryFields;
import web.ssa.entity.products.ProductImg;
import web.ssa.entity.products.ProductMaster;
import web.ssa.entity.products.ProductReview;
import web.ssa.entity.products.ProductVariant;
import web.ssa.util.DTOUtil;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertToDTO {

    private static final DTOUtil dtoUtil = new DTOUtil();

    public static List<CategoriesChildDTO> categoryChildDTOList(List<CategoriesChild> entities) {
        return entities.stream()
                .map(entity -> {
                    CategoriesChildDTO dto = new CategoriesChildDTO();
                    dto.setId(entity.getId());
                    dto.setCode(String.valueOf(entity.getCode()));
                    dto.setName(entity.getName());
                    dto.setCategoryId(entity.getCategoryChildId().getId());

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public static List<CategoriesDTO> categoryDTOList(List<Categories> entities) {
        return entities.stream()
                .map(entity -> {
                    CategoriesDTO dto = new CategoriesDTO();
                    dto.setId(entity.getId());
                    dto.setCode(String.valueOf(entity.getCode()));
                    dto.setName(entity.getName());

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public static List<CategoryFieldsDTO> categoryFieldsDTOList(List<CategoryFields> entities) {
        return entities.stream()
                .map(entity -> {
                    CategoryFieldsDTO dto = new CategoryFieldsDTO();
                    dto.setId(entity.getId());
                    dto.setCategoryId(entity.getCategoryFieldId().getId()); // categoryFieldId는 Categories 객체
                    if (entity.getCategoryChildId() != null) {
                        dto.setCategoryChildId(entity.getCategoryChildId().getId());
                    } else {
                        dto.setCategoryChildId(0);
                    }
                    dto.setAttributeKey(entity.getAttributeKey());
                    dto.setDisplayName(entity.getDisplayName());
                    dto.setDataType(entity.getDataType());
                    dto.setIsFilterable(entity.getIsFilterable());
                    dto.setUnit(entity.getUnit());
                    dto.setDisplayOrder(entity.getDisplayOrder());
                    dto.setTooltip(entity.getTooltip());
                    dto.setValueList(entity.getValueList());
                    dto.setFormattedValue(dtoUtil.formatValueList(entity.getValueList()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public static CategoryFieldsDTO categoryFieldsDTO(CategoryFields entity) {
        CategoryFieldsDTO dto = new CategoryFieldsDTO();
        dto.setId(entity.getId());
        dto.setCategoryId(entity.getCategoryFieldId().getId()); // categoryFieldId는 Categories 객체
        if (entity.getCategoryChildId() != null) {
            dto.setCategoryChildId(entity.getCategoryChildId().getId());
        } else {
            dto.setCategoryChildId(0);
        }
        dto.setAttributeKey(entity.getAttributeKey());
        dto.setDisplayName(entity.getDisplayName());
        dto.setDataType(entity.getDataType());
        dto.setIsFilterable(entity.getIsFilterable());
        dto.setUnit(entity.getUnit());
        dto.setDisplayOrder(entity.getDisplayOrder());
        dto.setTooltip(entity.getTooltip());
        dto.setValueList(entity.getValueList());
        dto.setFormattedValue(dtoUtil.formatValueList(entity.getValueList()));
        return dto;
    }

    public static List<ProductDTO> productDTOList(List<ProductMaster> entities) {
        return entities.stream()
                .map(entity -> {
                    ProductDTO dto = new ProductDTO();
                    dto.setId(entity.getId());
                    dto.setName(entity.getName());
                    dto.setCategoryId(entity.getCategoryId());
                    dto.setCategoryChildId(entity.getCategoryChildId());
                    dto.setSimpleImg(entity.getSimpleImg());
                    dto.setDetailImg(entity.getDetailImg());
                    dto.setPrice(entity.getPrice());
                    dto.setDetail(dtoUtil.stringToMapping(entity.getDetail()));
                    dto.setDefaultVariant(entity.getDefaultVariantId());
                    dto.setAmount(entity.getAmount());
                    dto.setReg(entity.getReg());

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public static ProductDTO productToDTO(ProductMaster entity) {
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCategoryId(entity.getCategoryId());
        dto.setCategoryChildId(entity.getCategoryChildId());
        dto.setSimpleImg(entity.getSimpleImg());
        dto.setDetailImg(entity.getDetailImg());
        dto.setPrice(entity.getPrice());
        dto.setDetail(dtoUtil.stringToMapping(entity.getDetail()));
        dto.setDefaultVariant(entity.getDefaultVariantId());
        dto.setAmount(entity.getAmount());
        dto.setReg(entity.getReg());
        return dto;
    }

    public static List<ProductImgDTO> productImgDTOList(List<ProductImg> entities) {
        return entities.stream()
                .map(entity -> {
                    ProductImgDTO dto = new ProductImgDTO();
                    dto.setId(entity.getId());
                    dto.setImgPath(entity.getImgPath());

                    return dto;
                })
                .collect(Collectors.toList());
    }

//    public static List<ProductReviewDTO> productReviewDTOList(List<ProductReview> entities) {
//        return entities.stream()
//                .map(entity -> {
//                    ProductReviewDTO dto = new ProductReviewDTO();
//                    dto.setId(entity.getId());
//                    dto.setWriter(entity.getWriter());
//                    dto.setContent(entity.getContent());
//                    dto.setProductId(entity.getProductId().getId());
//                    dto.setUserImgs(dtoUtil.stringToMapping(entity.getUserImgs()));
//                    dto.setRecommendCount(entity.getRecommendCount());
//                    dto.setReviewType(entity.getReviewType());
//                    dto.setProductVariantId(entity.getProductVariant().getId());
//                    dto.setRecommendList(entity.getRecommendList());
//
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }

    public static List<ProductVariantDTO> productVariantDTOList(List<ProductVariant> entities) {
        return entities.stream()
                .map(entity -> {
                    ProductVariantDTO dto = new ProductVariantDTO();
                    dto.setId(entity.getId());
                    dto.setName(entity.getName());
                    dto.setPrice(entity.getPrice());
                    dto.setDetail(entity.getDetail());
                    dto.setAmount(entity.getAmount());
                    dto.setSimpleImg(entity.getSimpleImg());
                    dto.setDetailImg(entity.getDetailImg());
                    dto.setMasterId(entity.getMasterId().getId());

                    return dto;
                })
                .collect(Collectors.toList());
    }

}
