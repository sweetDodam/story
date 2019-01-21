package net.healingchurch.story.services.user;

import net.healingchurch.story.domain.Role;
import net.healingchurch.story.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findUserList(User user);

    int findUserListCnt(User user);

    User findUserById(String userId);

    int createUser(User user);

    List<Role> findUserRoleList(String userId);

    int updateUser(User user);

    void removeUser(String userId);
}
