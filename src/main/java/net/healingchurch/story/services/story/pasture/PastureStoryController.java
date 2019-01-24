package net.healingchurch.story.services.story.pasture;

import net.healingchurch.story.domain.PastureStory;
import net.healingchurch.story.domain.User;
import net.healingchurch.story.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@EnableAutoConfiguration
@RequestMapping("/services/story/pasture")
public class PastureStoryController {

    @Autowired
    private PastureStoryService pastureStoryService;

    @GetMapping("list")
    public Map<Object, Object> findStoryList(
            @RequestParam(value = "userId", required = true, defaultValue = "") String userId,
            @RequestParam(value = "fromDate", defaultValue = "") String fromDate,
            @RequestParam(value = "toDate", defaultValue = "") String toDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "20") int limit) throws Exception {
        return pastureStoryService.findStoryList(userId, fromDate, toDate, page, limit);
    }

    @GetMapping("get")
    public PastureStory getStory(
            @RequestParam(value = "storyId", required = true, defaultValue = "0") int storyId) {
        return pastureStoryService.getStory(storyId);
    }

    @PostMapping("create")
    public int createStory(
            @RequestParam(value = "worshipYn", defaultValue = "") int worshipYn,
            @RequestParam(value = "pastureMeetYn", defaultValue = "") int pastureMeetYn,
            @RequestParam(value = "bibleCount", defaultValue = "0") int bibleCount,
            @RequestParam(value = "qtCount", defaultValue = "0") int qtCount,
            @RequestParam(value = "fridayWorshipYn", defaultValue = "") int fridayWorshipYn,
            @RequestParam(value = "dawnPrayCount", defaultValue = "0") int dawnPrayCount,
            @RequestParam(value = "etc", defaultValue = "") String etc,
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "groupId", defaultValue = "") int groupId,
            @RequestParam(value = "parentGroupId", defaultValue = "") int parentGroupId,
            @RequestParam(value = "prayers", defaultValue = "") String prayers,
            @RequestParam(value = "leaderYn", defaultValue = "") int leaderYn,
            @RequestParam(value = "inputDate", defaultValue = "") String inputDate,
            @RequestParam(value = "worshipReason", defaultValue = "") String worshipReason,
            @RequestParam(value = "leaderReason", defaultValue = "") String leaderReason
    ) throws Exception {
        return pastureStoryService.createStory(worshipYn, pastureMeetYn, bibleCount, qtCount, fridayWorshipYn, dawnPrayCount, etc, userId, groupId, parentGroupId, prayers, leaderYn, inputDate, worshipReason, leaderReason);
    }

    @PostMapping("update")
    public int updateStory(
            @RequestParam(value = "storyId", required = true, defaultValue = "") int storyId,
            @RequestParam(value = "worshipYn", defaultValue = "false") int worshipYn,
            @RequestParam(value = "pastureMeetYn", defaultValue = "false") int pastureMeetYn,
            @RequestParam(value = "bibleCount", defaultValue = "0") int bibleCount,
            @RequestParam(value = "qtCount", defaultValue = "0") int qtCount,
            @RequestParam(value = "fridayWorshipYn", defaultValue = "false") int fridayWorshipYn,
            @RequestParam(value = "dawnPrayCount", defaultValue = "0") int dawnPrayCount,
            @RequestParam(value = "etc", defaultValue = "") String etc,
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "groupId", defaultValue = "") int groupId,
            @RequestParam(value = "parentGroupId", defaultValue = "") int parentGroupId,
            @RequestParam(value = "prayers", defaultValue = "") String prayers,
            @RequestParam(value = "leaderYn", defaultValue = "false") int leaderYn,
            @RequestParam(value = "inputDate", defaultValue = "") String inputDate,
            @RequestParam(value = "worshipReason", defaultValue = "") String worshipReason,
            @RequestParam(value = "leaderReason", defaultValue = "") String leaderReason
    ) throws Exception {
        return pastureStoryService.updateStory(storyId, worshipYn, pastureMeetYn, bibleCount, qtCount, fridayWorshipYn, dawnPrayCount, etc, userId, groupId, parentGroupId, prayers, leaderYn, inputDate, worshipReason, leaderReason);
    }

    @PostMapping("remove")
    public int removeStory(
            @RequestParam(value = "storyId", required = true, defaultValue = "0") int storyId,
            @RequestParam(value = "groupId", defaultValue = "") int groupId,
            @RequestParam(value = "parentGroupId", defaultValue = "") int parentGroupId,
            @RequestParam(value = "inputDate", defaultValue = "") String inputDate
    ) {
        pastureStoryService.removeStory(storyId, groupId, parentGroupId, inputDate);
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
        return pastureStoryService.findUserStoryList(userId, groupId, roleId, userName, inputDate, page, limit);
    }
}
