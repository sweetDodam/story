package net.healingchurch.story.services.user.role;

import net.healingchurch.story.domain.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper {
    List<Role> findUserRoleList(Role role);

    Role getUserRole(int roleId);

    int createUserRole(Role role);

    int updateUserRole(Role role);

    void removeUserRole(int roleId);
}
