package fashionmanager.kim.develop;

import fashionmanager.kim.develop.dto.ReportDTO;
import fashionmanager.kim.develop.service.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @DisplayName("신고 목록 조회 테스트")
    @Test
    public void selectReportsTest() {
        Assertions.assertDoesNotThrow(
                () -> {
                    List<ReportDTO> reports = reportService.selectReports();
                    reports.forEach(System.out::println);
                }
        );
    }

    @DisplayName("신고 추가 테스트")
    @Test
    public void getReportsTest() {
        ReportDTO insertReportDTO = new ReportDTO();
        insertReportDTO.setReportDate(LocalDateTime.of(2025,9,25,10,15,0));
        insertReportDTO.setReportContent("신고 내용");
        insertReportDTO.setReportState("대기");
        insertReportDTO.setReportCategoryNum(4);
        insertReportDTO.setFashionPostNum(5);

        int result = reportService.insertReport(insertReportDTO);
        Assertions.assertEquals(1,result);
    }

    @DisplayName("신고 삭제 테스트")
    @Test
    void testDeleteReports(){
        int num = 5;
        int result = reportService.deleteReport(num);
        Assertions.assertEquals(1,result);
    }

    @DisplayName("신고 상태 변경 테스트")
    @Test
    void deleteReportsTest(){
        int num = 9;
        String state = "처리중";
        int result = reportService.updateReportState(num, state);
        
        Assertions.assertEquals(1,result);

    }
}
