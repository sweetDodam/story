package net.healingchurch.story.services.menu;

import net.healingchurch.story.domain.Menu;
import net.healingchurch.story.domain.User;
import net.healingchurch.story.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component("menuService")
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private UserService userService;

    @Override
    public List<Menu> findMenuList() {
        return menuMapper.findMenuList();
    }

    @Override
    public List<Menu> findMenuChildList(int parentMenuId) {
        return menuMapper.findMenuChildList(parentMenuId);
    }

    @Override
    public Menu getMenu(int menuId) {
        return menuMapper.getMenu(menuId);
    }

    @Override
    public int createMenu(int parentMenuId, String menuName, int menuLevel, int sortIdx, String menuUrl, boolean isAdmin, int roleId) {
        Menu menu = new Menu();

        menu.setParentMenuId(parentMenuId);
        menu.setMenuName(menuName);
        menu.setMenuLevel(menuLevel);
        menu.setSortIdx(sortIdx);
        menu.setMenuUrl(menuUrl);
        menu.setIsAdmin(isAdmin);
        menu.setRoleId(roleId);

        //로그인한 유저 아이디
        UserDetails principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        menu.setLoginUserId(principal.getUsername());

        return menuMapper.createMenu(menu);
    }

    @Override
    public int updateMenu(int menuId, int parentMenuId, String menuName, int menuLevel, int sortIdx, String menuUrl, boolean isAdmin, int roleId)  {
        Menu menu = new Menu();

        menu.setMenuId(menuId);
        menu.setParentMenuId(parentMenuId);
        menu.setMenuName(menuName);
        menu.setMenuLevel(menuLevel);
        menu.setSortIdx(sortIdx);
        menu.setMenuUrl(menuUrl);
        menu.setIsAdmin(isAdmin);
        menu.setRoleId(roleId);

        //로그인한 유저 아이디
        UserDetails principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        menu.setLoginUserId(principal.getUsername());

        return menuMapper.updateMenu(menu);
    }

    @Override
    public void removeMenu(int menuId) {
        menuMapper.removeMenu(menuId);
    }

    public boolean accessCheck(String userId, int menuId){
        User user = userService.getUser(userId);
        Menu menu = menuMapper.getMenu(menuId);

        int userRoleOrder = user.getRoleOrder();
        int menuRoleOrder = menu.getRoleOrder();

        if(userRoleOrder <= menuRoleOrder){
            return true;
        }

        return false;
    }
}
