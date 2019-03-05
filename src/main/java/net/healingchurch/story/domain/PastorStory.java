package net.healingchurch.story.domain;

import java.util.Objects;

public class PastorStory extends User{
    private int storyId;
    private String pastorId;
    private String visitUserId;
    private String visitDate;
    private String visitPlace;
    private String summary;
    private String prayers;
    private String etc;
    private String createDate;
    private String updateDate;
    private String createUser;
    private String updateUser;

    private String fromDate;
    private String toDate;
    private int page;
    private int offset;
    private int limit;

    private int reserveId;
    private String inputDate;
    private String isReserve;
    private String isConfirm;

    public PastorStory() {
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    public String getPastorId() {
        return pastorId;
    }

    public void setPastorId(String pastorId) {
        this.pastorId = pastorId;
    }

    public String getVisitUserId() {
        return visitUserId;
    }

    public void setVisitUserId(String visitUserId) {
        this.visitUserId = visitUserId;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitPlace() {
        return visitPlace;
    }

    public void setVisitPlace(String visitPlace) {
        this.visitPlace = visitPlace;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPrayers() {
        return prayers;
    }

    public void setPrayers(String prayers) {
        this.prayers = prayers;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
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

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getReserveId() {
        return reserveId;
    }

    public void setReserveId(int reserveId) {
        this.reserveId = reserveId;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }

    public String isReserve() {
        return isReserve;
    }

    public void setIsReserve(String reserve) {
        isReserve = reserve;
    }

    public String getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(String isConfirm) {
        this.isConfirm = isConfirm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PastorStory that = (PastorStory) o;
        return storyId == that.storyId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(storyId);
    }

    @Override
    public String toString() {
        return "PastorStory{" +
                "storyId=" + storyId +
                ", pastorId='" + pastorId + '\'' +
                ", visitUserId='" + visitUserId + '\'' +
                ", visitDate='" + visitDate + '\'' +
                ", visitPlace='" + visitPlace + '\'' +
                ", summary='" + summary + '\'' +
                ", prayers='" + prayers + '\'' +
                ", etc='" + etc + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", createUser='" + createUser + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", isReserve='" + isReserve + '\'' +
                ", isConfirm='" + isConfirm + '\'' +
                ", page=" + page +
                ", offset=" + offset +
                ", limit=" + limit +
                ", reserveId='" + reserveId + '\'' +
                ", inputDate='" + inputDate + '\'' +
                '}';
    }
}
