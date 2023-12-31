package net.healingchurch.story.services.story.pastor;

import net.healingchurch.story.domain.PastorStory;

import java.util.List;
import java.util.Map;

public interface PastorStoryService {
    Map<Object, Object> findStoryList(String visitUserId, String pastorId, String fromDate, String toDate, int page, int limit);

    int createStory(String pastorId, String visitUserId, String visitDate, String visitPlace, String summary, String prayers, String etc);

    PastorStory getStory(int storyId);

    int updateStory(int storyId, String pastorId, String visitUserId, String visitDate, String visitPlace, String summary, String prayers, String etc);

    void removeStory(int storyId);

    Map<Object, Object> findUserStoryList(String userId, int groupId, int roleId, String userName, String visitDate, String pastorId, String isReserve, String isConfirm, int page, int limit);
}
