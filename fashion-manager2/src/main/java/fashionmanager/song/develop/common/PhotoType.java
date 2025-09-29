package fashionmanager.song.develop.common;


public enum PhotoType {

    // beak에 필요한 코드
    FASHION_POST(1),
    REVIEW_POST(2),
    MENTORING_POST(3),
    FASHION_ITEM(4),

    // song에 필요한 코드
    INFLUENCER_APPLY(5),
    INFLUENCER_PAGE(6),
    MENTEE_APPLY(7);



    private final int code;

    PhotoType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}