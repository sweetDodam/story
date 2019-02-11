package net.healingchurch.story.services.story.pastor;

import net.healingchurch.story.domain.PastorStory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PastorStoryMapper {
    List<PastorStory> findStoryList(PastorStory pastorStory);

    int findStoryListCnt(PastorStory pastorStory);

    int createStory(PastorStory pastorStory);

    PastorStory getStory(int storyId);

    int updateStory(PastorStory pastorStory);

    void removeStory(int storyId);

    List<PastorStory> findUserStoryList(PastorStory pastorStory);

    int findUserStoryListCnt(PastorStory pastorStory);
}
