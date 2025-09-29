package fashionmanager.park.develop.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

@Slf4j
public class Pagination {
    public static PagingButtonInfo getPagingButtonInfo(Page page) {
        int currentPage = page.getNumber() + 1;   // 인덱스 개념 -> 실제 페이지에 보여질 번호의 개념으로 다시 변경
        int defaultButtonCount = 10;              // 한 페이지에 보일 버튼의 갯수
        int startPage;                            // 한 페이지에 보여질 첫 버튼
        int endPage;                              // 한 페이지에 보여질 마지막 버튼

        log.debug("Pagination에서 currentPage 정보 확인: {}", currentPage);
//        startPage = (int)(Math.ceil(currentPage / (double)defaultButtonCount) - 1)
        startPage = ((int)(currentPage / (double)defaultButtonCount + 0.9) - 1)
                * defaultButtonCount + 1;
        log.debug("Pagination에서 startPage 정보 확인: {}", startPage);
        endPage = startPage + defaultButtonCount - 1;


        if(page.getTotalPages() < endPage) {     // totalPage가 마지막 페이지보다 작으면
            endPage = page.getTotalPages();      // totalPage가 마지막 페이지 버튼이 된다.
        }

        if(page.getTotalPages() == 0) {          // 1페이지도 안된다면
            endPage = startPage;                 // startPage가 곧 endPage가 된다.(1페이지 버튼만 표시)
        }

        return new PagingButtonInfo(currentPage, startPage, endPage);
    }
}