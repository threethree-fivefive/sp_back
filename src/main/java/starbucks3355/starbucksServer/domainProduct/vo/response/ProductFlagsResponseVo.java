package starbucks3355.starbucksServer.domainProduct.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFlagsResponseVo {
	private Boolean isLiked;
	private Boolean isNew;
	private Boolean isBest;
}