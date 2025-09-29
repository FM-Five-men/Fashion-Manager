package fashionmanager.kim.develop.controller;

import fashionmanager.kim.develop.dto.InsertReportDTO;
import fashionmanager.kim.develop.dto.ReportDTO;
import fashionmanager.kim.develop.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService rs;

    public ReportController(ReportService reportService) {
        this.rs = reportService;
    }

    @GetMapping("/selectreport")
    public ResponseEntity<List<ReportDTO>> selectReports() {
        List<ReportDTO> list = rs.selectReports();
        for (ReportDTO reportDTO : list) {
            log.info("reportDTO: {}", reportDTO);
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping("/insertreport")
    public ResponseEntity<String> insertReport(@RequestBody ReportDTO reportDTO) {
        int result = rs.insertReport(reportDTO);
        if (result == 1) {
            log.info("신고가 신청되었습니다.");
            return ResponseEntity.ok("신고가 신청되었습니다.");
        }else{
            log.info("신고 신청을 실패하였습니다.");
            return ResponseEntity.ok("신고 신청을 실패하였습니다.");
        }
    }

    @PostMapping("/deletereport")
    public ResponseEntity<String> deleteReport(int reportNum) {
        int result = rs.deleteReport(reportNum);
        if (result == 1) {
            log.info("신고를 삭제하였습니다.");
            return ResponseEntity.ok("신고를 삭제했습니다.");
        }else{
            log.info("신고 삭제에 실패했습니다.");
            return ResponseEntity.ok("신고 삭제에 실패했습니다.");
        }
    }

    @PostMapping("/updatereportstate")
    public ResponseEntity<String> updateReportState(int reportNum, String reportState){
        int result = rs.updateReportState(reportNum, reportState);
        if(result == 1){
            log.info("신고 상태를 변경했습니다.");
            return ResponseEntity.ok("신고 상태를 변경했습니다.");
        }else{
            log.info("신고 상태 변경에 실패했습니다.");
            return ResponseEntity.ok("신고 상태 변경에 실패했습니다.");
        }
    }

}
