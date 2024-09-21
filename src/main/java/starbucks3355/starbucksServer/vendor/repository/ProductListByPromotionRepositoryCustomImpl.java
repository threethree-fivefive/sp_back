package starbucks3355.starbucksServer.vendor.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import starbucks3355.starbucksServer.vendor.dto.out.ProductListByPromotionResponseDto;
import starbucks3355.starbucksServer.vendor.entity.QProductByPromotionList;

@RequiredArgsConstructor
@Repository
public class ProductListByPromotionRepositoryCustomImpl implements ProductListByPromotionRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<ProductListByPromotionResponseDto> getProductByPromotionList(String promotionUuid) {

		QProductByPromotionList qProductByPromotionList = QProductByPromotionList.productByPromotionList;
		BooleanBuilder builder = new BooleanBuilder();

		// 기획전 uuid를 통해 상품 uuid를 조회
		List<String> productUuidList = jpaQueryFactory
			.select(qProductByPromotionList.productUuid)
			.from(qProductByPromotionList)
			.where(qProductByPromotionList.promotionUuid.eq(promotionUuid))
			.fetch();

		return productUuidList.stream()
			.map(ProductListByPromotionResponseDto::new)
			.toList();
	}

	@Override
	public List<ProductListByPromotionResponseDto> getProductsBySamePromotion(String productUuid) {

		QProductByPromotionList qProductByPromotionList = QProductByPromotionList.productByPromotionList;
		BooleanBuilder builder = new BooleanBuilder();

		// 상품 uuid를 통해 기획전 uuid를 조회
		List<String> promotionUuidList = jpaQueryFactory
			.select(qProductByPromotionList.promotionUuid)
			.from(qProductByPromotionList)
			.where(qProductByPromotionList.productUuid.eq(productUuid))
			.fetch();

		List<String> productUuidList = jpaQueryFactory
			.select(qProductByPromotionList.productUuid)
			.from(qProductByPromotionList)
			.where(qProductByPromotionList.promotionUuid.eq(promotionUuidList.get(0)))
			.fetch();

		// 현재 상품의 uuid를 제외한 상품 목록을 조회
		return productUuidList.stream()
			.filter(uuid -> !uuid.equals(productUuid))
			.map(ProductListByPromotionResponseDto::new)
			.toList();

	}
}
