package net.healingchurch.story.services.menu;

import net.healingchurch.story.domain.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<Menu> findMenuList();

    List<Menu> findMenuChildList(int parentMenuId);

    Menu getMenu(int menuId);

    int createMenu(Menu menu);

    int updateMenu(Menu menu);

    void removeMenu(int menuId);
}
