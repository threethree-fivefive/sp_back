package starbucks3355.starbucksServer.category.sevice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import starbucks3355.starbucksServer.category.dto.request.BottomCategoryRequestDto;
import starbucks3355.starbucksServer.category.dto.request.MiddleCategoryRequestDto;
import starbucks3355.starbucksServer.category.dto.request.TopCategoryRequestDto;
import starbucks3355.starbucksServer.category.dto.response.BottomCategoryResponseDto;
import starbucks3355.starbucksServer.category.dto.response.MiddleCategoryResponseDto;
import starbucks3355.starbucksServer.category.dto.response.TopCategoryResponseDto;
import starbucks3355.starbucksServer.category.entity.BottomCategory;
import starbucks3355.starbucksServer.category.entity.MiddleCategory;
import starbucks3355.starbucksServer.category.entity.TopCategory;
import starbucks3355.starbucksServer.category.repository.BottomCategoryRepository;
import starbucks3355.starbucksServer.category.repository.CategoryListRepository;
import starbucks3355.starbucksServer.category.repository.MiddleCategoryRepository;
import starbucks3355.starbucksServer.category.repository.TopCategoryRepository;
import starbucks3355.starbucksServer.common.utils.CategoryCodeGenerator;
import starbucks3355.starbucksServer.domainOrders.repository.OrderRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final TopCategoryRepository topCategoryRepository;
	private final MiddleCategoryRepository middleCategoryRepository;
	private final BottomCategoryRepository bottomCategoryRepository;

	private static final int MAX_CODE_TRIES = 5; // 최대 재시도 횟수
	private final CategoryListRepository categoryListRepository;
	private final OrderRepository orderRepository;

	@Transactional
	@Override
	public void createTopCategory(TopCategoryRequestDto topCategoryRequestDto) {
		if (topCategoryRepository.existsByCategoryName(topCategoryRequestDto.getTopCategoryName())) {
			throw new IllegalArgumentException("이미 존재하는 카테고리입니다.");
		}

		String categoryCode = generateUniqueCategoryCode("TC-");

		// DTO를 엔티티 변환해서 생성된 카테고리 코드 주입
		TopCategory topCategory = topCategoryRequestDto.toEntity(categoryCode);
		try {
			topCategoryRepository.save(topCategory);
			log.info("Top 카테고리 코드: " + topCategory.getCategoryCode());
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("Unexpected error occurred", e);
			throw new RuntimeException("카테고리 생성 중 오류가 발생했습니다.", e);
		}
	}

	@Transactional
	@Override
	public void createMiddleCategory(MiddleCategoryRequestDto middleCategoryRequestDto) {
		if (middleCategoryRepository.existsByCategoryName(middleCategoryRequestDto.getMiddleCategoryName())) {
			throw new IllegalArgumentException("이미 존재하는 카테고리입니다.");
		}

		try { // Top 카테고리 코드가 존재하는지 확인
			TopCategory topCategory = topCategoryRepository.findByCategoryCode(
				middleCategoryRequestDto.getTopCategoryCode()).orElseThrow(
				() -> new IllegalArgumentException("존재하지 않는 Top 카테고리 코드입니다.")
			); //
			String categoryCode = generateUniqueCategoryCode("MC-");

			// Topcategory도 객체화 시키는 이유는 middlecategory가 속할 topcategory를 지정해주는거임
			middleCategoryRepository.save(middleCategoryRequestDto.toEntity(topCategory, categoryCode));
			log.info("Middle 카테고리 코드: " + categoryCode);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("Unexpected error occurred", e);
			throw new RuntimeException("카테고리 생성 중 오류가 발생했습니다.", e);
		}

	}

	@Transactional
	@Override
	public void createBottomCategory(BottomCategoryRequestDto bottomCategoryRequestDto) {
		if (bottomCategoryRepository.existsByCategoryName(bottomCategoryRequestDto.getBottomCategoryName())) {
			throw new IllegalArgumentException("이미 존재하는 카테고리입니다.");
		}
		try { // Middle 카테고리 코드가 존재하는지 확인
			MiddleCategory middleCategory = middleCategoryRepository.findByCategoryCode(
				bottomCategoryRequestDto.getMiddleCategoryCode()).orElseThrow(
				() -> new IllegalArgumentException("존재하지 않는 Middle 카테고리 코드입니다.")
			);

			String categoryCode = generateUniqueCategoryCode("BC-");
			bottomCategoryRepository.save(bottomCategoryRequestDto.toEntity(middleCategory, categoryCode));
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("Unexpected error occurred", e);
			throw new RuntimeException("카테고리 생성 중 오류가 발생했습니다.", e);
		}

	}

	// top 전체 카테고리 조회
	@Override
	// top 카테고리 조회
	// db에 저장 돼 있는 객체를 dto로 변환 시키는 과정
	public List<TopCategoryResponseDto> getTopCategories() {
		return topCategoryRepository.findAll().stream().map(
			topCategory -> TopCategoryResponseDto.builder()
				.id(topCategory.getId())
				.topCategoryName(topCategory.getCategoryName())
				.build()
		).toList();
	}

	@Override
	@Transactional
	// 단일 top 카테고리 조회
	public TopCategoryResponseDto getTopCategoryByCategoryCode(String topCategoryCode) {
		try {
			TopCategory topCategory = topCategoryRepository.findByCategoryCode(topCategoryCode)
				.orElseThrow(() -> new IllegalArgumentException("해당하지 않는 Top 카테고리 코드입니다."));
			// 디비에 있는 값 객체화 시키기
			return TopCategoryResponseDto.builder()
				.id(topCategory.getId())
				.topCategoryName(topCategory.getCategoryName())
				.build();
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("Unexpected error occurred", e);
			throw new RuntimeException("카테고리 조회 중 오류가 발생했습니다.", e);
		}

	}

	// middle 전체 카테고리 조회
	@Override
	@Transactional(readOnly = true) // 임시로 name으로 수정 변수값
	public List<MiddleCategoryResponseDto> getMiddleCategories(String topCategoryName) {
		// 미들 카테고리는 조회할때 탑 카테고리의 코드가 있는지 확인을 해야함
		try {
			TopCategory topCategory = topCategoryRepository.findByCategoryName(topCategoryName)
				.orElseThrow(() -> new IllegalArgumentException("해당하지 않는 Top 카테고리 이름입니다."));
			log.info("topCategory : {}", topCategory);

			List<MiddleCategoryResponseDto> middleCategoryResponseDtos = middleCategoryRepository
				.findByTopCategoryCategoryCode(topCategory.getCategoryCode())
				.stream()
				.map(
					middleCategory -> MiddleCategoryResponseDto.builder()
						.middleCategoryCode(middleCategory.getCategoryCode())
						.middleCategoryDescription(middleCategory.getCategoryDescription())
						.middleCategoryName(middleCategory.getCategoryName())
						.build())
				.collect(Collectors.toList());
			return middleCategoryResponseDtos; // db의 저장된 객체를 dto로 변환해서 반환
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("Unexpected error occurred", e);
			throw new RuntimeException("카테고리 조회 중 오류가 발생했습니다.", e);
		}
	}

	// 단일 middle 카테고리 조회
	@Override
	@Transactional
	public MiddleCategoryResponseDto getMiddleCategoryByCategoryCode(String middleCategoryCode) {
		try {
			MiddleCategory middleCategory = middleCategoryRepository.findByCategoryCode(middleCategoryCode)
				.orElseThrow(() -> new IllegalArgumentException("해당하지 않는 Middle 카테고리 코드입니다."));
			return MiddleCategoryResponseDto.builder()
				.middleCategoryCode(middleCategory.getCategoryCode())
				.middleCategoryDescription(middleCategory.getCategoryDescription())
				.middleCategoryName(middleCategory.getCategoryName())
				.build();
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("Unexpected error occurred", e);
			throw new RuntimeException("카테고리 조회 중 오류가 발생했습니다.", e);
		}
	}

	// bottom 전체 카테고리 조회
	@Override
	@Transactional(readOnly = true) //middleCategoryCode -> middleCategoryName으로 수정 (임시 테스트)
	public List<BottomCategoryResponseDto> getBottomCategories(String middleCategoryCode) {
		try {
			MiddleCategory middleCategory = middleCategoryRepository.findByCategoryCode(middleCategoryCode)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Middle 카테고리 코드입니다."));

			List<BottomCategoryResponseDto> bottomCategoryResponseDtos = bottomCategoryRepository
				.findByMiddleCategoryCategoryCode(middleCategory.getCategoryCode())
				.stream()
				.map(
					bottomCategory -> BottomCategoryResponseDto.builder()
						.bottomCategoryCode(bottomCategory.getCategoryCode())
						.bottomCategoryDescription(bottomCategory.getCategoryDescription())
						.bottomCategoryName(bottomCategory.getCategoryName())
						.build())
				.collect(Collectors.toList());
			return bottomCategoryResponseDtos;

		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("Unexpected error occurred", e);
			throw new RuntimeException("카테고리 조회 중 오류가 발생했습니다.", e);
		}
	}

	@Override
	@Transactional
	public BottomCategoryResponseDto getBottomCategoryByCategoryCode(String bottomCategoryCode) {
		try {
			BottomCategory bottomCategory = bottomCategoryRepository.findByCategoryCode(bottomCategoryCode)
				.orElseThrow(() -> new IllegalArgumentException("해당하지 않는 Bottom 카테고리 코드입니다."));
			return BottomCategoryResponseDto.builder()
				.bottomCategoryCode(bottomCategory.getCategoryCode())
				.bottomCategoryDescription(bottomCategory.getCategoryDescription())
				.bottomCategoryName(bottomCategory.getCategoryName())
				.build();
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.error("Unexpected error occurred", e);
			throw new RuntimeException("카테고리 조회 중 오류가 발생했습니다.", e);
		}
	}

	private String generateUniqueCategoryCode(String prefix) {
		for (int i = 0; i < MAX_CODE_TRIES; i++) {
			String categoryCode = CategoryCodeGenerator.generateCategoryCode(prefix);
			switch (prefix) {
				case "TC-":
					if (!topCategoryRepository.existsByCategoryCode(categoryCode)) {
						return categoryCode;  // 중복이 없으면 코드 반환
					}
					break;
				case "MC-":
					if (!middleCategoryRepository.existsByCategoryCode(categoryCode)) {
						return categoryCode;  // 중복이 없으면 코드 반환
					}
					break;
				case "BC-":
					if (!bottomCategoryRepository.existsByCategoryCode(categoryCode)) {
						return categoryCode;  // 중복이 없으면 코드 반환
					}
					break;
				default:
					throw new IllegalArgumentException("유효하지 않은 카테고리 코드 접두사입니다: " + prefix);
			}

		}
		throw new IllegalStateException("고유한 카테고리 코드를 생성하는 데 실패했습니다.");
	}

}
