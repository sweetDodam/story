package net.healingchurch.story.domain;

import java.util.Objects;

public class Story extends User{
    private int storyId;
    private String userId;
    private int groupId;
    private int worshipYn;
    private int leaderYn;
    private int pastureMeetYn;
    private int bibleCount;
    private int qtCount;
    private int fridayWorshipYn;
    private int dawnPrayCount;
    private String etc;

    private String createDate;
    private String updateDate;
    private String createUser;
    private String updateUser;
    private String inputDate;

    private String fromDate;
    private String toDate;

    private int page;
    private int offset;
    private int limit;

    public Story() {
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getDawnPrayCount() {
        return dawnPrayCount;
    }

    public void setDawnPrayCount(int dawnPrayCount) {
        this.dawnPrayCount = dawnPrayCount;
    }

    public int getWorshipYn() {
        return worshipYn;
    }

    public void setWorshipYn(int worshipYn) {
        this.worshipYn = worshipYn;
    }

    public int getLeaderYn() {
        return leaderYn;
    }

    public void setLeaderYn(int leaderYn) {
        this.leaderYn = leaderYn;
    }

    public int getPastureMeetYn() {
        return pastureMeetYn;
    }

    public void setPastureMeetYn(int pastureMeetYn) {
        this.pastureMeetYn = pastureMeetYn;
    }

    public int getBibleCount() {
        return bibleCount;
    }

    public void setBibleCount(int bibleCount) {
        this.bibleCount = bibleCount;
    }

    public int getQtCount() {
        return qtCount;
    }

    public void setQtCount(int qtCount) {
        this.qtCount = qtCount;
    }

    public int getFridayWorshipYn() {
        return fridayWorshipYn;
    }

    public void setFridayWorshipYn(int fridayWorshipYn) {
        this.fridayWorshipYn = fridayWorshipYn;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Story story = (Story) o;
        return storyId == story.storyId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(storyId);
    }

    @Override
    public String toString() {
        return "Story{" +
                "storyId=" + storyId +
                ", userId=" + userId +
                ", groupId=" + groupId +
                ", worshipYn=" + worshipYn +
                ", pastureMeetYn=" + pastureMeetYn +
                ", bibleCount=" + bibleCount +
                ", qtCount=" + qtCount +
                ", fridayWorshipYn=" + fridayWorshipYn +
                ", dawnPrayCount=" + dawnPrayCount +
                ", etc='" + etc + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", createUser='" + createUser + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", inputDate=" + inputDate + '\'' +
                '}';
    }
}
