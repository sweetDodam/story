package net.healingchurch.story.services.index;

import net.healingchurch.story.domain.*;

import net.healingchurch.story.services.common.code.CodeService;
import net.healingchurch.story.services.event.EventService;
import net.healingchurch.story.services.menu.MenuService;
import net.healingchurch.story.services.user.UserService;
import net.healingchurch.story.services.user.group.UserGroupService;
import net.healingchurch.story.services.story.pastor.PastorStoryService;
import net.healingchurch.story.services.story.pasture.PastureStoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Field;
import java.util.*;

@Controller
public class WebAppContoller {
    @Autowired
    private UserService userService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private PastorStoryService pastorStoryService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private PastureStoryService pastureStoryService;

    @Autowired
    private EventService eventService;

    @Autowired
    private CodeService codeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home(Map<String, Object> model, @AuthenticationPrincipal UserDetails userDetails) {
        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //초기비밀번호라면
        if(passwordEncoder.matches("1111", user.getPassword())){
            return "password";
        }

        model.put("message", "샬롬!");
        model.put("title", "Chyou Web Story");
        model.put("date", new Date());

        return "home";
    }

    /*****스토리 페이지*****/
    //목장 스토리 관리
    @PreAuthorize("@menuService.accessCheck(#userDetails.getUsername(), #menuId)")
    @GetMapping("/story/pasture")
    public String storyPasture(Map<String, Object> model, @RequestParam(value = "menuId", defaultValue = "") int menuId, @AuthenticationPrincipal UserDetails userDetails) {
        //메뉴 정보
        Menu menu = menuService.getMenu(menuId);
        model.put("menuInfo", menu);

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //로그인한 유저의 그룹 상세 정보
        UserGroup formUserGroup = userGroupService.getUserGroup(user.getGroupId(), "N", "Y");
        model.put("formUserGroup", formUserGroup);

        return "story_pasture";
    }

    //목장 스토리 등록/수정
    @PreAuthorize("@menuService.accessCheck(#userDetails.getUsername(), #menuId)")
    @GetMapping("/story/pasture/form")
    public String storyPastureForm(Map<String, Object> model,
                                   @RequestParam(value = "userId", defaultValue = "") String userId,
                                   @RequestParam(value = "storyId", defaultValue = "0") int storyId,
                                   @RequestParam(value = "inputDate", defaultValue = "") String inputDate,
                                   @RequestParam(value = "menuId", defaultValue = "") int menuId,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        //메뉴 정보
        Menu menu = menuService.getMenu(menuId);
        model.put("menuInfo", menu);

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

        //화면에 뿌릴 공통코드 조회
        model.put("worshipList", codeService.findCodeChildList("WORSHIP_REASON"));
        model.put("leaderList", codeService.findCodeChildList("LEADER_REASON"));

        return "story_pasture_form";
    }

    //목장 스토리 조회
    @PreAuthorize("@menuService.accessCheck(#userDetails.getUsername(), #menuId)")
    @GetMapping("/story/pasture/list")
    public String storyPastureList(Map<String, Object> model, @RequestParam(value = "menuId", defaultValue = "") int menuId, @AuthenticationPrincipal UserDetails userDetails) {
        //메뉴 정보
        Menu menu = menuService.getMenu(menuId);
        model.put("menuInfo", menu);

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //로그인한 유저의 그룹 상세 정보
        UserGroup formUserGroup = userGroupService.getUserGroup(user.getGroupId(), "N", "Y");
        model.put("formUserGroup", formUserGroup);

        //화면에 뿌릴 공통코드 조회
        model.put("roleList", codeService.findCodeChildList("USER_ROLE"));

        return "story_pasture_list";
    }

    //목장 스토리 상세
    @PreAuthorize("@menuService.accessCheck(#userDetails.getUsername(), #menuId)")
    @GetMapping("/story/pasture/detail")
    public String storyPastureDetail(Map<String, Object> model,
                                   @RequestParam(value = "userId", defaultValue = "") String userId,
                                   @RequestParam(value = "menuId", defaultValue = "") int menuId,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        //메뉴 정보
        Menu menu = menuService.getMenu(menuId);
        model.put("menuInfo", menu);

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //선택된 목원의 정보
        User formUser = userService.getUser(userId);
        model.put("formUser", formUser);

        return "story_pasture_detail";
    }

    //마을 스토리 관리
    @PreAuthorize("@menuService.accessCheck(#userDetails.getUsername(), #menuId)")
    @GetMapping("/story/town")
    public String storyTown(Map<String, Object> model, @RequestParam(value = "menuId", defaultValue = "") int menuId, @AuthenticationPrincipal UserDetails userDetails) {
        //메뉴 정보
        Menu menu = menuService.getMenu(menuId);
        model.put("menuInfo", menu);

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //로그인한 유저의 마을 그룹 상세 정보
        UserGroup formUserGroup = userGroupService.getUserGroup(user.getParentGroupId(), "N", "Y");
        model.put("formUserGroup", formUserGroup);

        return "story_town";
    }

    //마을 스토리 등록/수정
    @PreAuthorize("@menuService.accessCheck(#userDetails.getUsername(), #menuId)")
    @GetMapping("/story/town/form")
    public String storyTownForm(Map<String, Object> model,
                                @RequestParam(value = "userId", defaultValue = "") String userId,
                                @RequestParam(value = "groupId", defaultValue = "0") int groupId,
                                @RequestParam(value = "eventId", defaultValue = "0") int eventId,
                                @RequestParam(value = "inputDate", defaultValue = "") String inputDate,
                                @RequestParam(value = "menuId", defaultValue = "") int menuId,
                                @AuthenticationPrincipal UserDetails userDetails) {
        //메뉴 정보
        Menu menu = menuService.getMenu(menuId);
        model.put("menuInfo", menu);

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //마을 목자 유저의 정보
        User formUser = userService.getUser(userId);
        model.put("formUser", formUser);

        //마을 동정 정보
        Event event = eventService.getEvent(eventId);
        model.put("formEvent", event);

        //선택한 그룹 아이디
        model.put("formGroupId", groupId);

        //선택한 스토리 날짜
        model.put("formInputDate", inputDate);

        return "story_town_form";
    }

    //마을 스토리 조회
    @PreAuthorize("@menuService.accessCheck(#userDetails.getUsername(), #menuId)")
    @GetMapping("/story/town/list")
    public String storyTownList(Map<String, Object> model, @RequestParam(value = "menuId", defaultValue = "") int menuId, @AuthenticationPrincipal UserDetails userDetails) {
        //메뉴 정보
        Menu menu = menuService.getMenu(menuId);
        model.put("menuInfo", menu);

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //로그인한 유저의 마을 그룹 상세 정보
        UserGroup formUserGroup = userGroupService.getUserGroup(user.getParentGroupId(), "N", "Y");
        model.put("formUserGroup", formUserGroup);

        //화면에 뿌릴 공통코드 조회
        model.put("roleList", codeService.findCodeChildList("USER_ROLE"));

        return "story_town_list";
    }

    //마을 스토리 상세
    @PreAuthorize("@menuService.accessCheck(#userDetails.getUsername(), #menuId)")
    @GetMapping("/story/town/detail")
    public String storyTownDetail(Map<String, Object> model,
                                     @RequestParam(value = "userId", defaultValue = "") String userId,
                                  @RequestParam(value = "menuId", defaultValue = "") int menuId,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        //메뉴 정보
        Menu menu = menuService.getMenu(menuId);
        model.put("menuInfo", menu);

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //마을 목자 유저의 정보
        User formUser = userService.getUser(userId);
        model.put("formUser", formUser);

        return "story_town_detail";
    }

    //사역자 스토리 관리
    @PreAuthorize("@menuService.accessCheck(#userDetails.getUsername(), #menuId)")
    @GetMapping("/story/pastor")
    public String storyPastor(Map<String, Object> model, @RequestParam(value = "menuId", defaultValue = "") int menuId, @AuthenticationPrincipal UserDetails userDetails) {
        //메뉴 정보
        Menu menu = menuService.getMenu(menuId);
        model.put("menuInfo", menu);

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //로그인한 유저의 그룹 상세 정보
        UserGroup formUserGroup = userGroupService.getUserGroup(user.getGroupId(), "N", "Y");
        model.put("formUserGroup", formUserGroup);

        //목회자 리스트
        List<User> pastorList = userService.findUserSimpleList("", 0, 2, "");
        model.put("pastorList", pastorList);

        return "story_pastor";
    }

    //사역자 스토리 등록/수정
    @PreAuthorize("@menuService.accessCheck(#userDetails.getUsername(), #menuId)")
    @GetMapping("/story/pastor/form")
    public String storyPastorForm(Map<String, Object> model,
                                  @RequestParam(value = "userId", defaultValue = "") String userId,
                                  @RequestParam(value = "pastorId", defaultValue = "") String pastorId,
                                  @RequestParam(value = "storyId", defaultValue = "0") int storyId,
                                  @RequestParam(value = "visitDate", defaultValue = "") String visitDate,
                                  @RequestParam(value = "menuId", defaultValue = "") int menuId,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        //메뉴 정보
        Menu menu = menuService.getMenu(menuId);
        model.put("menuInfo", menu);

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //선택된 유저의 스토리 정보
        PastorStory formStory = pastorStoryService.getStory(storyId);
        model.put("formStory", formStory);

        //선택된 유저의 정보
        User formUser = userService.getUser(userId);
        model.put("formUser", formUser);

        //선택한 목회자 ID
        model.put("formPastorId", pastorId);

        //선택한 스토리 날짜
        model.put("formVisitDate", visitDate);

        return "story_pastor_form";
    }

    //사역자 스토리 조회
    @PreAuthorize("@menuService.accessCheck(#userDetails.getUsername(), #menuId)")
    @GetMapping("/story/pastor/list")
    public String storyPastorList(Map<String, Object> model, @RequestParam(value = "menuId", defaultValue = "") int menuId, @AuthenticationPrincipal UserDetails userDetails) {
        //메뉴 정보
        Menu menu = menuService.getMenu(menuId);
        model.put("menuInfo", menu);

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //로그인한 유저의 그룹 상세 정보
        UserGroup formUserGroup = userGroupService.getUserGroup(user.getGroupId(), "N", "Y");
        model.put("formUserGroup", formUserGroup);

        //화면에 뿌릴 공통코드 조회
        model.put("roleList", codeService.findCodeChildList("USER_ROLE"));

        return "story_pastor_list";
    }

    //사역자 스토리 상세
    @PreAuthorize("@menuService.accessCheck(#userDetails.getUsername(), #menuId)")
    @GetMapping("/story/pastor/detail")
    public String storyPastorDetail(Map<String, Object> model,
                                     @RequestParam(value = "userId", defaultValue = "") String userId,
                                     @RequestParam(value = "menuId", defaultValue = "") int menuId,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        //메뉴 정보
        Menu menu = menuService.getMenu(menuId);
        model.put("menuInfo", menu);

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //선택된 유저의 정보
        User formUser = userService.getUser(userId);
        model.put("formUser", formUser);

        //목회자 리스트
        List<User> pastorList = userService.findUserSimpleList("", 0, 2, "");
        model.put("pastorList", pastorList);

        return "story_pastor_detail";
    }

    /*****공통 템플릿 페이지*****/
    @GetMapping("/com/menu")
    public String comMenu(Map<String, Object> model, @AuthenticationPrincipal UserDetails userDetails) {
        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        List<Map> menuList = new ArrayList<Map>();

        //최상위 메뉴 리스트 조회
        List<Menu> list = menuService.findMenuChildList(-1);
        for(int i = 0;i < list.size();i++){
            Menu menu = list.get(i);

            Map data = new HashMap<>();
            data.putAll(ConverObjectToMap(menu));

            List<Map> menuList2 = new ArrayList<Map>();
            List<Menu> list2 = menuService.findMenuChildList(menu.getMenuId());
            for(int j = 0;j < list2.size();j++){
                Menu menu2 = list2.get(j);

                Map data2 = new HashMap<>();
                data2.putAll(ConverObjectToMap(menu2));

                List<Map> menuList3 = new ArrayList<Map>();
                List<Menu> list3 = menuService.findMenuChildList(menu2.getMenuId());
                for(int k = 0;k < list3.size();k++){
                    Menu menu3 = list3.get(k);

                    Map data3 = new HashMap<>();
                    data3.putAll(ConverObjectToMap(menu3));

                    menuList3.add(data3);
                }
                data2.put("childData", list3);

                menuList2.add(data2);
            }
            data.put("childData", menuList2);

            menuList.add(data);
        }
        model.put("menuList", menuList);

        return "com_menu";
    }


    /*****관리 페이지*****/
    //사용자 관리
    @PreAuthorize("@menuService.accessCheck(#userDetails.getUsername(), #menuId)")
    @GetMapping("/user")
    public String user(Map<String, Object> model, @RequestParam(value = "menuId", defaultValue = "") int menuId, @AuthenticationPrincipal UserDetails userDetails) {
        //메뉴 정보
        Menu menu = menuService.getMenu(menuId);
        model.put("menuInfo", menu);

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //화면에 뿌릴 공통코드 조회
        model.put("statusList", codeService.findCodeChildList("USER_STATUS"));
        model.put("roleList", codeService.findCodeChildList("USER_ROLE"));

        return "user";
    }

    //사용자 관리 > 등록수정
    @PreAuthorize("@menuService.accessCheck(#userDetails.getUsername(), #menuId)")
    @GetMapping("/user/form")
    public String userForm(Map<String, Object> model, @RequestParam(value = "userId", defaultValue = "") String userId, @RequestParam(value = "menuId", defaultValue = "") int menuId, @AuthenticationPrincipal UserDetails userDetails) {

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //선택된 유저의 정보
        User formUser = userService.getUser(userId);
        model.put("formUser", formUser);

        if(formUser != null) {
            //선택된 유저의 그룹 상세 정보
            UserGroup formUserGroup = userGroupService.getUserGroup(formUser.getGroupId(), "N", "Y");
            model.put("formUserGroup", formUserGroup);
        }

        //화면에 뿌릴 공통코드 조회
        model.put("statusList", codeService.findCodeChildList("USER_STATUS"));
        model.put("roleList", codeService.findCodeChildList("USER_ROLE"));

        return "user_form";
    }

    //사용자 그룹 관리
    @PreAuthorize("@menuService.accessCheck(#userDetails.getUsername(), #menuId)")
    @GetMapping("/user/group")
    public String userGroup(Map<String, Object> model, @RequestParam(value = "menuId", defaultValue = "") int menuId, @AuthenticationPrincipal UserDetails userDetails) {
        //메뉴 정보
        Menu menu = menuService.getMenu(menuId);
        model.put("menuInfo", menu);

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        return "user_group";
    }

    //사용자 권한 관리
    @PreAuthorize("@menuService.accessCheck(#userDetails.getUsername(), #menuId)")
    @GetMapping("/user/role")
    public String permission(Map<String, Object> model, @RequestParam(value = "menuId", defaultValue = "") int menuId, @AuthenticationPrincipal UserDetails userDetails) {
        //메뉴 정보
        Menu menu = menuService.getMenu(menuId);
        model.put("menuInfo", menu);

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        return "user_role";
    }

    //메뉴 관리
    @PreAuthorize("@menuService.accessCheck(#userDetails.getUsername(), #menuId)")
    @GetMapping("/menu")
    public String menu(Map<String, Object> model, @RequestParam(value = "menuId", defaultValue = "") int menuId, @AuthenticationPrincipal UserDetails userDetails) {
        //메뉴 정보
        Menu menu = menuService.getMenu(menuId);
        model.put("menuInfo", menu);

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        //화면에 뿌릴 공통코드 조회
        model.put("roleList", codeService.findCodeChildList("USER_ROLE"));

        return "menuMgm";
    }

    //공통코드 관리
    @PreAuthorize("@menuService.accessCheck(#userDetails.getUsername(), #menuId)")
    @GetMapping("/common/code")
    public String commonCode(Map<String, Object> model, @RequestParam(value = "menuId", defaultValue = "") int menuId, @AuthenticationPrincipal UserDetails userDetails) {
        //메뉴 정보
        Menu menu = menuService.getMenu(menuId);
        model.put("menuInfo", menu);

        //로그인한 유저의 정보
        User user = userService.getUser(userDetails.getUsername());
        model.put("userInfo", user);

        return "common_code";
    }

    public static Map ConverObjectToMap(Object obj){
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            Map resultMap = new HashMap();

            for(int i=0; i<=fields.length-1;i++){
                fields[i].setAccessible(true);
                resultMap.put(fields[i].getName(), fields[i].get(obj));
            }
            return resultMap;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace(); } return null;
    }

    public boolean accessCheck(String userId, int menuId){
        return menuService.accessCheck(userId, menuId);
    }
}
