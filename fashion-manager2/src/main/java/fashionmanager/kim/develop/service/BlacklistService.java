package fashionmanager.kim.develop.service;

import fashionmanager.kim.develop.dto.BlacklistDTO;
import fashionmanager.kim.develop.entity.Blacklist;
import fashionmanager.kim.develop.mapper.BlacklistMapper;
import fashionmanager.kim.develop.repository.BlacklistRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlacklistService {

    private final BlacklistMapper blacklistMapper;

    private final BlacklistRepository blacklistRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public BlacklistService(BlacklistMapper blacklistMapper, BlacklistRepository blacklistRepository, ModelMapper modelMapper) {
        this.blacklistMapper = blacklistMapper;
        this.blacklistRepository = blacklistRepository;
        this.modelMapper = modelMapper;
    }

    public List<BlacklistDTO> selectBlacklist() {
        return blacklistMapper.selectBlacklist();
    }

    public int insertBlacklist(BlacklistDTO blacklistDTO) {
        boolean check1 = blacklistDTO.getBlacklistReason() == null || blacklistDTO.getBlacklistReason().isEmpty();
        boolean check2 = blacklistDTO.getBlacklistStartDate() == null || blacklistDTO.getBlacklistExpDate() == null;
        boolean check3 = blacklistDTO.getBlacklistMemberNum() == 0;
        if(check1 || check2 || check3){
            return 0;
        }

        blacklistRepository.save(modelMapper.map(blacklistDTO, Blacklist.class));
        return 1;
    }

    @Transactional
    public int deleteBlacklist(int blacklistNum) {
        int result = blacklistRepository.deleteBlacklist(blacklistNum);
        if(result == 1){
            return 1;
        }else{
            return 0;
        }
    }
}
