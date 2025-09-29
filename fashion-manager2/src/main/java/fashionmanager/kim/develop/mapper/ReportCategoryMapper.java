package fashionmanager.kim.develop.mapper;

import fashionmanager.kim.develop.dto.ReportCategoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportCategoryMapper {
    List<ReportCategoryDTO> selectAllReportCategories();
}
