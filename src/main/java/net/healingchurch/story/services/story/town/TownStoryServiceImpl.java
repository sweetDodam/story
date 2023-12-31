package net.healingchurch.story.services.story.town;

import net.healingchurch.story.domain.Event;
import net.healingchurch.story.domain.PastureStory;
import net.healingchurch.story.domain.TownStory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("townStoryService")
public class TownStoryServiceImpl implements TownStoryService {
    @Autowired
    private TownStoryMapper townStoryMapper;

    @Override
    public Map<Object, Object> findStoryList(int groupId, String inputDate) {
        TownStory townStory = new TownStory();

        townStory.setGroupId(groupId);
        townStory.setInputDate(inputDate);

        Map<Object, Object> resutMap = new HashMap<>();

        //데이터
        resutMap.put("rows", townStoryMapper.findStoryList(townStory));

        return resutMap;
    }

    @Override
    public Map<Object, Object> findStorySumList(int groupId, String fromDate, String toDate) {
        TownStory townStory = new TownStory();

        townStory.setGroupId(groupId);
        townStory.setFromDate(fromDate);
        townStory.setToDate(toDate);

        Map<Object, Object> resutMap = new HashMap<>();

        //데이터
        resutMap.put("rows", townStoryMapper.findStorySumList(townStory));

        return resutMap;
    }


    @Override
    public Map<Object, Object> findStoryCareList(int groupId, String userId, String fromDate, String toDate) {
        TownStory townStory = new TownStory();

        townStory.setGroupId(groupId);
        townStory.setUserId(userId);
        townStory.setFromDate(fromDate);
        townStory.setToDate(toDate);

        Map<Object, Object> resutMap = new HashMap<>();

        //데이터
        resutMap.put("rows", townStoryMapper.findStoryCareList(townStory));

        return resutMap;
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
            townStoryMapper.createStorySum(townStory);
        }else{
            //마을 스토리 합계 수정
            townStoryMapper.updateStorySum(townStory);
        }
    }

    @Override
    //@PreAuthorize("hasAnyRole('ROLE_TOWN_MANAGER')")
    public int createStory(int storyId, String leaderCareStory, String pastureCareStory) {
        TownStory townStory = new TownStory();

        townStory.setStoryId(storyId);
        townStory.setLeaderCareStory(leaderCareStory);
        townStory.setPastureCareStory(pastureCareStory);

        //로그인한 유저 아이디
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        townStory.setLoginUserId(principal.getUsername());

        townStoryMapper.createStory(townStory);

        return townStory.getTownStoryId();
    }

    @Override
    //@PreAuthorize("hasAnyRole('ROLE_TOWN_MANAGER')")
    public int updateStory(int storyId, String leaderCareStory, String pastureCareStory) {
        TownStory townStory = new TownStory();

        townStory.setStoryId(storyId);
        townStory.setLeaderCareStory(leaderCareStory);
        townStory.setPastureCareStory(pastureCareStory);

        //로그인한 유저 아이디
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        townStory.setLoginUserId(principal.getUsername());

        return townStoryMapper.updateStory(townStory);
    }

    @Override
    public void removeStory(int storyId) {
        townStoryMapper.removeStory(storyId);
    }

    @Override
    public Map<Object, Object> findUserStoryList(String userId, int groupId, int roleId, String userName, String inputDate, int page, int limit) {
        TownStory townStory = new TownStory();

        townStory.setPage(page);
        townStory.setOffset((page-1)*limit);
        townStory.setLimit(limit);

        townStory.setUserId(userId);
        townStory.setGroupId(groupId);
        townStory.setRoleId(roleId);
        townStory.setUserName(userName);
        townStory.setInputDate(inputDate);

        Map<Object, Object> resutMap = new HashMap<>();

        //데이터
        resutMap.put("rows", townStoryMapper.findUserStoryList(townStory));

        //전체 갯수
        int records = townStoryMapper.findUserStoryListCnt(townStory);
        resutMap.put("records", records);

        //페이지 수
        resutMap.put("total", (int)Math.ceil((double)records/(double)limit));

        return resutMap;
    }
}
