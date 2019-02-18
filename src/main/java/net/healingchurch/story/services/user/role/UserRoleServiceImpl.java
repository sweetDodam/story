package net.healingchurch.story.services.user.role;

import net.healingchurch.story.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<Role> findUserRoleList() {
        Role role = new Role();
        return userRoleMapper.findUserRoleList(role);
    }

    @Override
    public Role getUserRole(int roleId) {
        return userRoleMapper.getUserRole(roleId);
    }

    @Override
    public int createUserRole(String roleName, String description, int roleOrder) {
        Role role = new Role();

        role.setRoleName(roleName);
        role.setDescription(description);
        role.setRoleOrder(roleOrder);

        //로그인한 유저 아이디
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        role.setLoginUserId(principal.getUsername());

        return userRoleMapper.createUserRole(role);
    }

    @Override
    public int updateUserRole(int roleId, String roleName, String description, int roleOrder) {
        Role role = new Role();

        role.setRoleId(roleId);
        role.setRoleName(roleName);
        role.setDescription(description);
        role.setRoleOrder(roleOrder);

        //로그인한 유저 아이디
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        role.setLoginUserId(principal.getUsername());

        return userRoleMapper.updateUserRole(role);
    }

    @Override
    public void removeUserRole(int roleId) {
        userRoleMapper.removeUserRole(roleId);
    }

}
