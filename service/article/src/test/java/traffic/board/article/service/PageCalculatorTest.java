package traffic.board.article.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageCalculatorTest {

    @Test
    void calculatorPageLimitTest() {
        calculatorPageLimitTest(1L, 30L, 10L, 301L);
        calculatorPageLimitTest(7L, 30L, 10L, 301L);
        calculatorPageLimitTest(10L, 30L, 10L, 301L);
        calculatorPageLimitTest(11L, 30L, 10L, 601L);
        calculatorPageLimitTest(12L, 30L, 10L, 601L);
    }

    void calculatorPageLimitTest(Long page, Long pageSize, Long moveablePageCount, Long expected) {
        Long result = PageCalculator.calculatePageLimit(page, pageSize, moveablePageCount);
        Assertions.assertThat(result).isEqualTo(expected);
    }


}