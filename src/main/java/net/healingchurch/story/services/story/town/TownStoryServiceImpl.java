package net.healingchurch.story.services.story.town;

import net.healingchurch.story.domain.TownStory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("townStoryService")
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
    public TownStory getStory(int storyId) {
        return townStoryMapper.getStory(storyId);
    }

    @Override
    public TownStory getStoryByGroup(String groupId, int parentGroupId, String inputDate) {
        TownStory townStory = new TownStory();

        townStory.setUserId(groupId);
        townStory.setGroupId(parentGroupId);
        townStory.setInputDate(inputDate);

        return townStoryMapper.getStoryByGroup(townStory);
    }

    @Override
    public void townStorySum(String groupId, int parentGroupId, String inputDate){
        TownStory townStory = getStoryByGroup(groupId, parentGroupId, inputDate);

        //등록된 마을 스토리가 없을 경우
        if(townStory == null){
            townStory = new TownStory();

            townStory.setUserId(groupId);
            townStory.setGroupId(parentGroupId);
            townStory.setInputDate(inputDate);

            //마을 스토리 합계 입력
            townStoryMapper.createStory(townStory);
        }else{
            //마을 스토리 합계 수정
            townStoryMapper.updateStorySum(townStory);
        }
    }

    @Override
    //@PreAuthorize("hasAnyRole('ROLE_TOWN_MANAGER')")
    public int updateStory(int storyId, String userId, int groupId, String leaderCareStory, String townCareStroy) {
        TownStory townStory = new TownStory();

        townStory.setStoryId(storyId);
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
