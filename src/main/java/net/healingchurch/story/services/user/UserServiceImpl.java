package net.healingchurch.story.services.user;

import net.healingchurch.story.domain.Role;
import net.healingchurch.story.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findUserList(String userId, int roleId, int groupId, String userName, boolean isAdmin, String alphaDate, String pastureJoinDate, int page, int limit) {
        User user = new User();
        user.setPage(page);
        user.setOffset((page-1)*limit);
        user.setLimit(limit);

       /* user.setUserId(limit);
        user.setGroupId(limit);
        user.setRoleId(limit);
        user.setUserName(limit);
        user.setIsAdmin(limit);
        user.setAlphaDate(limit);
        user.setPastureJoinDate(limit);*/

        return userMapper.findUserList(user);
    }

    @Override
    public int findUserListCnt(String userId, int roleId, int groupId, String userName, boolean isAdmin, String alphaDate, String pastureJoinDate) {
        User user = new User();

       /* user.setUserId(limit);
        user.setGroupId(limit);
        user.setRoleId(limit);
        user.setUserName(limit);
        user.setIsAdmin(limit);
        user.setAlphaDate(limit);
        user.setPastureJoinDate(limit);*/

        return userMapper.findUserListCnt(user);
    }

    @Override
    public int createUser(String userId, String password, int roleId, int groupId, String userName, boolean isAdmin, String address, String mobile, String email, String regDate, String alphaDate, String pastureJoinDate, String status) {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoleId(roleId);
        user.setGroupId(groupId);
        user.setUserName(userName);
        user.setIsAdmin(isAdmin);
        user.setAddress(address);
        user.setMobile(mobile);
        user.setEmail(email);
        user.setRegDate(regDate);
        user.setAlphaDate(alphaDate);
        user.setPastureJoinDate(pastureJoinDate);
        user.setStatus(status);
        return userMapper.createUser(user);
    }

    @Override
    public User getUser(String userId) {
        return userMapper.findUserById(userId);
    }

    @Override
    public int updateUser(String userId, String password, int roleId, int groupId, String userName, boolean isAdmin, String address, String mobile, String email, String regDate, String alphaDate, String pastureJoinDate, String status) {
        User user = new User();
        user.setUserId(userId);

        if(!"".equals(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        user.setRoleId(roleId);
        user.setGroupId(groupId);
        user.setUserName(userName);
        user.setIsAdmin(isAdmin);
        user.setAddress(address);
        user.setMobile(mobile);
        user.setEmail(email);
        user.setRegDate(regDate);
        user.setAlphaDate(alphaDate);
        user.setPastureJoinDate(pastureJoinDate);
        user.setStatus(status);
        return userMapper.updateUser(user);
    }

    @Override
    public void removeUser(String userId) {
        userMapper.removeUser(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userMapper.findUserById(userId);
        List<Role> userRoles = userMapper.findUserRoleList(userId);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : userRoles){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUserId(),user.getPassword(), grantedAuthorities);
    }


}
