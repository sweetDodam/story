package net.healingchurch.story.domain;

import java.util.Objects;

public class UserGroup {
    private int groupId;
    private int parentGroupId;
    private String groupName;
    private int groupLevel;
    private String createDate;
    private String updateDate;

    private int page;
    private int offset;
    private int limit;

    public UserGroup(){}

    public UserGroup(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(int parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(int groupLevel) {
        this.groupLevel = groupLevel;
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
        UserGroup userGroup = (UserGroup) o;
        return groupId == userGroup.groupId;
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

    @Override
    public int hashCode() {
        return Objects.hash(groupId);
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "groupId=" + groupId +
                ", parentGroupId=" + parentGroupId +
                ", groupName='" + groupName + '\'' +
                ", groupLevel=" + groupLevel +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", page=" + page +
                ", offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}
