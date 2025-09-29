package fashionmanager.baek.develop.service;

import fashionmanager.baek.develop.dto.FashionRegistRequestDTO;
import fashionmanager.baek.develop.dto.SelectAllFashionPostDTO;
import fashionmanager.kim.develop.dto.BlacklistDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FashionPostServiceTest {

    @Autowired
    private FashionPostService fashionPostService;

    @DisplayName("패션 게시판 조회 테스트")
    @Test
    void testSelectFashionPost() {
        Assertions.assertDoesNotThrow(
                () -> {
                    List<SelectAllFashionPostDTO> fashionPosts = fashionPostService.getPostList();
                    fashionPosts.forEach(System.out::println);
                }
        );
    }

}