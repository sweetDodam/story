package net.healingchurch.story.services.user.group;

import net.healingchurch.story.domain.Role;
import net.healingchurch.story.domain.User;
import net.healingchurch.story.domain.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserGroupServiceImpl implements UserGroupService {
    @Autowired
    private UserGroupMapper userGroupMapper;

    @Override
    public List<UserGroup> findUserGroupList(int page, int limit) {
        UserGroup userGroup = new UserGroup();
        userGroup.setPage(page);
        userGroup.setOffset((page-1)*limit);
        userGroup.setLimit(limit);
        return userGroupMapper.findUserGroupList(userGroup);
    }

    @Override
    public List<UserGroup> findUserGroupChildList(int parentGroupId) {
        return userGroupMapper.findUserGroupChildList(parentGroupId);
    }

    @Override
    public UserGroup getUserGroup(int groupId) {
        return userGroupMapper.getUserGroup(groupId);
    }

    @Override
    public int createUserGroup(int parentGroupId, String groupName, int groupLevel) {
        UserGroup userGroup = new UserGroup();
        userGroup.setParentGroupId(parentGroupId);
        userGroup.setGroupName(groupName);
        userGroup.setGroupLevel(groupLevel);
        return userGroupMapper.createUserGroup(userGroup);
    }

    @Override
    public int updateUserGroup(int groupId, int parentGroupId, String groupName, int groupLevel) {
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupId(groupId);
        userGroup.setParentGroupId(parentGroupId);
        userGroup.setGroupName(groupName);
        userGroup.setGroupLevel(groupLevel);
        return userGroupMapper.updateUserGroup(userGroup);
    }

    @Override
    public void removeUserGroup(int groupId) {
        userGroupMapper.removeUserGroup(groupId);
    }

}
