package net.healingchurch.story.services.story.pasture;

import net.healingchurch.story.domain.PastureStory;
import net.healingchurch.story.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PastureStoryServiceImpl implements PastureStoryService {
    @Autowired
    private PastureStoryMapper pastureStoryMapper;

    @Override
    public List<PastureStory> findStoryList(int groupId, String fromDate, String toDate, int page, int limit) {
        PastureStory pastureStory = new PastureStory();
        pastureStory.setFromDate(fromDate);
        pastureStory.setToDate(toDate);
        pastureStory.setGroupId(groupId);
        pastureStory.setPage(page);
        pastureStory.setOffset((page-1)*limit);
        pastureStory.setLimit(limit);
        return pastureStoryMapper.findStoryList(pastureStory);
    }

    @Override
    //@PreAuthorize("hasAnyRole('ROLE_PASTURE_MANAGER')")
    public int createStory(boolean worshipYn, boolean pastureMeetYn, int bibleCount, int qtCount, boolean fridayWorshipYn, int dawnPrayCount, String etc, String userId, int groupId, String prayers, boolean leaderYn, String inputDate, String worshipReason, String leaderReason) {
        PastureStory pastorStory = new PastureStory();
        pastorStory.setWorshipYn(worshipYn);
        pastorStory.setPastureMeetYn(pastureMeetYn);
        pastorStory.setBibleCount(bibleCount);
        pastorStory.setQtCount(qtCount);
        pastorStory.setFridayWorshipYn(fridayWorshipYn);
        pastorStory.setDawnPrayCount(dawnPrayCount);
        pastorStory.setEtc(etc);
        pastorStory.setUserId(userId);
        pastorStory.setGroupId(groupId);
        pastorStory.setInputDate(inputDate);
        pastorStory.setPrayers(prayers);
        pastorStory.setLeaderYn(leaderYn);
        pastorStory.setWorshipReason(worshipReason);
        pastorStory.setLeaderReason(leaderReason);

        return pastureStoryMapper.createStory(pastorStory);
    }

    @Override
    public PastureStory getStory(int storyId) {
        return pastureStoryMapper.getStory(storyId);
    }

    @Override
    //@PreAuthorize("hasAnyRole('ROLE_PASTURE_MANAGER')")
    public int updateStory(int storyId, boolean worshipYn, boolean pastureMeetYn, int bibleCount, int qtCount, boolean fridayWorshipYn, int dawnPrayCount, String etc, String userId, int groupId, String prayers, boolean leaderYn, String worshipReason, String leaderReason) {
        PastureStory pastorStory = new PastureStory();
        pastorStory.setStoryId(storyId);
        pastorStory.setWorshipYn(worshipYn);
        pastorStory.setPastureMeetYn(pastureMeetYn);
        pastorStory.setBibleCount(bibleCount);
        pastorStory.setQtCount(qtCount);
        pastorStory.setFridayWorshipYn(fridayWorshipYn);
        pastorStory.setDawnPrayCount(dawnPrayCount);
        pastorStory.setEtc(etc);
        pastorStory.setUserId(userId);
        pastorStory.setGroupId(groupId);
        pastorStory.setPrayers(prayers);
        pastorStory.setLeaderYn(leaderYn);
        pastorStory.setWorshipReason(worshipReason);
        pastorStory.setLeaderReason(leaderReason);

        return pastureStoryMapper.updateStory(pastorStory);
    }

    @Override
    public void removeStory(int storyId) {
        pastureStoryMapper.removeStory(storyId);
    }

    @Override
    public List<PastureStory> findUserStoryList(String userId, int groupId, String fromDate, String toDate, String inputDate, int page, int limit) {
        PastureStory story = new PastureStory();
        story.setPage(page);
        story.setOffset((page-1)*limit);
        story.setLimit(limit);

        story.setUserId(userId);
        story.setGroupId(groupId);
        story.setFromDate(fromDate);
        story.setToDate(toDate);
        story.setInputDate(inputDate);

        return pastureStoryMapper.findUserStoryList(story);
    }

    @Override
    public int findUserStoryListCnt(String userId, int groupId, String fromDate, String toDate, String inputDate) {
        PastureStory story = new PastureStory();

        story.setUserId(userId);
        story.setGroupId(groupId);
        story.setFromDate(fromDate);
        story.setToDate(toDate);
        story.setInputDate(inputDate);

        return pastureStoryMapper.findUserStoryListCnt(story);
    }
}
