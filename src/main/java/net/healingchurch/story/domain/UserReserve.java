package net.healingchurch.story.domain;

import java.util.Objects;

public class UserReserve {
    private int reserveId;
    private String userId;
    private String inputDate;
    private String createDate;
    private String updateDate;
    private String createUser;
    private String updateUser;

    private int page;
    private int offset;
    private int limit;

    private String loginUserId;

    public int getReserveId() {
        return reserveId;
    }

    public void setReserveId(int reserveId) {
        this.reserveId = reserveId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserReserve userReserve = (UserReserve) o;
        return reserveId == userReserve.reserveId;
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

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reserveId);
    }

    @Override
    public String toString() {
        return "UserReserve{" +
                "reserveId=" + reserveId +
                ", userId='" + userId + '\'' +
                ", inputDate='" + inputDate + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", createUser='" + createUser + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", page=" + page +
                ", offset=" + offset +
                ", limit=" + limit +
                ", loginUserId=" + loginUserId +
                '}';
    }
}
