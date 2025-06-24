package web.ssa.enumf;

import lombok.Getter;

@Getter
public enum ReviewType {
    REVIEW(1),
    QUESTION(2);

    private final int reviewType;

    ReviewType(int reviewType) {
        this.reviewType = reviewType;
    }
}
