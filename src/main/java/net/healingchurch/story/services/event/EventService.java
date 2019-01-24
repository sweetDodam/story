package net.healingchurch.story.services.event;

import net.healingchurch.story.domain.Event;

import java.util.List;

public interface EventService {
    List<Event> findEventList(int groupId, String fromDate, String toDate, int page, int limit);

    Event getEvent(int eventId);

    int createEvent(int groupId, String eventContent, String writer, String eventDate);

    int updateEvent(int eventId, int groupId, String eventContent, String writer, String eventDate);

    void removeEvent(int eventId);
}
