package net.healingchurch.story.services.story.pasture;

import net.healingchurch.story.domain.PastureStory;
import net.healingchurch.story.domain.User;

import java.util.List;

public interface PastureStoryService {
    List<PastureStory> findStoryList(int groupId, String fromDate, String toDate, int page, int limit);

    int createStory(boolean worshipYn, boolean pastureMeetYn, int bibleCount, int qtCount, boolean fridayWorshipYn, int dawnPrayCount, String etc, String userId, int groupId, String prayers, boolean leaderYn, String inputDate, String worshipReason, String leaderReason);

    PastureStory getStory(int storyId);

    int updateStory(int storyId, boolean worshipYn, boolean pastureMeetYn, int bibleCount, int qtCount, boolean fridayWorshipYn, int dawnPrayCount, String etc, String userId, int groupId, String prayers, boolean leaderYn, String worshipReason, String leaderReason);

    void removeStory(int storyId);

    List<PastureStory> findUserStoryList(String userId, int groupId, String fromDate, String toDate, String inputDate, int page, int limit);

    int findUserStoryListCnt(String userId, int groupId, String fromDate, String toDate, String inputDate);
}
