package web.ssa.dto;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int page,           // 현재 페이지 (0-based)
        int size,           // 페이지 크기
        int totalPages,     // 전체 페이지 수
        long totalElements  // 전체 항목 수
) {}

