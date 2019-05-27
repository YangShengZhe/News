package com.yangsz.news.DBmodel;

import cn.bmob.v3.BmobObject;

public class FocusUser extends BmobObject {
    private String FocusUsers;
    private String BeFocused;
    public String focusId;

    public FocusUser(){}
    public FocusUser(String FocusUsers,String BeFocused){
        this.FocusUsers=FocusUsers;
        this.BeFocused=BeFocused;
    }

    public String getFocusUsers() {
        return FocusUsers;
    }

    public void setFocusUsers(String focusUsers) {
        FocusUsers = focusUsers;
    }

    public String getBeFocused() {
        return BeFocused;
    }

    public void setBeFocused(String beFocused) {
        BeFocused = beFocused;
    }


}
