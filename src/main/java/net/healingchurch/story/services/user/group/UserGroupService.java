package net.healingchurch.story.services.user.group;

import net.healingchurch.story.domain.User;
import net.healingchurch.story.domain.UserGroup;

import java.util.List;

public interface UserGroupService {
    List<UserGroup> findUserGroupList(int page, String tempYn, String useYn, int limit);

    List<UserGroup> findUserGroupChildList(int parentGroupId, String tempYn, String useYn);

    UserGroup getUserGroup(int groupId, String tempYn, String useYn);

    int createUserGroup(int parentGroupId, String groupName, int groupLevel, String tempYn);

    int updateUserGroup(int groupId, int parentGroupId, String groupName, int groupLevel, String tempYn);

    void removeUserGroup(int groupId);
}
