package starbucks3355.starbucksServer.domainProduct.dto.response;

import lombok.Builder;
import lombok.Getter;
import starbucks3355.starbucksServer.domainProduct.vo.response.ProductImgResponseVo;

@Getter
@Builder
public class ProductImgResponseDto {
	private String productImgUrl;

	public ProductImgResponseDto(String productImgUrl) {
		this.productImgUrl = productImgUrl;
	}

	public ProductImgResponseVo dtoToResponseVo() {
		return ProductImgResponseVo.builder()
			.productImgUrl(productImgUrl)
			.build();
	}
}