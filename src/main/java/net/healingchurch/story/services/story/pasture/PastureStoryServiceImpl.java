package net.healingchurch.story.services.story.pasture;

import net.healingchurch.story.domain.PastureStory;
import net.healingchurch.story.domain.TownStory;
import net.healingchurch.story.services.story.town.TownStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PastureStoryServiceImpl implements PastureStoryService {
    @Autowired
    private PastureStoryMapper pastureStoryMapper;

    @Resource(name = "townStoryService")
    private TownStoryService townStoryService;

    @Override
    public Map<Object, Object> findStoryList(String userId, String fromDate, String toDate, int page, int limit) {
        PastureStory pastureStory = new PastureStory();
        pastureStory.setFromDate(fromDate);
        pastureStory.setToDate(toDate);
        pastureStory.setUserId(userId);
        pastureStory.setPage(page);
        pastureStory.setOffset((page-1)*limit);
        pastureStory.setLimit(limit);

        Map<Object, Object> resutMap = new HashMap<>();

        //데이터
        resutMap.put("rows", pastureStoryMapper.findStoryList(pastureStory));

        //전체 갯수
        int records = pastureStoryMapper.findStoryListCnt(pastureStory);
        resutMap.put("records", records);

        //페이지 수
        resutMap.put("total", (int)Math.ceil((double)records/(double)limit));

        return resutMap;
    }

    @Override
    //@PreAuthorize("hasAnyRole('ROLE_PASTURE_MANAGER')")
    public int createStory(int worshipYn, int pastureMeetYn, int bibleCount, int qtCount, int fridayWorshipYn, int dawnPrayCount, String etc, String userId, int groupId, int parentGroupId, String prayers, int leaderYn, String inputDate, String worshipReason, String leaderReason) {
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

        //목장 스토리 입력
        int rs = pastureStoryMapper.createStory(pastorStory);

        //마을 스토리 합계 등록/수정
        String groupIdStr = String.valueOf(groupId);
        townStoryService.townStorySum(groupIdStr, parentGroupId, inputDate);

        return rs;
    }

    @Override
    public PastureStory getStory(int storyId) {
        return pastureStoryMapper.getStory(storyId);
    }

    @Override
    //@PreAuthorize("hasAnyRole('ROLE_PASTURE_MANAGER')")
    public int updateStory(int storyId, int worshipYn, int pastureMeetYn, int bibleCount, int qtCount, int fridayWorshipYn, int dawnPrayCount, String etc, String userId, int groupId, int parentGroupId, String prayers, int leaderYn, String inputDate, String worshipReason, String leaderReason) {
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

        //목장 스토리 수정
        int rs = pastureStoryMapper.updateStory(pastorStory);

        //마을 스토리 합계 등록/수정
        String groupIdStr = String.valueOf(groupId);
        townStoryService.townStorySum(groupIdStr, parentGroupId, inputDate);

        return rs;
    }

    @Override
    public void removeStory(int storyId, int groupId, int parentGroupId, String inputDate) {
        //목장 스토리 삭제
        pastureStoryMapper.removeStory(storyId);

        //마을 스토리 합계 등록/수정
        String groupIdStr = String.valueOf(groupId);
        townStoryService.townStorySum(groupIdStr, parentGroupId, inputDate);
    }

    @Override
    public Map<Object, Object> findUserStoryList(String userId, int groupId, int roleId, String userName, String inputDate, int page, int limit) {
        PastureStory story = new PastureStory();

        story.setPage(page);
        story.setOffset((page-1)*limit);
        story.setLimit(limit);

        story.setUserId(userId);
        story.setGroupId(groupId);
        story.setRoleId(roleId);
        story.setUserName(userName);
        story.setInputDate(inputDate);

        Map<Object, Object> resutMap = new HashMap<>();

        //데이터
        resutMap.put("rows", pastureStoryMapper.findUserStoryList(story));

        //전체 갯수
        int records = pastureStoryMapper.findUserStoryListCnt(story);
        resutMap.put("records", records);

        //페이지 수
        resutMap.put("total", (int)Math.ceil((double)records/(double)limit));

        return resutMap;
    }
}
