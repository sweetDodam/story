package net.healingchurch.story.services.menu;

import net.healingchurch.story.domain.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/services/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("list")
    public List<Menu> findMenuList() {
        return menuService.findMenuList();
    }

    @GetMapping("childList")
    public List<Menu> findMenuChildList(@RequestParam(value = "parentMenuId", defaultValue = "") int parentMenuId) {
        return menuService.findMenuChildList(parentMenuId);
    }

    @GetMapping("get")
    public Menu getMenu(@RequestParam(value = "menuId", required = true, defaultValue = "0") int menuId) {
        return menuService.getMenu(menuId);
    }

    @PostMapping("create")
    public int createMenu(
            @RequestParam(value = "parentMenuId", defaultValue = "0") int parentMenuId,
            @RequestParam(value = "menuName", defaultValue = "") String menuName,
            @RequestParam(value = "menuLevel", defaultValue = "0") int menuLevel,
            @RequestParam(value = "sortIdx", defaultValue = "0") int sortIdx,
            @RequestParam(value = "menuUrl", defaultValue = "") String menuUrl
    ) throws Exception {
        return menuService.createMenu(parentMenuId, menuName, menuLevel, sortIdx, menuUrl);
    }

    @PostMapping("update")
    public int updateMenu(
            @RequestParam(value = "menuId", defaultValue = "0") int menuId,
            @RequestParam(value = "parentMenuId", defaultValue = "0") int parentMenuId,
            @RequestParam(value = "menuName", defaultValue = "") String menuName,
            @RequestParam(value = "menuLevel", defaultValue = "0") int menuLevel,
            @RequestParam(value = "sortIdx", defaultValue = "0") int sortIdx,
            @RequestParam(value = "menuUrl", defaultValue = "") String menuUrl
    ) throws Exception {
        return menuService.updateMenu(menuId, parentMenuId, menuName, menuLevel, sortIdx, menuUrl);
    }

    @PostMapping("remove")
    public int removeMenu(
            @RequestParam(value = "menuId", required = true, defaultValue = "0") int menuId) {
        menuService.removeMenu(menuId);
        return 1;
    }
}
