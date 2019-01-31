package net.healingchurch.story.services.story.town;

import net.healingchurch.story.domain.TownStory;
import net.healingchurch.story.services.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@EnableAutoConfiguration
@RequestMapping("/services/story/town")
public class TownStoryController {

    @Autowired
    private TownStoryService townStoryService;

    @GetMapping("list")
    public Map<Object, Object> findStoryList(
            @RequestParam(value = "groupId", required = true, defaultValue = "") int groupId,
            @RequestParam(value = "inputDate", defaultValue = "") String inputDate) throws Exception {
        return townStoryService.findStoryList(groupId, inputDate);
    }

    @GetMapping("get")
    public TownStory getStory(
            @RequestParam(value = "storyId", required = true, defaultValue = "0") int storyId) {
        return townStoryService.getStory(storyId);
    }

    @PostMapping("createUpdate")
    public int updateStory(
            @RequestParam(value = "storyId", defaultValue = "0") int storyId,
            @RequestParam(value = "townStoryId", defaultValue = "0") int townStoryId,
            @RequestParam(value = "leaderCareStory", defaultValue = "") String leaderCareStory,
            @RequestParam(value = "pastureCareStory", defaultValue = "") String pastureCareStory
    ) throws Exception {

        if(townStoryId == 0){
            townStoryId = townStoryService.createStory(storyId, leaderCareStory, pastureCareStory);
        }else{
            townStoryService.updateStory(storyId, leaderCareStory, pastureCareStory);
        }

        return townStoryId;
    }

    @PostMapping("remove")
    public int removeStory(
            @RequestParam(value = "storyId", required = true, defaultValue = "0") int storyId) {
        townStoryService.removeStory(storyId);
        return 1;
    }

    @GetMapping("storyList")
    public Map<Object, Object> findUserStoryList(@RequestParam(value = "userId", defaultValue = "") String userId,
                                                 @RequestParam(value = "groupId", defaultValue = "0") int groupId,
                                                 @RequestParam(value = "roleId", defaultValue = "0") int roleId,
                                                 @RequestParam(value = "userName", defaultValue = "") String userName,
                                                 @RequestParam(value = "inputDate", defaultValue = "") String inputDate,
                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "limit", defaultValue = "20") int limit) {
        return townStoryService.findUserStoryList(userId, groupId, roleId, userName, inputDate, page, limit);
    }
}
