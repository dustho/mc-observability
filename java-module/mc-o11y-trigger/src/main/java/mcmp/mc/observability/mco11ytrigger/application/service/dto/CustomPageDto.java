package mcmp.mc.observability.mco11ytrigger.application.service.dto;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomPageDto<T> {

	private List<T> content;
	private Pageable pageable;
	private long totalPages;
	private long totalElements;
	private long numberOfElements;

	public static <T> CustomPageDto<T> of(Page<?> page, Supplier<List<T>> supplier) {
		CustomPageDto<T> dto = new CustomPageDto<>();
		dto.content = supplier.get();
		dto.pageable = page.getPageable();
		dto.totalPages = page.getTotalPages();
		dto.totalElements = page.getTotalElements();
		dto.numberOfElements = page.getNumberOfElements();
		return dto;
	}

	public static <T> CustomPageDto<T> empty() {
		CustomPageDto<T> dto = new CustomPageDto<>();
		dto.content = List.of();
		dto.pageable = Pageable.ofSize(1);
		dto.totalPages = 1;
		dto.totalElements = 0;
		dto.numberOfElements = 0;
		return dto;
	}
}
