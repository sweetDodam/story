package net.healingchurch.story.services.user.role;

import net.healingchurch.story.domain.Role;

import java.util.List;

public interface UserRoleService {
    List<Role> findUserRoleList();

    Role getUserRole(int roleId);

    int createUserRole(String roleName, String description, int roleOrder);

    int updateUserRole(int roleId, String roleName, String description, int roleOrder);

    void removeUserRole(int roleId);
}
