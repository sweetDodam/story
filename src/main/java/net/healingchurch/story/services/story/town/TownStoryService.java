package net.healingchurch.story.services.story.town;

import net.healingchurch.story.domain.TownStory;

import java.util.List;

public interface TownStoryService {
    List<TownStory> findStoryList(int groupId, String fromDate, String toDate, int page, int limit);

    int createStory(boolean worshipYn, boolean pastureMeetYn, int bibleCount, int qtCount, boolean fridayWorshipYn, int dawnPrayCount, String etc, String userId, int groupId, String leaderCareStory, String townCareStroy);

    TownStory getStory(int storyId);

    int updateStory(int storyId, boolean worshipYn, boolean pastureMeetYn, int bibleCount, int qtCount, boolean fridayWorshipYn, int dawnPrayCount, String etc, String userId, int groupId, String leaderCareStory, String townCareStroy);

    void removeStory(int storyId);
}
