package net.healingchurch.story.services.story.pastor;

import net.healingchurch.story.domain.PastorStory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@EnableAutoConfiguration
@RequestMapping("/services/story/pastor")
public class PastorStoryController {

    @Autowired
    private PastorStoryService pastorStoryService;

    @GetMapping("list")
    public Map<Object, Object> findStoryList(
            @RequestParam(value = "visitUserId", required = true, defaultValue = "") String visitUserId,
            @RequestParam(value = "pastorId", defaultValue = "") String pastorId,
            @RequestParam(value = "fromDate", defaultValue = "") String fromDate,
            @RequestParam(value = "toDate", defaultValue = "") String toDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "20") int limit) throws Exception {
        return pastorStoryService.findStoryList(visitUserId, pastorId, fromDate, toDate, page, limit);
    }

    @GetMapping("get")
    public PastorStory getStory(
            @RequestParam(value = "storyId", required = true, defaultValue = "0") int storyId) {
        return pastorStoryService.getStory(storyId);
    }

    @PostMapping("create")
    public int createStory(
            @RequestParam(value = "pastorId", required = true, defaultValue = "") String pastorId,
            @RequestParam(value = "visitUserId", defaultValue = "") String visitUserId,
            @RequestParam(value = "visitDate", defaultValue = "") String visitDate,
            @RequestParam(value = "visitPlace", defaultValue = "") String visitPlace,
            @RequestParam(value = "summary", defaultValue = "") String summary,
            @RequestParam(value = "prayers", defaultValue = "") String prayers,
            @RequestParam(value = "etc", defaultValue = "") String etc
    ) throws Exception {
        return pastorStoryService.createStory(pastorId, visitUserId, visitDate, visitPlace, summary, prayers, etc);
    }

    @PostMapping("update")
    public int updateStory(
            @RequestParam(value = "storyId", required = true, defaultValue = "0") int storyId,
            @RequestParam(value = "pastorId", required = true, defaultValue = "") String pastorId,
            @RequestParam(value = "visitUserId", defaultValue = "") String visitUserId,
            @RequestParam(value = "visitDate", defaultValue = "") String visitDate,
            @RequestParam(value = "visitPlace", defaultValue = "") String visitPlace,
            @RequestParam(value = "summary", defaultValue = "") String summary,
            @RequestParam(value = "prayers", defaultValue = "") String prayers,
            @RequestParam(value = "etc", defaultValue = "") String etc
    ) throws Exception {
        return pastorStoryService.updateStory(storyId, pastorId, visitUserId, visitDate, visitPlace, summary, prayers, etc);
    }

    @PostMapping("remove")
    public int removeStory(
            @RequestParam(value = "storyId", required = true, defaultValue = "0") int storyId) {
        pastorStoryService.removeStory(storyId);
        return 1;
    }


    @GetMapping("storyList")
    public Map<Object, Object> findUserStoryList(@RequestParam(value = "userId", defaultValue = "") String userId,
                                                 @RequestParam(value = "groupId", defaultValue = "0") int groupId,
                                                 @RequestParam(value = "roleId", defaultValue = "0") int roleId,
                                                 @RequestParam(value = "userName", defaultValue = "") String userName,
                                                 @RequestParam(value = "visitDate", defaultValue = "") String visitDate,
                                                 @RequestParam(value = "pastorId", defaultValue = "") String pastorId,
                                                 @RequestParam(value = "isReserve", defaultValue = "") String isReserve,
                                                 @RequestParam(value = "isConfirm", defaultValue = "") String isConfirm,
                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "limit", defaultValue = "20") int limit) {
        return pastorStoryService.findUserStoryList(userId, groupId, roleId, userName, visitDate, pastorId, isReserve, isConfirm, page, limit);
    }
}
