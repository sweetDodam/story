package net.healingchurch.story.services.index;

import net.healingchurch.story.domain.User;
import net.healingchurch.story.domain.UserGroup;
import net.healingchurch.story.domain.PastorStory;
import net.healingchurch.story.domain.TownStory;
import net.healingchurch.story.domain.PastureStory;

import net.healingchurch.story.services.user.UserService;
import net.healingchurch.story.services.user.group.UserGroupService;
import net.healingchurch.story.services.story.pastor.PastorStoryService;
import net.healingchurch.story.services.story.town.TownStoryService;
import net.healingchurch.story.services.story.pasture.PastureStoryService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

@Controller
public class WebAppContoller {
    @Autowired
    private UserService userService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private PastorStoryService pastorStoryService;

    @Autowired
    private TownStoryService townStoryService;

    @Autowired
    private PastureStoryService pastureStoryService;

    @GetMapping("/")
    public String home(Map<String, Object> model, @AuthenticationPrincipal UserDetails userDetails) {
        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        model.put("message", "샬롬!");
        model.put("title", "Chyou Web Story");
        model.put("date", new Date());

        return "home";
    }

    /*****스토리 페이지*****/
    @GetMapping("/story/pasture")
    public String storyPasture(Map<String, Object> model, @AuthenticationPrincipal UserDetails userDetails) {
        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //로그인한 유저의 그룹 상세 정보
        UserGroup formUserGroup = userGroupService.getUserGroup(user.getGroupId());
        model.put("formUserGroup", formUserGroup);

        return "story_pasture";
    }

    @GetMapping("/story/pasture/form")
    public String storyPastureForm(Map<String, Object> model,
                                   @RequestParam(value = "userId", defaultValue = "") String userId,
                                   @RequestParam(value = "storyId", defaultValue = "0") int storyId,
                                   @RequestParam(value = "inputDate", defaultValue = "") String inputDate,
                                   @AuthenticationPrincipal UserDetails userDetails) {

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //선택된 목원의 스토리 정보
        PastureStory formStory = pastureStoryService.getStory(storyId);
        model.put("formStory", formStory);

        //선택된 목원의 정보
        User formUser = userService.getUser(userId);
        model.put("formUser", formUser);

        //선택한 스토리 날짜
        model.put("formInputDate", inputDate);

        return "story_pasture_form";
    }

    @GetMapping("/story/town")
    public String storyTown(Map<String, Object> model, @AuthenticationPrincipal UserDetails userDetails) {
        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //로그인한 유저의 그룹 상세 정보
        UserGroup formUserGroup = userGroupService.getUserGroup(user.getGroupId());
        model.put("formUserGroup", formUserGroup);

        return "story_town";
    }

    @GetMapping("/story/town/form")
    public String storyTownForm(Map<String, Object> model, @RequestParam(value = "storyId", defaultValue = "") int storyId, @AuthenticationPrincipal UserDetails userDetails) {

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //선택된 목장의 스토리 정보
        TownStory formStory = townStoryService.getStory(storyId);
        model.put("formStory", formStory);

        return "story_town_form";
    }

    @GetMapping("/story/pastor")
    public String storyPastor(Map<String, Object> model, @AuthenticationPrincipal UserDetails userDetails) {
        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //로그인한 유저의 그룹 상세 정보
        UserGroup formUserGroup = userGroupService.getUserGroup(user.getGroupId());
        model.put("formUserGroup", formUserGroup);

        return "story_pastor";
    }

    @GetMapping("/story/pastor/form")
    public String storyPastorForm(Map<String, Object> model, @RequestParam(value = "storyId", defaultValue = "") int storyId, @AuthenticationPrincipal UserDetails userDetails) {

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //선택된 마을의 스토리 정보
        PastorStory formStory = pastorStoryService.getStory(storyId);
        model.put("formStory", formStory);

        return "story_pastor_form";
    }

    /*****공통 템플릿 페이지*****/
    @GetMapping("/com/menu")
    public String comMenu(Map<String, Object> model, @AuthenticationPrincipal UserDetails userDetails) {
        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        return "com_menu";
    }


    /*****관리 페이지*****/
    @GetMapping("/user")
    public String user(Map<String, Object> model, @AuthenticationPrincipal UserDetails userDetails) {
        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        return "user";
    }

    @GetMapping("/user/form")
    public String userForm(Map<String, Object> model, @RequestParam(value = "userId", defaultValue = "") String userId, @AuthenticationPrincipal UserDetails userDetails) {

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //선택된 유저의 정보
        User formUser = userService.getUser(userId);
        model.put("formUser", formUser);

        if(formUser != null) {
            //선택된 유저의 그룹 상세 정보
            UserGroup formUserGroup = userGroupService.getUserGroup(formUser.getGroupId());
            model.put("formUserGroup", formUserGroup);
        }

        return "user_form";
    }

    @GetMapping("/user/group")
    public String userGroup(Map<String, Object> model, @AuthenticationPrincipal UserDetails userDetails) {
        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        return "user_group";
    }

    @GetMapping("/user/group/add")
    public String addUserGroup(Map<String, Object> model, @AuthenticationPrincipal UserDetails userDetails) {
        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        return "user_group_add";
    }

    @GetMapping("/town")
    public String town(Map<String, Object> model, @AuthenticationPrincipal UserDetails userDetails) {
        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        return "town";
    }

    @GetMapping("/town/add")
    public String addTown(Map<String, Object> model, @AuthenticationPrincipal UserDetails userDetails) {
        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        return "town_add";
    }

    @GetMapping("/permission")
    public String permission(Map<String, Object> model, @AuthenticationPrincipal UserDetails userDetails) {
        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        return "permission";
    }

    @GetMapping("/permission/add")
    public String addPermission(Map<String, Object> model, @AuthenticationPrincipal UserDetails userDetails) {
        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        return "permission_add";
    }

    @GetMapping("/menu")
    public String menu(Map<String, Object> model, @AuthenticationPrincipal UserDetails userDetails) {
        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        return "menu";
    }

    @GetMapping("/menu/add")
    public String addMenu(Map<String, Object> model, @AuthenticationPrincipal UserDetails userDetails) {
        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        return "menu_add";
    }

    @GetMapping("/common/code")
    public String commonCode(Map<String, Object> model, @AuthenticationPrincipal UserDetails userDetails) {
        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        return "common_code";
    }

    @GetMapping("/common/code/add")
    public String addCommonCode(Map<String, Object> model, @AuthenticationPrincipal UserDetails userDetails) {
        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        return "common_code_add";
    }
}
