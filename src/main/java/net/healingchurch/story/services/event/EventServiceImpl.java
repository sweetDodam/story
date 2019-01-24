package net.healingchurch.story.services.event;

import net.healingchurch.story.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventMapper eventMapper;

    @Override
    //@PreAuthorize("hasAnyRole('ROLE_TOWN_MANAGER', 'ROLE_PASTER')")
    public List<Event> findEventList(int groupId, String fromDate, String toDate, int page, int limit) {
        Event event = new Event();
        event.setGroupId(groupId);
        event.setFromDate(fromDate);
        event.setToDate(toDate);
        event.setPage(page);
        event.setOffset((page-1)*limit);
        event.setLimit(limit);
        return eventMapper.findEventList(event);
    }

    @Override
    //@PreAuthorize("hasAnyRole('ROLE_TOWN_MANAGER')")
    public int createEvent(int groupId, String eventContent, String writer, String eventDate) {
        Event event = new Event();
        event.setGroupId(groupId);
        event.setEventContent(eventContent);
        event.setWriter(writer);
        event.setEventDate(eventDate);
        return eventMapper.createEvent(event);
    }

    @Override
    //@PreAuthorize("hasAnyRole('ROLE_TOWN_MANAGER', 'ROLE_PASTER')")
    public Event getEvent(int eventId) {
        return eventMapper.getEvent(eventId);
    }

    @Override
    //@PreAuthorize("hasAnyRole('ROLE_TOWN_MANAGER')")
    public int updateEvent(int eventId, int groupId, String eventContent, String writer, String eventDate) {
        Event event = new Event();
        event.setEventId(eventId);
        event.setGroupId(groupId);
        event.setEventContent(eventContent);
        event.setWriter(writer);
        event.setEventDate(eventDate);
        return eventMapper.updateEvent(event);
    }

    @Override
    //@PreAuthorize("hasAnyRole('ROLE_TOWN_MANAGER')")
    public void removeEvent(int storyId) {
        eventMapper.removeEvent(storyId);
    }

}
