package net.healingchurch.story.domain;

import java.util.Objects;

public class User {
    private int userSeq;
    private String userId;
    private String password;
    private int roleId;
    private int groupId;
    private int parentGroupId;
    private String userName;
    private boolean isAdmin;
    private String address;
    private String mobile;
    private String email;
    private String birthDate;
    private String regDate;
    private String alphaDate;
    private String pastureJoinDate;
    private String status;
    private String createDate;
    private String updateDate;
    private String createUser;
    private String updateUser;
    private boolean isPermission;
    private String searchPermission;

    private String fromDate;
    private String toDate;

    private String groupDesc;
    private String roleDesc;
    private int roleOrder;

    private int page;
    private int offset;
    private int limit;

    private String loginUserId;
    private int reserveId;

    public User(){}

    public int getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(int userSeq) {
        this.userSeq = userSeq;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getAlphaDate() {
        return alphaDate;
    }

    public void setAlphaDate(String alphaDate) {
        this.alphaDate = alphaDate;
    }

    public String getPastureJoinDate() {
        return pastureJoinDate;
    }

    public void setPastureJoinDate(String pastureJoinDate) {
        this.pastureJoinDate = pastureJoinDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public boolean getIsPermission() {
        return isPermission;
    }

    public void setIsPermission(boolean isPermission) {
        this.isPermission = isPermission;
    }

    public String getSearchPermission() {
        return searchPermission;
    }

    public void setSearchPermission(String searchPermission) {
        this.searchPermission = searchPermission;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public int getRoleOrder() {
        return roleOrder;
    }

    public void setRoleOrder(int roleOrder) {
        this.roleOrder = roleOrder;
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

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    public int getReserveId() {
        return reserveId;
    }

    public void setReserveId(int reserveId) {
        this.reserveId = reserveId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userSeq == user.userSeq;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userSeq);
    }

    @Override
    public String toString() {
        return "User{" +
                "userSeq=" + userSeq +
                "userId='" + userId + '\'' +
                ", roleId=" + roleId +
                ", groupId=" + groupId +
                ", parentGroupId=" + parentGroupId +
                ", userName='" + userName + '\'' +
                ", isAdmin=" + isAdmin +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", regDate='" + regDate + '\'' +
                ", alphaDate='" + alphaDate + '\'' +
                ", pastureJoinDate='" + pastureJoinDate + '\'' +
                ", status='" + status + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", createUser='" + createUser + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", isPermission='" + isPermission + '\'' +
                ", searchPermission='" + searchPermission + '\'' +
                ", groupDesc='" + groupDesc + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                ", roleOrder='" + roleOrder + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", page=" + page +
                ", offset=" + offset +
                ", limit=" + limit +
                ", loginUserId='" + loginUserId + '\'' +
                ", reserveId=" + reserveId +
                '}';
    }
}