package net.healingchurch.story.services.user.group;

import net.healingchurch.story.domain.UserGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/services/user/group")
public class UserGroupController {

    private static final Logger logger = LoggerFactory.getLogger(UserGroupController.class);

    @Autowired
    private UserGroupService userGroupService;

    @GetMapping("list")
    public List<UserGroup> findUserGroupList(@RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "limit", defaultValue = "20") int limit) {
        return userGroupService.findUserGroupList(page, limit);
    }

    @GetMapping("childList")
    public List<UserGroup> findUserGroupChildList(@RequestParam(value = "parentGroupId", defaultValue = "") int parentGroupId) {
        return userGroupService.findUserGroupChildList(parentGroupId);
    }

    @GetMapping("get")
    public UserGroup getUserGroup(@RequestParam(value = "groupId", defaultValue = "") int groupId) {
        return userGroupService.getUserGroup(groupId);
    }

    @PostMapping("create")
    public int createUserGroup(
                            @RequestParam(value = "parentGroupId", defaultValue = "0") int parentGroupId,
                            @RequestParam(value = "groupName", defaultValue = "") String groupName,
                            @RequestParam(value = "groupLevel", defaultValue = "0") int groupLevel){
        return userGroupService.createUserGroup(parentGroupId, groupName, groupLevel);
    }

    @PostMapping("update")
    public int updateUserGroup(@RequestParam(value = "groupId", defaultValue = "0") int groupId,
                               @RequestParam(value = "parentGroupId", defaultValue = "0") int parentGroupId,
                               @RequestParam(value = "groupName", defaultValue = "") String groupName,
                               @RequestParam(value = "groupLevel", defaultValue = "0") int groupLevel){
        return userGroupService.updateUserGroup(groupId, parentGroupId, groupName, groupLevel);
    }

    @PostMapping("remove")
    public int removeUserGroup(
            @RequestParam(value = "groupId", required = true, defaultValue = "") int groupId) {
        userGroupService.removeUserGroup(groupId);
        return 1;
    }
}
