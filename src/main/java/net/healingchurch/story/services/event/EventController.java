package net.healingchurch.story.services.event;

import net.healingchurch.story.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/services/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("list")
    public List<Event> findStoryList(
            @RequestParam(value = "groupId", defaultValue = "") int groupId,
            @RequestParam(value = "fromDate", defaultValue = "") String fromDate,
            @RequestParam(value = "toDate", defaultValue = "") String toDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "20") int limit) throws Exception {
        return eventService.findEventList(groupId, fromDate, toDate, page, limit);
    }

    @GetMapping("get")
    public Event getEvent(
            @RequestParam(value = "eventId", required = true, defaultValue = "0") int eventId) {
        return eventService.getEvent(eventId);
    }

    @PostMapping("create")
    public int createEvent(
            @RequestParam(value = "groupId", defaultValue = "") int groupId,
            @RequestParam(value = "eventContent", defaultValue = "") String eventContent,
            @RequestParam(value = "writer", defaultValue = "") String writer,
            @RequestParam(value = "eventDate", defaultValue = "") String eventDate
    ) throws Exception {
        return eventService.createEvent(groupId, eventContent, writer, eventDate);
    }

    @PostMapping("update")
    public int updateStory(
            @RequestParam(value = "eventId", defaultValue = "") int eventId,
            @RequestParam(value = "groupId", defaultValue = "") int groupId,
            @RequestParam(value = "eventContent", defaultValue = "") String eventContent,
            @RequestParam(value = "writer", defaultValue = "") String writer,
            @RequestParam(value = "eventDate", defaultValue = "") String eventDate
    ) throws Exception {
        return eventService.updateEvent(eventId, groupId, eventContent, writer, eventDate);
    }

    @PostMapping("remove")
    public int removeStory(
            @RequestParam(value = "eventId", required = true, defaultValue = "0") int eventId) {
        eventService.removeEvent(eventId);
        return 1;
    }

}
