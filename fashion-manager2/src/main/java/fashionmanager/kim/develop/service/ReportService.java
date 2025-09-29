package fashionmanager.kim.develop.service;

import fashionmanager.kim.develop.dto.InsertReportDTO;
import fashionmanager.kim.develop.dto.ReportDTO;
import fashionmanager.kim.develop.entity.Report;
import fashionmanager.kim.develop.mapper.ReportMapper;
import fashionmanager.kim.develop.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportService {

    private final ReportMapper reportMapper;

    private final ReportRepository reportRepository;

    public ReportService(ReportMapper reportMapper, ReportRepository reportRepository) {
        this.reportMapper = reportMapper;
        this.reportRepository = reportRepository;
    }

    public List<ReportDTO> selectReports() {
        return reportMapper.selectReports();
    }

    public int insertReport(ReportDTO reportDTO) {
        Report report = new Report();
        int check = reportDTO.getReportCategoryNum();

        if(check==0){
            return 0;
        }

        if(check == 1){
            report.setReportDate(reportDTO.getReportDate());
            report.setReportContent(reportDTO.getReportContent());
            report.setReportState(reportDTO.getReportState());
            report.setReportCategoryNum(reportDTO.getReportCategoryNum());
            report.setFashionPostNum(reportDTO.getFashionPostNum());
            report.setReviewPostNum(null);
            report.setMentoringPostNum(null);
            report.setCommentNum(null);

            reportRepository.save(report);
            return 1;
        }else if(check == 2){
            report.setReportDate(reportDTO.getReportDate());
            report.setReportContent(reportDTO.getReportContent());
            report.setReportState(reportDTO.getReportState());
            report.setReportCategoryNum(reportDTO.getReportCategoryNum());
            report.setFashionPostNum(null);
            report.setReviewPostNum(reportDTO.getReviewPostNum());
            report.setMentoringPostNum(null);
            report.setCommentNum(null);

            reportRepository.save(report);
            return 1;
        }else if(check == 3){
            report.setReportDate(reportDTO.getReportDate());
            report.setReportContent(reportDTO.getReportContent());
            report.setReportState(reportDTO.getReportState());
            report.setReportCategoryNum(reportDTO.getReportCategoryNum());
            report.setFashionPostNum(null);
            report.setReviewPostNum(null);
            report.setMentoringPostNum(reportDTO.getMentoringPostNum());
            report.setCommentNum(null);

            reportRepository.save(report);
            return 1;
        }else{
            report.setReportDate(reportDTO.getReportDate());
            report.setReportContent(reportDTO.getReportContent());
            report.setReportState(reportDTO.getReportState());
            report.setReportCategoryNum(reportDTO.getReportCategoryNum());
            report.setFashionPostNum(null);
            report.setReviewPostNum(null);
            report.setMentoringPostNum(null);
            report.setCommentNum(reportDTO.getCommentNum());

            reportRepository.save(report);
            return 1;
        }
    }

    @Transactional
    public int deleteReport(int reportNum) {
        int result = reportRepository.deleteReport(reportNum);
        if (result == 1) {
            return 1;
        }else{
            return 0;
        }
    }

    @Transactional
    public int updateReportState(int reportNum, String reportState) {
        int result = reportRepository.updateReportState(reportNum, reportState);
        if(result == 1){
            return 1;
        }else{
            return 0;
        }
    }
}
