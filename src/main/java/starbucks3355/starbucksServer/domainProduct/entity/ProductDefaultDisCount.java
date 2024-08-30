package starbucks3355.starbucksServer.domainProduct.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "productOptions")
public class ProductDefaultDisCount {
	@Id
	@JoinColumn(name = "option_id")
	private Long id;
	@OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "option_id", nullable = false)
	private ProductOptions productOptions;
	@Column(nullable = false)
	private int type;
	@Column(nullable = false)
	private int value;
}
