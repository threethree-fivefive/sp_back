package starbucks3355.starbucksServer.common.entity;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

	/**
	 * 200: 요청 성공
	 **/
	SUCCESS(HttpStatus.OK, true, 200, "요청에 성공하였습니다."),

	/**
	 * 400 : security 에러
	 */
	WRONG_JWT_TOKEN(HttpStatus.UNAUTHORIZED, false, 401, "다시 로그인 해주세요"),
	NO_SIGN_IN(HttpStatus.UNAUTHORIZED, false, 402, "로그인을 먼저 진행해주세요"),
	NO_ACCESS_AUTHORITY(HttpStatus.FORBIDDEN, false, 403, "접근 권한이 없습니다"),
	DISABLED_USER(HttpStatus.FORBIDDEN, false, 404, "비활성화된 계정입니다. 계정을 복구하시겠습니까?"),
	FAILED_TO_RESTORE(HttpStatus.INTERNAL_SERVER_ERROR, false, 405, "계정 복구에 실패했습니다. 관리자에게 문의해주세요."),

	// Shipping
	SHIPPING_ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, false, 406, "배송지 정보를 찾을 수 없습니다."),
	DUPLICATE_ADDRESS(HttpStatus.CONFLICT, false, 407, "이미 존재하는 배송지입니다."),
	COUNT_OVER(HttpStatus.BAD_REQUEST, false, 408, "최대 개수를 초과하였습니다."),
	COUNT_OVER_20(HttpStatus.BAD_REQUEST, false, 416, "장바구니에는 20개의 품목까지 등록가능합니다."),
	COUNT_UNDER_ONE(HttpStatus.BAD_REQUEST, false, 417, "장바구니에는 20개의 품목까지 등록가능합니다."),
	SHIPPING_ADDRESS_ENROLL_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, false, 409, "배송지 등록에 실패했습니다."),
	SHIPPING_ADDRESS_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, false, 410, "배송지 삭제에 실패했습니다."),
	SHIPPING_ADDRESS_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, false, 411, "배송지 수정에 실패했습니다."),
	SHIPPING_ADDRESS_NOT_EXIST(HttpStatus.NOT_FOUND, false, 412, "존재하지 않는 배송지입니다."),
	SHIPPING_ID_NOT_EXIST(HttpStatus.NOT_FOUND, false, 413, "존재하지 않는 배송지 ID입니다."),
	BASE_ADDRESS_EXIST(HttpStatus.BAD_REQUEST, false, 414, "기본 배송지가 이미 존재합니다."),
	DUPLICATE_ID(HttpStatus.BAD_REQUEST, false, 415, "id 값이 중복됩니다."),
	WRONG_FILE_TYPE(HttpStatus.BAD_REQUEST, false, 418, "잘못된 파일 형식입니다."),
	SHIPPING_UUID_NOT_EXIST(HttpStatus.NOT_FOUND, false, 419, "존재하지 않는 배송지 UUID입니다."),

	/**
	 * 900: 기타 에러
	 */
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, 900, "Internal server error"),
	SSE_SEND_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, false, 901, "알림 전송에 실패하였습니다."),
	SAVE_MEDIA_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, false, 902, "S3 미디어 등록에 실패하였습니다."),
	DELETE_MEDIA_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, false, 903, "S3 미디어 삭제에 실패하였습니다."),

	/**
	 * 2000: users service error
	 */
	// token
	TOKEN_NOT_VALID(HttpStatus.UNAUTHORIZED, false, 2001, "토큰이 유효하지 않습니다."),

	// Users
	DUPLICATED_USER(HttpStatus.CONFLICT, false, 2101, "이미 가입된 멤버입니다."),
	DUPLICATED_EMAIL(HttpStatus.CONFLICT, false, 2101, "이미 존재하는 이메일입니다."),
	FAILED_TO_LOGIN(HttpStatus.UNAUTHORIZED, false, 2102, "아이디 또는 패스워드를 다시 확인하세요."),
	DUPLICATED_SOCIAL_USER(HttpStatus.CONFLICT, false, 2103, "이미 소셜 연동된 계정입니다."),
	DUPLICATED_SOCIAL_PROVIDER_USER(HttpStatus.CONFLICT, false, 2104, "계정에 동일한 플랫폼이 이미 연동되어있습니다"),
	NO_EXIST_USER(HttpStatus.NOT_FOUND, false, 2105, "존재하지 않는 멤버 정보입니다."),
	PASSWORD_SAME_FAILED(HttpStatus.BAD_REQUEST, false, 2106, "현재 사용중인 비밀번호 입니다."),
	PASSWORD_CONTAIN_NUM_FAILED(HttpStatus.BAD_REQUEST, false, 2107, "휴대폰 번호를 포함한 비밀번호 입니다."),
	PASSWORD_MATCH_FAILED(HttpStatus.BAD_REQUEST, false, 2108, "패스워드를 다시 확인해주세요."),
	NO_SUPPORTED_PROVIDER(HttpStatus.BAD_REQUEST, false, 2109, "지원하지 않는 플랫폼입니다"),
	DUPLICATED_NICKNAME(HttpStatus.CONFLICT, false, 2010, "이미 사용중인 닉네임입니다."),
	SAME_NICKNAME(HttpStatus.CONFLICT, false, 2011, "현재 사용중인 닉네임입니다."),
	INVALID_EMAIL_ADDRESS(HttpStatus.BAD_REQUEST, false, 2012, "이메일을 다시 확인해주세요."),

	// Pet
	NO_EXIST_CATEGORY(HttpStatus.NOT_FOUND, false, 2401, "존재하지 않는 카테고리입니다."),

	// Interest
	NO_EXIST_INTEREST(HttpStatus.NOT_FOUND, false, 2501, "존재하지 않는 관심사입니다."),

	/**
	 * 3000: product service error
	 */

	// Shorts
	NO_EXIST_PRODUCT(HttpStatus.NOT_FOUND, false, 3001, "존재하지 않는 상품입니다"),
	NO_EXIST_OPTION(HttpStatus.NOT_FOUND, false, 3002, "존재하지 않는 옵션입니다"),
	NO_EXIST_DISCOUNT(HttpStatus.NOT_FOUND, false, 3004, "할인 정책이 존재하지 않습니다"),
	FAILED_TO_ADD_REVIEWS(HttpStatus.INTERNAL_SERVER_ERROR, false, 3003, "리뷰 등록에 실패했습니다."),
	NO_EXIST_REVIEW(HttpStatus.NOT_FOUND, false, 3005, "해당 리뷰가 존재하지 않습니다."),
	NO_EXIST_PRODUCT_DETAIL(HttpStatus.NOT_FOUND, false, 3006, "해당 상품의 상세정보가 존재하지 않습니다."),
	FAILED_TO_ADD_S3(HttpStatus.INTERNAL_SERVER_ERROR, false, 3007, "AWS S3에 이미지 저장을 실패했습니다."),
	NO_EXIST_S3_IMAGE(HttpStatus.NOT_FOUND, false, 3008, "AWS S3에 해당 이미지가 존재하지 않습니다."),
	NO_EXIST_IMAGE(HttpStatus.NOT_FOUND, false, 3009, "해당 이미지가 존재하지 않습니다."),
	NO_EXIST_TAG(HttpStatus.NOT_FOUND, false, 3010, "태그 정보가 존재하지 않습니다."),
	NO_EXIST_PROMOTION(HttpStatus.NOT_FOUND, false, 3011, "해당 기획전 정보가 존재하지 않습니다."),
	NO_EXIST_RECENTLY(HttpStatus.NOT_FOUND, false, 3012, "최근 본 정보가 존재하지 않습니다."),
	ALREADY_EXIST_MAIN_IMAGE(HttpStatus.BAD_REQUEST, false, 3013, "개체에 대한 메인이미지가 이미 존재합니다."),

	/**
	 * 4000: comment service error
	 */

	// Comment
	NO_EXIST_COMMENT(HttpStatus.NOT_FOUND, false, 4001, "존재하지 않는 댓글입니다"),
	NO_DELETE_COMMENT_AUTHORITY(HttpStatus.BAD_REQUEST, false, 4002, "댓글 삭제 권한이 없습니다"),
	NO_DELETE_RE_COMMENT_AUTHORITY(HttpStatus.BAD_REQUEST, false, 4003, "대댓글 삭제 권한이 없습니다"),
	NO_EXIST_RE_COMMENT(HttpStatus.NOT_FOUND, false, 4003, "존재하지 않는 대댓글입니다"),
	NO_EXIST_PIN_AUTHORITY(HttpStatus.BAD_REQUEST, false, 4004, "고정 권한이 없습니다"),

	/**
	 * 5000: notification service error
	 */

	// Notification
	NO_EXIST_NOTIFICATION_SETTING(HttpStatus.NOT_FOUND, false, 5001, "유저의 알림 설정이 존재하지 않습니다."),
	EXIST_NOTIFICATION_SETTING(HttpStatus.BAD_REQUEST, false, 5002, "유저의 알림 설정이 이미 존재합니다."),
	NO_EXIST_NOTIFICATION(HttpStatus.NOT_FOUND, false, 5003, "존재하지 않는 알림입니다."),
	CANNOT_SHARE(HttpStatus.BAD_REQUEST, false, 5004, "공유할 수 없는 유저입니다."),

	/**
	 * 6000: gpt-api error
	 */
	// Gpt
	GPT_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, 6001, "GPT API 호출에 실패했습니다."),
	;

	/**
	 * 7000: s3 error
	 */
	// S3

	private final HttpStatusCode httpStatusCode;
	private final boolean isSuccess;
	private final int code;
	private final String message;
}