package fashionmanager.kim.develop.controller;

import fashionmanager.kim.develop.dto.HashTagAndPostDTO;
import fashionmanager.kim.develop.dto.HashTagDTO;
import fashionmanager.kim.develop.service.HashTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/hashtag")
public class HashTagController {

    private final HashTagService hs;

    @Autowired
    public HashTagController(HashTagService hashTagService){
        this.hs = hashTagService;
    }

    @GetMapping("/selecthashtag")
    public ResponseEntity<List<HashTagDTO>> selectHashTags() {
        List<HashTagDTO> hashTagList = hs.selectHashTags();
        for (HashTagDTO hashTagDTO : hashTagList) {
            log.info("hashTagDTO: {}", hashTagDTO);
        }
        return ResponseEntity.ok(hashTagList);
    }

    @GetMapping("/selecthashtagandpost")
    public ResponseEntity<List<HashTagAndPostDTO>> selectHashTagsAndPosts() {
        List<HashTagAndPostDTO> hashTagAndPostList = hs.selectHashTagsAndPosts();
        for (HashTagAndPostDTO hashTagAndPostDTO : hashTagAndPostList) {
            log.info("hashTagAndPostDTO: {}", hashTagAndPostDTO);
        }
        return ResponseEntity.ok(hashTagAndPostList);
    }

    @PostMapping("/inserthashtag")
    public ResponseEntity<String> insertHashTag(String insertHashTagName) {
        List<HashTagDTO> hashTagList = hs.selectHashTags();
        for (HashTagDTO hashTagDTO : hashTagList) {
            if(hashTagDTO.getHashTagName().equals(insertHashTagName)){
                log.info("새로운 해시태그 등록에 실패했습니다.");
                return ResponseEntity.ok("새로운 해시태그 등록에 실패했습니다.");
            }
        }

        int result = hs.insertHashTag(insertHashTagName);
        if(result == 1){
            log.info("새로운 해시태그 요소가 등록되었습니다.");
            return ResponseEntity.ok("새로운 해시태그 요소가 등록되었습니다.");
        }else{
            log.info("새로운 해시태그 등록에 실패했습니다.");
            return ResponseEntity.ok("새로운 해시태그 등록에 실패했습니다.");
        }
    }

    @PostMapping("/updatehashtag")
    public ResponseEntity<String> updateHashTag(@RequestBody HashTagDTO updateHashTagDTO){
        List<HashTagDTO> hashTagList = hs.selectHashTags();
        for (HashTagDTO hashTagDTO : hashTagList) {
            if(hashTagDTO.getHashTagName().equals(updateHashTagDTO.getHashTagName())){
                log.info("해시태그 수정에 실패했습니다.");
                return ResponseEntity.ok("해시태그 수정에 실패했습니다.");
            }
        }

        int result = hs.updateHashTag(updateHashTagDTO);
        if(result == 1){
            log.info("해시태그 요소가 수정되었습니다.");
            return ResponseEntity.ok("해시태그 요소가 수정되었습니다.");
        }else{
            log.info("해시태그 수정에 실패했습니다.");
            return ResponseEntity.ok("해시태그 수정에 실패했습니다.");
        }
    }

    @PostMapping("/deletehashtag")
    public ResponseEntity<String> deleteHashTag(int deleteHashTagNum){
        int result = hs.deleteHashTag(deleteHashTagNum);
        if(result == 1){
            log.info("해시태그 요소가 삭제 되었습니다.");
            return ResponseEntity.ok("해시태그 요소가 삭제 되었습니다.");
        }else{
            log.info("해시태그 요소가 삭제에 실패했습니다.");
            return ResponseEntity.ok("해시태그 요소가 삭제에 실패했습니다.");
        }
    }
}
