package web.ssa.repository.products;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import web.ssa.entity.products.ProductMaster;
import java.util.*;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ProductMaster> searchByDynamicFilter(Map<String, List<String>> filterMap) {
        StringBuilder sql = new StringBuilder("SELECT * FROM product_master WHERE amount != -1 AND detail IS NOT NULL");
        Map<String, Object> params = new HashMap<>();
        int idx = 0;
        for (String key : filterMap.keySet()) {
            List<String> values = filterMap.get(key);
            if (values != null && !values.isEmpty()) {
                sql.append(" AND (");
                for (int i = 0; i < values.size(); i++) {
                    if (i > 0)
                        sql.append(" OR ");
                    sql.append("JSON_UNQUOTE(JSON_EXTRACT(detail, '$.\"기본\".\"")
                            .append(key)
                            .append("\"')) LIKE :val")
                            .append(idx);
                    params.put("val" + idx, "%" + values.get(i) + "%");
                    idx++;
                }
                sql.append(")");
            }
        }
        Query query = em.createNativeQuery(sql.toString(), ProductMaster.class);
        for (String p : params.keySet()) {
            query.setParameter(p, params.get(p));
        }
        return query.getResultList();
    }
}