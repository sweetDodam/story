package net.healingchurch.story.domain;

import java.util.Objects;

public class PastorStory {
    private int storyId;
    private String pastorId;
    private String visitUserId;
    private String visitDate;
    private String summary;
    private String prayers;
    private String etc;
    private String createDate;
    private String updateDate;

    private String fromDate;
    private String toDate;
    private int page;
    private int offset;
    private int limit;

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
                ", summary='" + summary + '\'' +
                ", prayers='" + prayers + '\'' +
                ", etc='" + etc + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", page=" + page +
                ", offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}
