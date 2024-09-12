package starbucks3355.starbucksServer.domainProduct.dto.response;

import lombok.Builder;
import lombok.Getter;
import starbucks3355.starbucksServer.domainProduct.vo.response.ProductResponseVo;

@Getter
@Builder
public class ProductResponseDto {
	private String productName;
	private String productDescription;
	private String productInfo;

	public ProductResponseDto(
		String productName,
		String productDescription,
		String productInfo
	) {
		this.productName = productName;
		this.productDescription = productDescription;
		this.productInfo = productInfo;
	}

	public ProductResponseVo dtoToResponseVo() {
		return ProductResponseVo.builder()
			.productName(productName)
			.productDescription(productDescription)
			.productInfo(productInfo)
			.build();
	}

}
