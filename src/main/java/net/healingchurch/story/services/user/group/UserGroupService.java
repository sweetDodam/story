package net.healingchurch.story.services.user.group;

import net.healingchurch.story.domain.User;
import net.healingchurch.story.domain.UserGroup;

import java.util.List;

public interface UserGroupService {
    List<UserGroup> findUserGroupList(int page, int limit);

    List<UserGroup> findUserGroupChildList(int parentGroupId);

    UserGroup getUserGroup(int groupId);

    int createUserGroup(int parentGroupId, String groupName, int groupLevel);

    int updateUserGroup(int groupId, int parentGroupId, String groupName, int groupLevel);

    void removeUserGroup(int groupId);
}
