package net.healingchurch.story.services.story.town;

import net.healingchurch.story.domain.TownStory;

import java.util.List;
import java.util.Map;

public interface TownStoryService {
    Map<Object, Object> findStoryList(int groupId, String inputDate);

    TownStory getStory(int storyId);

    TownStory getStoryByGroup(String groupId, int parentGroupId, String inputDate);

    void townStorySum(String groupId, int parentGroupId, String inputDate);

    int updateStory(int storyId, String leaderCareStory, String pastureCareStory);

    void removeStory(int storyId);

    Map<Object, Object> findUserStoryList(String userId, int groupId, int roleId, String userName, String inputDate, int page, int limit);
}
