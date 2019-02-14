package net.healingchurch.story.services.user;

import net.healingchurch.story.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@EnableAutoConfiguration
@RequestMapping("/services/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("list")
    public Map<Object, Object>  findUserList(@RequestParam(value = "userId", defaultValue = "") String userId,
                                   @RequestParam(value = "roleId", defaultValue = "0") int roleId,
                                   @RequestParam(value = "groupId", defaultValue = "0") int groupId,
                                   @RequestParam(value = "userName", defaultValue = "") String userName,
                                   @RequestParam(value = "isAdmin", defaultValue = "false") boolean isAdmin,
                                   @RequestParam(value = "alphaDate", defaultValue = "") String alphaDate,
                                   @RequestParam(value = "pastureJoinDate", defaultValue = "") String pastureJoinDate,
                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "limit", defaultValue = "20") int limit) {
        return userService.findUserList(userId, roleId, groupId, userName, isAdmin, alphaDate, pastureJoinDate, page, limit);
    }

    @GetMapping("get")
    public User getUser(@RequestParam(value = "userId", defaultValue = "") String userId) {
        return userService.getUser(userId);
    }

    @PostMapping("create")
    public int createUser(@RequestParam(value = "userId", defaultValue = "") String userId,
                            @RequestParam(value = "password", defaultValue = "") String password,
                            @RequestParam(value = "roleId", defaultValue = "0") int roleId,
                            @RequestParam(value = "groupId", defaultValue = "0") int groupId,
                            @RequestParam(value = "userName", defaultValue = "") String userName,
                            @RequestParam(value = "isAdmin", defaultValue = "false") boolean isAdmin,
                            @RequestParam(value = "address", defaultValue = "") String address,
                            @RequestParam(value = "mobile", defaultValue = "") String mobile,
                            @RequestParam(value = "email", defaultValue = "") String email,
                            @RequestParam(value = "birthDate", defaultValue = "") String birthDate,
                            @RequestParam(value = "regDate", defaultValue = "") String regDate,
                            @RequestParam(value = "alphaDate", defaultValue = "") String alphaDate,
                            @RequestParam(value = "pastureJoinDate", defaultValue = "") String pastureJoinDate,
                            @RequestParam(value = "isPermission", defaultValue = "false") boolean isPermission,
                            @RequestParam(value = "status", defaultValue = "") String status){
        return userService.createUser(userId, password, roleId, groupId, userName, isAdmin, address, mobile, email, birthDate, regDate, alphaDate, pastureJoinDate, isPermission, status);
    }

    @PostMapping("update")
    public int updateUser(@RequestParam(value = "userId", defaultValue = "") String userId,
                          @RequestParam(value = "roleId", defaultValue = "0") int roleId,
                          @RequestParam(value = "groupId", defaultValue = "0") int groupId,
                          @RequestParam(value = "userName", defaultValue = "") String userName,
                          @RequestParam(value = "isAdmin", defaultValue = "false") boolean isAdmin,
                          @RequestParam(value = "address", defaultValue = "") String address,
                          @RequestParam(value = "mobile", defaultValue = "") String mobile,
                          @RequestParam(value = "email", defaultValue = "") String email,
                          @RequestParam(value = "birthDate", defaultValue = "") String birthDate,
                          @RequestParam(value = "regDate", defaultValue = "") String regDate,
                          @RequestParam(value = "alphaDate", defaultValue = "") String alphaDate,
                          @RequestParam(value = "pastureJoinDate", defaultValue = "") String pastureJoinDate,
                          @RequestParam(value = "isPermission", defaultValue = "false") boolean isPermission,
                          @RequestParam(value = "status", defaultValue = "") String status){
        return userService.updateUser(userId, roleId, groupId, userName, isAdmin, address, mobile, email, birthDate, regDate, alphaDate, pastureJoinDate, isPermission, status);
    }

    @PostMapping("passwordUpdate")
    public Map<Object, Object> updateUserPassword(@RequestParam(value = "userId", defaultValue = "") String userId,
                          @RequestParam(value = "password", defaultValue = "") String password,
                          @RequestParam(value = "validation", defaultValue = "true") boolean validation){
        return userService.updateUserPassword(userId, password, validation);
    }

    @PostMapping("remove")
    public int removeUser(
            @RequestParam(value = "userId", required = true, defaultValue = "") String userId) {
        userService.removeUser(userId);
        return 1;
    }
}
