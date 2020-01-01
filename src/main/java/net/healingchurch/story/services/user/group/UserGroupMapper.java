package net.healingchurch.story.services.user.group;

import net.healingchurch.story.domain.UserGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserGroupMapper {
    List<UserGroup> findUserGroupList(UserGroup userGroup);

    List<UserGroup> findUserGroupChildList(UserGroup userGroup);

    UserGroup getUserGroup(UserGroup userGroup);

    int createUserGroup(UserGroup userGroup);

    int updateUserGroup(UserGroup userGroup);

    void removeUserGroup(int groupId);
}
