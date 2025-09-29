package fashionmanager.kim.develop;

import fashionmanager.kim.develop.dto.MessageCategoryDTO;
import fashionmanager.kim.develop.dto.PhotoCategoryDTO;
import fashionmanager.kim.develop.dto.ReportCategoryDTO;
import fashionmanager.kim.develop.dto.ReviewCategoryDTO;
import fashionmanager.kim.develop.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @DisplayName("후기 카테고리 목록 조회 테스트")
    @Test
    void testSelectAllReviewCategories() {
        Assertions.assertDoesNotThrow(
                () -> {
                    List<ReviewCategoryDTO> category = categoryService.selectAllReviewCategories();
                    category.forEach(System.out::println);
                }
        );
    }

    @DisplayName("신고 카테고리 목록 조회 테스트")
    @Test
    void testSelectAllReportCategories() {
        Assertions.assertDoesNotThrow(
                () -> {
                    List<ReportCategoryDTO> category = categoryService.selectAllReportCategories();
                    category.forEach(System.out::println);
                }
        );
    }

    @DisplayName("쪽지 카테고리 목록 조회 테스트")
    @Test
    void testSelectAllMessageCategories() {
        Assertions.assertDoesNotThrow(
                () -> {
                    List<MessageCategoryDTO> category = categoryService.selectAllMessageCategories();
                    category.forEach(System.out::println);
                }
        );
    }

    @DisplayName("사진 카테고리 목록 조회 테스트 ")
    @Test
    void testSelectAllPhotoCategories() {
        Assertions.assertDoesNotThrow(
                () -> {
                    List<PhotoCategoryDTO> category = categoryService.selectAllPhotoCategories();
                    category.forEach(System.out::println);
                }
        );
    }

    @DisplayName("후기 카테고리 요소 추가 테스트")
    @Test
    void testInsertReviewCategory() {
        String name = "아우터";

        int result = categoryService.insertReviewCategory(name);
        Assertions.assertTrue(1 == result);
    }

    @DisplayName("신고 카테고리 요소 추가 테스트")
    @Test
    void testInsertReportCategory() {
        String name = "인플루언서";

        int result = categoryService.insertReportCategory(name);
        Assertions.assertTrue(1 == result);
    }

    @DisplayName("메세지 카테고리 요소 추가 테스트")
    @Test
    void testInsertMessageCategory() {
        String name = "조언";

        int result = categoryService.insertMessageCategory(name);
        Assertions.assertTrue(1 == result);
    }

    @DisplayName("사진 카테고리 요소 추가 테스트")
    @Test
    void testInsertPhotoCategory() {
        String name = "테스트 요소";
        int result = categoryService.insertPhotoCategory(name);
        Assertions.assertTrue(1 == result);
    }

    @DisplayName("후기 카테고리 요소 수정 테스트")
    @ParameterizedTest
    @CsvSource({"6,신발", "1, 패션쇼", "2, 패션 이벤트"})
    void testUpdateReviewCategory(int num, String name) {
        ReviewCategoryDTO testReviewCategoryDTO = new ReviewCategoryDTO();
        testReviewCategoryDTO.setReviewCategoryNum(num);
        testReviewCategoryDTO.setReviewCategoryName(name);

        int result = categoryService.updateReviewCategory(testReviewCategoryDTO);
        Assertions.assertTrue(1 == result);
    }

    @DisplayName("신고 카테고리 요소 수정 테스트")
    @ParameterizedTest
    @CsvSource({"1,패션 게시물1", "2,패션 게시물2"})
    void testUpdateReportCategory(int num, String name) {
        ReportCategoryDTO testReportCategoryDTO = new ReportCategoryDTO();
        testReportCategoryDTO.setReportCategoryNum(num);
        testReportCategoryDTO.setReportCategoryName(name);

        int result = categoryService.updateReportCategory(testReportCategoryDTO);
        Assertions.assertTrue(1 == result);
    }

    @DisplayName("쪽지 카테고리 요소 수정 테스트")
    @ParameterizedTest
    @CsvSource({"1,요청사항", "2,문의"})
    void testUpdateMessageCategory(int num, String name) {
        MessageCategoryDTO testMessageCategoryDTO = new MessageCategoryDTO();
        testMessageCategoryDTO.setMessageCategoryNum(num);
        testMessageCategoryDTO.setMessageCategoryName(name);

        int result = categoryService.updateMessageCategory(testMessageCategoryDTO);
        Assertions.assertTrue(1 == result);
    }

    @DisplayName("사진 카테고리 요소 수정 테스트")
    @ParameterizedTest
    @CsvSource({"1,카테고리요소1", "2,카테고리요소2"})
    void testUpdatePhotoCategory(int num, String name) {
        PhotoCategoryDTO testPhotoCategoryDTO = new PhotoCategoryDTO();
        testPhotoCategoryDTO.setPhotoCategoryNum(num);
        testPhotoCategoryDTO.setPhotoCategoryName(name);

        int result = categoryService.updatePhotoCategory(testPhotoCategoryDTO);
        Assertions.assertTrue(1 == result);
    }

    @DisplayName("후기 카테고리 요소 삭제 테스트")
    @Test
    void testDeleteReviewCategory() {
        int num = 1;
        int result = categoryService.deleteReviewCategory(num);
        Assertions.assertTrue(1 == result);
    }

    @DisplayName("신고 카테고리 요소 삭제 테스트")
    @Test
    void testDeleteReportCategory() {
        int num = 1;
        int result = categoryService.deleteReportCategory(num);
        Assertions.assertTrue(1 == result);
    }

    @DisplayName("쪽지 카테고리 요소 삭제 테스트")
    @Test
    void testDeleteMessageCategory() {
        int num = 2;
        int result = categoryService.deleteMessageCategory(num);
        Assertions.assertTrue(1 == result);
    }

    @DisplayName("사진 카테고리 요소 삭제 테스트")
    @Test
    void testDeletePhotoCategory() {
        int num = 9;
        int result = categoryService.deletePhotoCategory(num);
        Assertions.assertTrue(1 == result);
    }
}
