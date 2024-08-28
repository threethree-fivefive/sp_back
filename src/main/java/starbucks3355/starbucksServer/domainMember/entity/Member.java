package starbucks3355.starbucksServer.domainMember.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Past;
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
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 30)
	private String userId;
	@Column(nullable = false, length = 30)
	private String password;
	@Column(nullable = false, length = 255)
	private String email;
	@Column(nullable = false, length = 30)
	private String phoneNumber;
	@Column(nullable = false)
	@Past // 생년월일이 과거여야 함
	private LocalDate birth;
	@Column(nullable = false, length = 255)
	private String address;
	@Column(nullable = false, length = 10)
	private String gender;
	@Column(length = 30)
	private String nickname;
	private UUID memberUuid;
	private Boolean isMember;
	/** false일 경우 회원 -> 회원 생성 시 값을 false로 부여 */
	@LastModifiedDate
	private LocalDateTime withdrawalTime;
}
