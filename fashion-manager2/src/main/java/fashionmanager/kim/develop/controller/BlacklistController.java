package fashionmanager.kim.develop.controller;

import fashionmanager.kim.develop.dto.BlacklistDTO;
import fashionmanager.kim.develop.service.BlacklistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/blacklist")
public class BlacklistController {

    private final BlacklistService bs;

    public BlacklistController(BlacklistService blacklistService) {
        this.bs = blacklistService;
    }

    @GetMapping("/selectblacklist")
    public ResponseEntity<List<BlacklistDTO>> selectBlacklist(){
        List<BlacklistDTO> blacklist = bs.selectBlacklist();
        for(BlacklistDTO black : blacklist){
            log.info("BlacklistDTO: {}", black);
        }
        return ResponseEntity.ok(blacklist);
    }

    @PostMapping("/insertblacklist")
    public ResponseEntity<String> insertBlacklist(@RequestBody BlacklistDTO blacklistDTO){
        int result = bs.insertBlacklist(blacklistDTO);
        if(result == 1){
            log.info("블랙리스트 등록을 완료했습니다.");
            return ResponseEntity.ok("블랙리스트 등록을 완료했습니다.");
        }else{
            log.info("블랙리스트 등록에 실패했습니다.");
            return ResponseEntity.ok("블랙리스트 등록에 실패했습니다.");
        }
    }

    @PostMapping("/deleteblacklist")
    public ResponseEntity<String> deleteBlacklist(int blacklistNum){
        int result = bs.deleteBlacklist(blacklistNum);
        if(result == 1){
            log.info("블랙리스트 삭제를 완료했습니다.");
            return ResponseEntity.ok("블랙리스트 삭제를 완료했습니다.");
        }else{
            log.info("블랙리스트 삭제를 실패했습니다.");
            return ResponseEntity.ok("블랙리스트 삭제를 실패했습니다.");
        }
    }
}
