package net.healingchurch.story.services.story.town;

import net.healingchurch.story.domain.TownStory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TownStoryMapper {
    List<TownStory> findStoryList(TownStory townStory);

    int createStory(TownStory townStory);

    TownStory getStory(int storyId);

    TownStory getStoryByGroup(TownStory townStory);

    int updateStorySum(TownStory townStory);

    int updateStory(TownStory townStory);

    void removeStory(int storyId);

    List<TownStory> findUserStoryList(TownStory townStory);

    int findUserStoryListCnt(TownStory townStory);
}
