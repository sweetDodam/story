package net.healingchurch.story.services.story.town;

import net.healingchurch.story.domain.TownStory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/services/story/town")
public class TownStoryController {

    @Autowired
    private TownStoryService townStoryService;

    @GetMapping("list")
    public List<TownStory> findStoryList(
            @RequestParam(value = "groupId", required = true, defaultValue = "") int groupId,
            @RequestParam(value = "fromDate", defaultValue = "") String fromDate,
            @RequestParam(value = "toDate", defaultValue = "") String toDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "20") int limit) throws Exception {
        return townStoryService.findStoryList(groupId, fromDate, toDate, page, limit);
    }

    @GetMapping("get")
    public TownStory getStory(
            @RequestParam(value = "storyId", required = true, defaultValue = "0") int storyId) {
        return townStoryService.getStory(storyId);
    }

    @PostMapping("create")
    public int createStory(
            @RequestParam(value = "worshipYn", required = true, defaultValue = "") boolean worshipYn,
            @RequestParam(value = "pastureMeetYn", defaultValue = "") boolean pastureMeetYn,
            @RequestParam(value = "bibleCount", defaultValue = "") int bibleCount,
            @RequestParam(value = "qtCount", defaultValue = "") int qtCount,
            @RequestParam(value = "fridayWorshipYn", defaultValue = "") boolean fridayWorshipYn,
            @RequestParam(value = "dawnPrayCount", defaultValue = "") int dawnPrayCount,
            @RequestParam(value = "etc", defaultValue = "") String etc,
            @RequestParam(value = "userId", defaultValue = "") String userId,
            @RequestParam(value = "groupId", defaultValue = "") int groupId,
            @RequestParam(value = "leaderCareStory", defaultValue = "") String leaderCareStory,
            @RequestParam(value = "townCareStroy", defaultValue = "") String townCareStroy
    ) throws Exception {
        return townStoryService.createStory(worshipYn, pastureMeetYn, bibleCount, qtCount, fridayWorshipYn, dawnPrayCount, etc, userId, groupId, leaderCareStory, townCareStroy);
    }

    @PostMapping("update")
    public int updateStory(
            @RequestParam(value = "storyId", required = true, defaultValue = "") int storyId,
            @RequestParam(value = "pastorId", required = true, defaultValue = "") boolean worshipYn,
            @RequestParam(value = "pastureMeetYn", defaultValue = "") boolean pastureMeetYn,
            @RequestParam(value = "bibleCount", defaultValue = "") int bibleCount,
            @RequestParam(value = "qtCount", defaultValue = "") int qtCount,
            @RequestParam(value = "fridayWorshipYn", defaultValue = "") boolean fridayWorshipYn,
            @RequestParam(value = "dawnPrayCount", defaultValue = "") int dawnPrayCount,
            @RequestParam(value = "etc", defaultValue = "") String etc,
            @RequestParam(value = "etc", defaultValue = "") String userId,
            @RequestParam(value = "etc", defaultValue = "") int groupId,
            @RequestParam(value = "leaderCareStory", defaultValue = "") String leaderCareStory,
            @RequestParam(value = "townCareStroy", defaultValue = "") String townCareStroy
    ) throws Exception {
        return townStoryService.updateStory(storyId, worshipYn, pastureMeetYn, bibleCount, qtCount, fridayWorshipYn, dawnPrayCount, etc, userId, groupId, leaderCareStory, townCareStroy);
    }

    @PostMapping("remove")
    public int removeStory(
            @RequestParam(value = "storyId", required = true, defaultValue = "0") int storyId) {
        townStoryService.removeStory(storyId);
        return 1;
    }

}
