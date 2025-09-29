package fashionmanager.kim.develop;

import fashionmanager.kim.develop.dto.HashTagAndPostDTO;
import fashionmanager.kim.develop.dto.HashTagDTO;
import fashionmanager.kim.develop.entity.HashTag;
import fashionmanager.kim.develop.service.HashTagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class HashTagServiceTest {

    @Autowired
    private HashTagService hashTagService;

    @DisplayName("해시태그 목록 조회 테스트")
    @Test
    void testSelectHashTags(){
        Assertions.assertDoesNotThrow(
                ()->{
                    List<HashTagDTO> hashTags = hashTagService.selectHashTags();
                    hashTags.forEach(System.out::println);
                }
        );
    }

    @DisplayName("해시태그와 패션 게시물이 연결된 목록 조회 테스트")
    @Test
    void testSelectHashTagsAndPost(){
        Assertions.assertDoesNotThrow(
                ()->{
                    List<HashTagAndPostDTO> hashTagsAndPosts = hashTagService.selectHashTagsAndPosts();
                    hashTagsAndPosts.forEach(System.out::println);
                }
        );
    }

    @DisplayName("해시태그 요소 등록 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"캐주얼","힙합"})
    void testInsertHashTag(String name){
        int result = hashTagService.insertHashTag(name);
        Assertions.assertTrue(1 == result);
    }

    @DisplayName("해시태그 요소 수정 테스트")
    @ParameterizedTest
    @CsvSource({"1,일상룩","2,오피스패션"})
    void testUpdateHashTag(int num, String name){

        HashTagDTO updateHashTagDTO = new HashTagDTO();
        updateHashTagDTO.setHashTagNum(num);
        updateHashTagDTO.setHashTagName(name);
        int result = hashTagService.updateHashTag(updateHashTagDTO);
        Assertions.assertTrue(1 == result);
    }

    @DisplayName("해시태그 요소 삭제 테스트")
    @Test
    void testDeleteHashTag(){
        int result = hashTagService.deleteHashTag(1);
        Assertions.assertTrue(1 == result);
    }
}
