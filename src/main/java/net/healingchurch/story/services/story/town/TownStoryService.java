package net.healingchurch.story.services.story.town;

import net.healingchurch.story.domain.TownStory;

import java.util.List;

public interface TownStoryService {
    List<TownStory> findStoryList(int groupId, String fromDate, String toDate, int page, int limit);

    TownStory getStory(int storyId);

    TownStory getStoryByGroup(String groupId, int parentGroupId, String inputDate);

    void townStorySum(String groupId, int parentGroupId, String inputDate);

    int updateStory(int storyId, String userId, int groupId, String leaderCareStory, String townCareStroy);

    void removeStory(int storyId);
}
