package net.healingchurch.story.services.user.group;

import net.healingchurch.story.domain.Role;
import net.healingchurch.story.domain.User;
import net.healingchurch.story.domain.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public List<UserGroup> findUserGroupList(int page, String tempYn, String useYn, int limit) {
        UserGroup userGroup = new UserGroup();
        userGroup.setPage(page);
        userGroup.setTempYn(tempYn);
        userGroup.setUseYn(useYn);
        userGroup.setOffset((page-1)*limit);
        userGroup.setLimit(limit);
        return userGroupMapper.findUserGroupList(userGroup);
    }

    @Override
    public List<UserGroup> findUserGroupChildList(int parentGroupId, String tempYn, String useYn) {
        UserGroup userGroup = new UserGroup();
        userGroup.setParentGroupId(parentGroupId);
        userGroup.setTempYn(tempYn);
        userGroup.setUseYn(useYn);
        return userGroupMapper.findUserGroupChildList(userGroup);
    }

    @Override
    public UserGroup getUserGroup(int groupId, String tempYn, String useYn) {
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupId(groupId);
        userGroup.setTempYn(tempYn);
        userGroup.setUseYn(useYn);
        return userGroupMapper.getUserGroup(userGroup);
    }

    @Override
    public int createUserGroup(int parentGroupId, String groupName, int groupLevel, String tempYn) {
        UserGroup userGroup = new UserGroup();

        userGroup.setParentGroupId(parentGroupId);
        userGroup.setGroupName(groupName);
        userGroup.setGroupLevel(groupLevel);
        userGroup.setTempYn(tempYn);

        //로그인한 유저 아이디
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userGroup.setLoginUserId(principal.getUsername());

        return userGroupMapper.createUserGroup(userGroup);
    }

    @Override
    public int updateUserGroup(int groupId, int parentGroupId, String groupName, int groupLevel, String tempYn) {
        UserGroup userGroup = new UserGroup();

        userGroup.setGroupId(groupId);
        userGroup.setParentGroupId(parentGroupId);
        userGroup.setGroupName(groupName);
        userGroup.setGroupLevel(groupLevel);
        userGroup.setTempYn(tempYn);

        //로그인한 유저 아이디
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userGroup.setLoginUserId(principal.getUsername());

        return userGroupMapper.updateUserGroup(userGroup);
    }

    @Override
    public void removeUserGroup(int groupId) {
        userGroupMapper.removeUserGroup(groupId);
    }

}
