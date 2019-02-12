package net.healingchurch.story.services.user;

import net.healingchurch.story.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<Object, Object> findUserList(String userId, int roleId, int groupId, String userName, boolean isAdmin, String alphaDate, String pastureJoinDate, int page, int limit);

    List<User> findUserSimpleList(String userId, int roleId, int groupId, String userName);

    int createUser(String userId, String password, int roleId, int groupId, String userName, boolean isAdmin, String address, String mobile, String email, String regDate, String alphaDate, String pastureJoinDate, boolean isPermission, String status);

    User getUser(String userId);

    int updateUser(String userId, int roleId, int groupId, String userName, boolean isAdmin, String address, String mobile, String email, String regDate, String alphaDate, String pastureJoinDate, boolean isPermission, String status);

    Map<Object, Object> updateUserPassword(String userId, String password, boolean validation);

    void removeUser(String userId);
}
