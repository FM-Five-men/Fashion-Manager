package fashionmanager.kim.develop.service;

import fashionmanager.kim.develop.dto.MessageCategoryDTO;
import fashionmanager.kim.develop.dto.PhotoCategoryDTO;
import fashionmanager.kim.develop.dto.ReportCategoryDTO;
import fashionmanager.kim.develop.dto.ReviewCategoryDTO;
import fashionmanager.kim.develop.entity.MessageCategory;
import fashionmanager.kim.develop.entity.PhotoCategory;
import fashionmanager.kim.develop.entity.ReportCategory;
import fashionmanager.kim.develop.entity.ReviewCategory;
import fashionmanager.kim.develop.mapper.MessageCategoryMapper;
import fashionmanager.kim.develop.mapper.PhotoCategoryMapper;
import fashionmanager.kim.develop.mapper.ReportCategoryMapper;
import fashionmanager.kim.develop.mapper.ReviewCategoryMapper;
import fashionmanager.kim.develop.repository.MessageCategoryRepository;
import fashionmanager.kim.develop.repository.PhotoCategoryRepository;
import fashionmanager.kim.develop.repository.ReportCategoryRepository;
import fashionmanager.kim.develop.repository.ReviewCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final ReviewCategoryMapper reviewCategoryMapper;
    private final ReportCategoryMapper reportCategoryMapper;
    private final MessageCategoryMapper messageCategoryMapper;
    private final PhotoCategoryMapper photoCategoryMapper;

    private final ReviewCategoryRepository reviewCategoryRepository;
    private final ReportCategoryRepository reportCategoryRepository;
    private final MessageCategoryRepository messageCategoryRepository;
    private final PhotoCategoryRepository photoCategoryRepository;

    @Autowired
    public CategoryService(ReviewCategoryMapper categoryMapper, ReportCategoryMapper reportCategoryMapper, MessageCategoryMapper messageCategoryMapper, PhotoCategoryMapper photoCategoryMapper,
                           ReviewCategoryRepository reviewCategoryRepository, ReportCategoryRepository reportCategoryRepository, MessageCategoryRepository messageCategoryRepository, PhotoCategoryRepository photoCategoryRepository) {
        this.reviewCategoryMapper = categoryMapper;
        this.reportCategoryMapper = reportCategoryMapper;
        this.messageCategoryMapper = messageCategoryMapper;
        this.photoCategoryMapper = photoCategoryMapper;

        this.reviewCategoryRepository = reviewCategoryRepository;
        this.reportCategoryRepository = reportCategoryRepository;
        this.messageCategoryRepository = messageCategoryRepository;
        this.photoCategoryRepository = photoCategoryRepository;
    }

    public List<ReviewCategoryDTO> selectAllReviewCategories() {
        return reviewCategoryMapper.selectAllReviewCategories();
    }

    public int insertReviewCategory(String insertReviewCategoryName){

        if(insertReviewCategoryName == null || insertReviewCategoryName.isEmpty()){
            return 0;
        }
        int num = reviewCategoryRepository.findMaxNum() + 1;
        String name = insertReviewCategoryName;
        reviewCategoryRepository.save(new ReviewCategory(num, name));
        return 1;
    }

    public List<ReportCategoryDTO> selectAllReportCategories() {
        return reportCategoryMapper.selectAllReportCategories();
    }

    public int insertReportCategory(String insertReportCategoryName) {

        if(insertReportCategoryName == null || insertReportCategoryName.isEmpty()){
            return 0;
        }
        int num = reportCategoryRepository.findMaxNum() + 1;
        String name = insertReportCategoryName;
        reportCategoryRepository.save(new ReportCategory(num, name));
        return 1;
    }

    public List<MessageCategoryDTO> selectAllMessageCategories() {
        return messageCategoryMapper.selectAllMessageCategories();
    }

    public int insertMessageCategory(String insertMessageCategoryName) {
        if(insertMessageCategoryName == null || insertMessageCategoryName.isEmpty()){
            return 0;
        }
        int num = messageCategoryRepository.findMaxNum() + 1;
        String name = insertMessageCategoryName;
        messageCategoryRepository.save(new MessageCategory(num, name));
        return 1;
    }

    @Transactional
    public int updateReviewCategory(ReviewCategoryDTO reviewCategoryDTO) {
        boolean check1 = reviewCategoryDTO.getReviewCategoryNum() == 0;
        boolean check2 = reviewCategoryDTO.getReviewCategoryName().isEmpty() || reviewCategoryDTO.getReviewCategoryName() == null;
        if(check1 || check2){
            return 0;
        }

        int result = reviewCategoryRepository.updateReviewCategory(reviewCategoryDTO.getReviewCategoryNum(), reviewCategoryDTO.getReviewCategoryName());
        if(result == 1){
            return 1;
        }else{
            return 0;
        }

    }

    @Transactional
    public int updateReportCategory(ReportCategoryDTO reportCategoryDTO) {
        boolean check1 = reportCategoryDTO.getReportCategoryNum() == 0;
        boolean check2 = reportCategoryDTO.getReportCategoryName().isEmpty() || reportCategoryDTO.getReportCategoryName() == null;
        if(check1 || check2){
            return 0;
        }
        int result = reportCategoryRepository.updateReportCategory(reportCategoryDTO.getReportCategoryNum(), reportCategoryDTO.getReportCategoryName());
        if(result == 1){
            return 1;
        }else{
            return 0;
        }
    }

    @Transactional
    public int updateMessageCategory(MessageCategoryDTO messageCategoryDTO) {
        boolean check1 = messageCategoryDTO.getMessageCategoryNum() == 0;
        boolean check2 = messageCategoryDTO.getMessageCategoryName().isEmpty() || messageCategoryDTO.getMessageCategoryName() == null;
        if(check1 || check2){
            return 0;
        }
        int result = messageCategoryRepository.updateMessageCategory(messageCategoryDTO.getMessageCategoryNum(), messageCategoryDTO.getMessageCategoryName());
        if(result == 1){
            return 1;
        }else{
            return 0;
        }
    }

    @Transactional
    public int deleteReviewCategory(int deleteReviewCategoryNum) {
        boolean check = deleteReviewCategoryNum == 0;
        if(check){
            return 0;
        }
        int result = reviewCategoryRepository.deleteReviewCategory(deleteReviewCategoryNum);
        if(result == 1){
            return 1;
        }else{
            return 0;
        }

    }

    @Transactional
    public int deleteReportCategory(int deleteReportCategoryNum) {
        boolean check = deleteReportCategoryNum == 0;
        if(check){
            return 0;
        }
        int result = reportCategoryRepository.deleteReviewCategory(deleteReportCategoryNum);
        if(result == 1){
            return 1;
        }else{
            return 0;
        }
    }

    @Transactional
    public int deleteMessageCategory(int deleteMessageCategoryNum) {
        boolean check = deleteMessageCategoryNum == 0;
        if(check) {
            return 0;
        }
        int result = messageCategoryRepository.deleteMessageCategory(deleteMessageCategoryNum);
        if(result == 1){
            return 1;
        }else{
            return 0;
        }
    }

    public List<PhotoCategoryDTO> selectAllPhotoCategories() {
        return photoCategoryMapper.selectAllPhotoCategories();
    }

    public int insertPhotoCategory(String insertPhotoCategoryName) {
        if(insertPhotoCategoryName == null || insertPhotoCategoryName.isEmpty()){
            return 0;
        }
        int num = photoCategoryRepository.findMaxNum() + 1;
        String name = insertPhotoCategoryName;
        photoCategoryRepository.save(new PhotoCategory(num,name));
        return 1;
    }

    @Transactional
    public int updatePhotoCategory(PhotoCategoryDTO photoCategoryDTO) {
        boolean check1 = photoCategoryDTO.getPhotoCategoryNum() == 0;
        boolean check2 = photoCategoryDTO.getPhotoCategoryName() == null || photoCategoryDTO.getPhotoCategoryName().isEmpty();
        if(check1 || check2){
            return 0;
        }

        int result = photoCategoryRepository.updatePhotoCategory(photoCategoryDTO.getPhotoCategoryNum(), photoCategoryDTO.getPhotoCategoryName());
        if(result == 1){
            return 1;
        }else{
            return 0;
        }

    }

    @Transactional
    public int deletePhotoCategory(int deletePhotoCategoryNum) {
        boolean check = deletePhotoCategoryNum == 0;
        if(check) {
            return 0;
        }
        int result = photoCategoryRepository.deletePhotoCategory(deletePhotoCategoryNum);
        if(result == 1){
            return 1;
        }else{
            return 0;
        }
    }
}
