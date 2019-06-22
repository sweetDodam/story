package net.healingchurch.story.services.story.pasture;

import net.healingchurch.story.domain.PastureStory;
import net.healingchurch.story.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PastureStoryMapper {
    List<PastureStory> findStoryList(PastureStory pastureStory);

    int findStoryListCnt(PastureStory pastureStory);

    int createStory(PastureStory pastureStory);

    int createStorySub(PastureStory pastureStory);

    PastureStory getStory(int storyId);

    int updateStory(PastureStory pastureStory);

    int updateStorySub(PastureStory pastureStory);

    void removeStory(int storyId);

    List<PastureStory> findUserStoryList(PastureStory pastureStory);

    int findUserStoryListCnt(PastureStory pastureStory);

    PastureStory getStoryId(PastureStory pastureStory);
}
