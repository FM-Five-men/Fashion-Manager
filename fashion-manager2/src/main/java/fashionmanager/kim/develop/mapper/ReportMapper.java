package fashionmanager.kim.develop.mapper;

import fashionmanager.kim.develop.dto.ReportDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {

    List<ReportDTO> selectReports();
}
