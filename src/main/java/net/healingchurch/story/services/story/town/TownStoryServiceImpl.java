package net.healingchurch.story.services.story.town;

import net.healingchurch.story.domain.TownStory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TownStoryServiceImpl implements TownStoryService {
    @Autowired
    private TownStoryMapper townStoryMapper;

    @Override
    public List<TownStory> findStoryList(int groupId, String fromDate, String toDate, int page, int limit) {
        TownStory townStory = new TownStory();
        townStory.setFromDate(fromDate);
        townStory.setToDate(toDate);
        townStory.setGroupId(groupId);
        townStory.setPage(page);
        townStory.setOffset((page-1)*limit);
        townStory.setLimit(limit);
        return townStoryMapper.findStoryList(townStory);
    }

    @Override
    //@PreAuthorize("hasAnyRole('ROLE_TOWN_MANAGER')")
    public int createStory(boolean worshipYn, boolean pastureMeetYn, int bibleCount, int qtCount, boolean fridayWorshipYn, int dawnPrayCount, String etc, String userId, int groupId, String leaderCareStory, String townCareStroy) {
        TownStory townStory = new TownStory();
        townStory.setWorshipYn(worshipYn);
        townStory.setPastureMeetYn(pastureMeetYn);
        townStory.setBibleCount(bibleCount);
        townStory.setQtCount(qtCount);
        townStory.setFridayWorshipYn(fridayWorshipYn);
        townStory.setDawnPrayCount(dawnPrayCount);
        townStory.setEtc(etc);
        townStory.setUserId(userId);
        townStory.setGroupId(groupId);
        townStory.setLeaderCareStory(leaderCareStory);
        townStory.setTownCareStroy(townCareStroy);
        return townStoryMapper.createStory(townStory);
    }

    @Override
    public TownStory getStory(int storyId) {
        return townStoryMapper.getStory(storyId);
    }

    @Override
    //@PreAuthorize("hasAnyRole('ROLE_TOWN_MANAGER')")
    public int updateStory(int storyId, boolean worshipYn, boolean pastureMeetYn, int bibleCount, int qtCount, boolean fridayWorshipYn, int dawnPrayCount, String etc, String userId, int groupId, String leaderCareStory, String townCareStroy) {
        TownStory townStory = new TownStory();
        townStory.setStoryId(storyId);
        townStory.setWorshipYn(worshipYn);
        townStory.setPastureMeetYn(pastureMeetYn);
        townStory.setBibleCount(bibleCount);
        townStory.setQtCount(qtCount);
        townStory.setFridayWorshipYn(fridayWorshipYn);
        townStory.setDawnPrayCount(dawnPrayCount);
        townStory.setEtc(etc);
        townStory.setUserId(userId);
        townStory.setGroupId(groupId);
        townStory.setLeaderCareStory(leaderCareStory);
        townStory.setTownCareStroy(townCareStroy);
        return townStoryMapper.updateStory(townStory);
    }

    @Override
    public void removeStory(int storyId) {
        townStoryMapper.removeStory(storyId);
    }

}
