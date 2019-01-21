package net.healingchurch.story.services.story.pasture;

import net.healingchurch.story.domain.PastureStory;
import net.healingchurch.story.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PastureStoryMapper {
    List<PastureStory> findStoryList(PastureStory pastorStory);

    int createStory(PastureStory pastorStory);

    PastureStory getStory(int storyId);

    int updateStory(PastureStory pastorStory);

    void removeStory(int storyId);

    List<PastureStory> findUserStoryList(PastureStory pastorStory);

    int findUserStoryListCnt(PastureStory pastorStory);

}
