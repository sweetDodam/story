package net.healingchurch.story.services.event;

import net.healingchurch.story.domain.Event;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EventMapper {
    List<Event> findEventList(Event event);

    int createEvent(Event event);

    Event getEvent(int eventId);

    int updateEvent(Event event);

    void removeEvent(int eventId);

    Event getEventByGroup(Event event);
}
