package com.mainproject.user.vo;

public enum UserRank {
    EGG("달걀", "/img/egg.png"),
    HATCHING_CHICK("햇병아리", "/img/hatching-chick.png"),
    CHICK("병아리", "/img/chick.png"),
    CHICKEN("닭", "/img/chicken.png"),
    FRIED_CHICKEN("치킨","/img/fried-chicken.png"),
    ADMIN("관리자", "/img/admin.png"),
    PRIVACY_ADMIN("개인정보 관리자", "/img/privacy-admin.png");

    private final String displayName;
    private final String imagePath;

    UserRank(String displayName, String imagePath) {
        this.displayName = displayName;
        this.imagePath = imagePath;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getImagePath() {
        return imagePath;
    }
    
}
