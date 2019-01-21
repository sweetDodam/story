package net.healingchurch.story.services.story.pasture;

import net.healingchurch.story.domain.PastureStory;
import net.healingchurch.story.domain.User;
import net.healingchurch.story.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@EnableAutoConfiguration
@RequestMapping("/services/story/pasture")
public class PastureStoryController {

    @Autowired
    private PastureStoryService pastureStoryService;

    @GetMapping("list")
    public List<PastureStory> findStoryList(
            @RequestParam(value = "groupId", required = true, defaultValue = "") int groupId,
            @RequestParam(value = "fromDate", defaultValue = "") String fromDate,
            @RequestParam(value = "toDate", defaultValue = "") String toDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "20") int limit) throws Exception {
        return pastureStoryService.findStoryList(groupId, fromDate, toDate, page, limit);
    }

    @GetMapping("get")
    public PastureStory getStory(
            @RequestParam(value = "storyId", required = true, defaultValue = "0") int storyId) {
        return pastureStoryService.getStory(storyId);
    }

    @PostMapping("create")
    public int createStory(
            @RequestParam(value = "worshipYn", defaultValue = "") boolean worshipYn,
            @RequestParam(value = "pastureMeetYn", defaultValue = "") boolean pastureMeetYn,
            @RequestParam(value = "bibleCount", defaultValue = "0") int bibleCount,
            @RequestParam(value = "qtCount", defaultValue = "0") int qtCount,
            @RequestParam(value = "fridayWorshipYn", defaultValue = "") boolean fridayWorshipYn,
            @RequestParam(value = "dawnPrayCount", defaultValue = "0") int dawnPrayCount,
            @RequestParam(value = "etc", defaultValue = "") String etc,
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "groupId", defaultValue = "") int groupId,
            @RequestParam(value = "prayers", defaultValue = "") String prayers,
            @RequestParam(value = "leaderYn", defaultValue = "") boolean leaderYn,
            @RequestParam(value = "inputDate", defaultValue = "") String inputDate,
            @RequestParam(value = "worshipReason", defaultValue = "") String worshipReason,
            @RequestParam(value = "leaderReason", defaultValue = "") String leaderReason
    ) throws Exception {
        return pastureStoryService.createStory(worshipYn, pastureMeetYn, bibleCount, qtCount, fridayWorshipYn, dawnPrayCount, etc, userId, groupId, prayers, leaderYn, inputDate, worshipReason, leaderReason);
    }

    @PostMapping("update")
    public int updateStory(
            @RequestParam(value = "storyId", required = true, defaultValue = "") int storyId,
            @RequestParam(value = "worshipYn", defaultValue = "false") boolean worshipYn,
            @RequestParam(value = "pastureMeetYn", defaultValue = "false") boolean pastureMeetYn,
            @RequestParam(value = "bibleCount", defaultValue = "0") int bibleCount,
            @RequestParam(value = "qtCount", defaultValue = "0") int qtCount,
            @RequestParam(value = "fridayWorshipYn", defaultValue = "false") boolean fridayWorshipYn,
            @RequestParam(value = "dawnPrayCount", defaultValue = "0") int dawnPrayCount,
            @RequestParam(value = "etc", defaultValue = "") String etc,
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "groupId", defaultValue = "") int groupId,
            @RequestParam(value = "prayers", defaultValue = "") String prayers,
            @RequestParam(value = "leaderYn", defaultValue = "false") boolean leaderYn,
            @RequestParam(value = "worshipReason", defaultValue = "") String worshipReason,
            @RequestParam(value = "leaderReason", defaultValue = "") String leaderReason
    ) throws Exception {
        return pastureStoryService.updateStory(storyId, worshipYn, pastureMeetYn, bibleCount, qtCount, fridayWorshipYn, dawnPrayCount, etc, userId, groupId, prayers, leaderYn, worshipReason, leaderReason);
    }

    @PostMapping("remove")
    public int removeStory(
            @RequestParam(value = "storyId", required = true, defaultValue = "0") int storyId) {
        pastureStoryService.removeStory(storyId);
        return 1;
    }

    @GetMapping("storyList")
    public List<PastureStory> findUserStoryList(@RequestParam(value = "userId", defaultValue = "") String userId,
                                   @RequestParam(value = "groupId", defaultValue = "0") int groupId,
                                    @RequestParam(value = "fromDate", defaultValue = "") String fromDate,
                                    @RequestParam(value = "toDate", defaultValue = "") String toDate,
                                    @RequestParam(value = "inputDate", defaultValue = "") String inputDate,
                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "20") int limit) {

        return pastureStoryService.findUserStoryList(userId, groupId, fromDate, toDate, inputDate, page, limit);
    }

    @GetMapping("storyListCnt")
    public int findUserStoryListCnt(Map<String, Object> model,
                               @RequestParam(value = "userId", defaultValue = "") String userId,
                               @RequestParam(value = "groupId", defaultValue = "0") int groupId,
                               @RequestParam(value = "fromDate", defaultValue = "") String fromDate,
                               @RequestParam(value = "toDate", defaultValue = "") String toDate,
                               @RequestParam(value = "inputDate", defaultValue = "") String inputDate) {

        return pastureStoryService.findUserStoryListCnt(userId, groupId, fromDate, toDate, inputDate);
    }
}
