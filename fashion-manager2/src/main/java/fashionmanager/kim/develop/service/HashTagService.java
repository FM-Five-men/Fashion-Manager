package fashionmanager.kim.develop.service;

import fashionmanager.kim.develop.dto.HashTagAndPostDTO;
import fashionmanager.kim.develop.dto.HashTagDTO;
import fashionmanager.kim.develop.entity.HashTag;
import fashionmanager.kim.develop.mapper.HashTagMapper;
import fashionmanager.kim.develop.repository.HashTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HashTagService {
    private final HashTagMapper hashTagMapper;

    private final HashTagRepository hashTagRepository;

    @Autowired
    public HashTagService(HashTagMapper hashTagMapper, HashTagRepository hashTagRepository) {
        this.hashTagMapper = hashTagMapper;
        this.hashTagRepository = hashTagRepository;
    }

    public List<HashTagDTO> selectHashTags() {
        return hashTagMapper.selectHashTags();
    }

    public List<HashTagAndPostDTO> selectHashTagsAndPosts() {
        return hashTagMapper.selectHashTagsAndPosts();
    }

    public int insertHashTag(String insertHashTagName) {
        if(insertHashTagName == null ||  insertHashTagName.isEmpty()){
            return 0;
        }

        int num = hashTagRepository.findMaxNum() + 1;
        String name = insertHashTagName;
        hashTagRepository.save(new HashTag(num, name));
        return 1;
    }

    @Transactional
    public int updateHashTag(HashTagDTO updateHashTagDTO) {
        boolean check1 = updateHashTagDTO.getHashTagNum() == 0;
        boolean check2 = updateHashTagDTO.getHashTagName() == null ||  updateHashTagDTO.getHashTagName().isEmpty();
        if(check1 || check2){
            return 0;
        }

        int result = hashTagRepository.updateHashTag(updateHashTagDTO.getHashTagNum(),updateHashTagDTO.getHashTagName());
        if(result == 1){
            return 1;
        }else{
            return 0;
        }

    }

    @Transactional
    public int deleteHashTag(int deleteHashTagNum) {
        boolean check = deleteHashTagNum == 0;
        if(check){
            return 0;
        }

        int result = hashTagRepository.deleteHashTag(deleteHashTagNum);
        if(result == 1){
            return 1;
        }else{
            return 0;
        }
    }
}
