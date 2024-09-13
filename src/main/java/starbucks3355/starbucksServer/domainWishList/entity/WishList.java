package starbucks3355.starbucksServer.domainWishList.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import starbucks3355.starbucksServer.common.entity.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WishList extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private int limitQuantity;
	private String memberUuid;
	@Builder.Default
	private boolean isChecked = true;
	@Column(length = 100)
	private String productUuid;
	private Integer currentQuantity;

	public void updateCurrentQuantity(int currentQuantity) {
		this.currentQuantity = currentQuantity;
	}
}
