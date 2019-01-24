package net.healingchurch.story.services.story.pasture;

import net.healingchurch.story.domain.PastureStory;
import net.healingchurch.story.domain.User;

import java.util.List;
import java.util.Map;

public interface PastureStoryService {
    Map<Object, Object> findStoryList(String userId, String fromDate, String toDate, int page, int limit);

    int createStory(int worshipYn, int pastureMeetYn, int bibleCount, int qtCount, int fridayWorshipYn, int dawnPrayCount, String etc, String userId, int groupId, int parentGroupId, String prayers, int leaderYn, String inputDate, String worshipReason, String leaderReason);

    PastureStory getStory(int storyId);

    int updateStory(int storyId, int worshipYn, int pastureMeetYn, int bibleCount, int qtCount, int fridayWorshipYn, int dawnPrayCount, String etc, String userId, int groupId, int parentGroupId, String prayers, int leaderYn, String inputDate, String worshipReason, String leaderReason);

    void removeStory(int storyId, int groupId, int parentGroupId, String inputDate);

    Map<Object, Object> findUserStoryList(String userId, int groupId, int roleId, String userName, String inputDate, int page, int limit);
}
