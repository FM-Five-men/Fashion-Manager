package fashionmanager.song.develop.influencerPage.service;

import fashionmanager.song.develop.influencerPage.dto.InfluencerPageResponseDTO;
import fashionmanager.song.develop.influencerPage.mapper.InfluencerPageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InfluencerPageReadService {

    private final InfluencerPageMapper influencerPageMapper;
    private static final int PAGE_SIZE = 2; // 각 페이지에 몇개 식 들어갈지

    public List<InfluencerPageResponseDTO> getList(
            int page,
            String title,
            String insta,
            String phone,
            Integer memberNum
    ) {
        int pageIndex = Math.max(page, 1) - 1; // 1-base → 0-base
        int offset = pageIndex * PAGE_SIZE;

        List<InfluencerPageResponseDTO> rows = influencerPageMapper.selectResultPageWithPaging(
                title, insta, phone, memberNum, offset, PAGE_SIZE
        );

        return rows;
    }
}