package net.healingchurch.story.services.user.role;

import net.healingchurch.story.domain.Role;
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
@RequestMapping("/services/user/role")
public class UserRoleController {

    private static final Logger logger = LoggerFactory.getLogger(UserRoleController.class);

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("list")
    public Map<Object, Object>findUserRoleList(){
        Map<Object, Object> resutMap = new HashMap<>();
        //데이터
        resutMap.put("rows", userRoleService.findUserRoleList());

        return resutMap;
    }

    @GetMapping("get")
    public Role getUserRole(@RequestParam(value = "roleId", defaultValue = "") int roleId) {
        return userRoleService.getUserRole(roleId);
    }

    @PostMapping("create")
    public int createUserRole(
                            @RequestParam(value = "roleName", defaultValue = "") String roleName,
                            @RequestParam(value = "description", defaultValue = "") String description,
                            @RequestParam(value = "roleOrder", defaultValue = "0") int roleOrder){
        return userRoleService.createUserRole(roleName, description, roleOrder);
    }

    @PostMapping("update")
    public int updateUserRole(@RequestParam(value = "roleId", defaultValue = "0") int roleId,
                               @RequestParam(value = "roleName", defaultValue = "") String roleName,
                              @RequestParam(value = "description", defaultValue = "") String description,
                               @RequestParam(value = "roleOrder", defaultValue = "0") int roleOrder){
        return userRoleService.updateUserRole(roleId, roleName, description, roleOrder);
    }

    @PostMapping("remove")
    public int removeUserRole(
            @RequestParam(value = "roleId", required = true, defaultValue = "") int roleId) {
        userRoleService.removeUserRole(roleId);
        return 1;
    }
}
