package net.healingchurch.story.services.story.pastor;

import net.healingchurch.story.domain.PastorStory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/services/story/pastor")
public class PastorStoryController {

    @Autowired
    private PastorStoryService pastorStoryService;

    @GetMapping("list")
    public List<PastorStory> findStoryList(
            @RequestParam(value = "pastorId", required = true, defaultValue = "") String pastorId,
            @RequestParam(value = "fromDate", defaultValue = "") String fromDate,
            @RequestParam(value = "toDate", defaultValue = "") String toDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "20") int limit) throws Exception {
        return pastorStoryService.findStoryList(pastorId, fromDate, toDate, page, limit);
    }

    @GetMapping("get")
    public PastorStory getStory(
            @RequestParam(value = "storyId", required = true, defaultValue = "0") int storyId) {
        return pastorStoryService.getStory(storyId);
    }

    @PostMapping("create")
    public int createStory(
            @RequestParam(value = "pastorId", required = true, defaultValue = "") String pastorId,
            @RequestParam(value = "fromDate", defaultValue = "") String visitUserId,
            @RequestParam(value = "toDate", defaultValue = "") String visitDate,
            @RequestParam(value = "toDate", defaultValue = "") String summary,
            @RequestParam(value = "toDate", defaultValue = "") String prayers,
            @RequestParam(value = "toDate", defaultValue = "") String etc
    ) throws Exception {
        return pastorStoryService.createStory(pastorId, visitUserId, visitDate, summary, prayers, etc);
    }

    @PostMapping("update")
    public int updateStory(
            @RequestParam(value = "storyId", required = true, defaultValue = "0") int storyId,
            @RequestParam(value = "pastorId", required = true, defaultValue = "") String pastorId,
            @RequestParam(value = "fromDate", defaultValue = "") String visitUserId,
            @RequestParam(value = "toDate", defaultValue = "") String visitDate,
            @RequestParam(value = "toDate", defaultValue = "") String summary,
            @RequestParam(value = "toDate", defaultValue = "") String prayers,
            @RequestParam(value = "toDate", defaultValue = "") String etc
    ) throws Exception {
        return pastorStoryService.updateStory(storyId, pastorId, visitUserId, visitDate, summary, prayers, etc);
    }

    @PostMapping("remove")
    public int removeStory(
            @RequestParam(value = "storyId", required = true, defaultValue = "0") int storyId) {
        pastorStoryService.removeStory(storyId);
        return 1;
    }

}
