package fashionmanager.lee.develop.mapper;

import fashionmanager.lee.develop.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {

    List<CommentDTO> findCommentsByPost(@Param("postType") String postType, @Param("postNum") Integer postNum);

    Optional<CommentDTO> findCommentByNum(@Param("commentNum") Integer commentNum);
}