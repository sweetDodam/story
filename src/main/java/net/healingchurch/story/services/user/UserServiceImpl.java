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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<Object, Object> findUserList(String userId, int roleId, int groupId, String userName, boolean isAdmin, String alphaDate, String pastureJoinDate, int page, int limit) {
        User user = new User();

        user.setPage(page);
        user.setOffset((page-1)*limit);
        user.setLimit(limit);

        user.setUserId(userId);
        user.setRoleId(roleId);
        user.setGroupId(groupId);
        user.setUserName(userName);
        user.setIsAdmin(isAdmin);
        user.setAlphaDate(alphaDate);
        user.setPastureJoinDate(pastureJoinDate);

        Map<Object, Object> resutMap = new HashMap<>();

        //데이터
        resutMap.put("rows", userMapper.findUserList(user));

        //전체 갯수
        int records = userMapper.findUserListCnt(user);
        resutMap.put("records", records);

        //페이지 수
        resutMap.put("total", (int)Math.ceil((double)records/(double)limit));

        return resutMap;
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
    public Map<Object, Object> updateUserPassword(String userId, String password) {
        //비밀번호 유효성 체크
        Map<Object, Object> resutMap = passwordChk(userId, password);

        //유효하다면 비밀번호 변경
        if("success".equals(resutMap.get("result").toString())){
            User user = new User();

            user.setUserId(userId);
            user.setPassword(passwordEncoder.encode(password));

            userMapper.updateUser(user);
        }else{

        }


        return resutMap;
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

    public  Map<Object, Object> passwordChk(String userId, String password){
        Map<Object, Object> resutMap = new HashMap<>();

        String result = "success";
        String msg = "변경되었습니다.";
        String location = "/";

        if(password.contains(" ")) {
            result = "fail";
            msg = "비밀번호에 공백을 넣을 수 없습니다.";
        }

        String pattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,}$";
        if(!Pattern.matches(pattern, password)){
            result = "fail";
            msg = "비밀번호는 8자리이상이고 숫자, 문자, 특수문자가 포함되어야합니다.";
        }

        resutMap.put("result", result);
        resutMap.put("msg", msg);
        resutMap.put("location", location);

        return resutMap;
    }
}
