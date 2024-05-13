package com.training.dao.jpaspecifications;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.annotations.CollectionBinder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import com.training.entity.BrandEntity;
import com.training.entity.ProductEntity;

public class ProductJpaSpecs {

	public static Specification<ProductEntity> getSeachProductBySpec(Map<String, Object> searchMap){
		
		return new Specification<ProductEntity>() {
				
			public static final long serialVersion = 1L;

			@Override
			public Predicate toPredicate(Root<ProductEntity> root,
										 CriteriaQuery<?> query,
										 CriteriaBuilder criteriaBuilder) {
				
				List<Predicate> predicates = new ArrayList<Predicate>();
				
				if(searchMap != null) {
					String keyword =  searchMap.get("keyword").toString();
					String priceFrom = searchMap.get("priceFrom").toString();
					String priceTo = searchMap.get("priceTo").toString();
					@SuppressWarnings("unchecked")
					List<String> brandIds = (List<String>) searchMap.get("list");
					Join<ProductEntity, BrandEntity> brandRoot = root.join("brandEntity");
					if(StringUtils.isNotEmpty(keyword)) {
						// brandRoot = root.join("brandEntity");
						predicates.add(criteriaBuilder.or(
								criteriaBuilder.like(root.get("productName"), "%" + keyword + "%"),
								criteriaBuilder.like(brandRoot.get("brandName"), "%" + keyword + "%")
								));
					}
					if(StringUtils.isNotEmpty(priceFrom)) {
						predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), Double.parseDouble(priceFrom)));
					}
					if(StringUtils.isNotEmpty(priceTo)) {
						predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), Double.parseDouble(priceTo)));
					}
					if(!CollectionUtils.isEmpty(brandIds)) {
						List<Predicate> listBrandId = new ArrayList<>();
						for (String brandId : brandIds) {
							listBrandId.add(criteriaBuilder.equal(brandRoot.get("brandId"), Long.parseLong(brandId)));
						}
						predicates.add(criteriaBuilder.or(listBrandId.toArray(new Predicate[listBrandId.size()])));
					}
					
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
}
