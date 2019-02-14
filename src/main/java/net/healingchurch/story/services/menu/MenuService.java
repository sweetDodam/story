package net.healingchurch.story.services.menu;

import net.healingchurch.story.domain.Menu;
import java.util.List;

public interface MenuService {
    List<Menu> findMenuList();

    List<Menu> findMenuChildList(int parentMenuId);

    Menu getMenu(int menuId);

    int createMenu(int parentMenuId, String menuName, int menuLevel, int sortIdx, String menuUrl, boolean isAdmin, int roleId);

    int updateMenu(int menuId, int parentMenuId, String menuName, int menuLevel, int sortIdx, String menuUrl, boolean isAdmin, int roleId);

    void removeMenu(int menuId);
}
