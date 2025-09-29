package fashionmanager.kim.develop;

import fashionmanager.kim.develop.dto.BlacklistDTO;
import fashionmanager.kim.develop.dto.ReviewCategoryDTO;
import fashionmanager.kim.develop.service.BlacklistService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class BlacklistServiceTest {

    @Autowired
    private BlacklistService blacklistService;

    @DisplayName("블랙리스트 조회 테스트")
    @Test
    void testSelectBlacklist() {
        Assertions.assertDoesNotThrow(
                () -> {
                    List<BlacklistDTO> blacklist = blacklistService.selectBlacklist();
                    blacklist.forEach(System.out::println);
                }
        );
    }

    @DisplayName("블랙리스트 추가 테스트")
    @Test
    void testInsertBlacklist(){
        BlacklistDTO blacklistDTO = new BlacklistDTO();
        blacklistDTO.setBlacklistReason("블랙리스트 사유");
        blacklistDTO.setBlacklistStartDate(LocalDateTime.now());
        blacklistDTO.setBlacklistExpDate(LocalDateTime.now().plusMonths(3));
        blacklistDTO.setBlacklistMemberNum(5);

        int result = blacklistService.insertBlacklist(blacklistDTO);
        Assertions.assertEquals(1,result);
    }

    @DisplayName("블랙리스트 삭제 테스트")
    @Test
    void testDeleteBlacklist(){
        int result = blacklistService.deleteBlacklist(2);
        Assertions.assertTrue(1 == result);
    }
}
