package net.healingchurch.story.services.story.pastor;

import net.healingchurch.story.domain.PastorStory;
import net.healingchurch.story.services.user.reserve.UserReserveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PastorStoryServiceImpl implements PastorStoryService {
    @Autowired
    private PastorStoryMapper pastorStoryMapper;

    @Autowired
    private UserReserveMapper userReserveMapper;

    @Override
    public Map<Object, Object> findStoryList(String visitUserId, String pastorId, String fromDate, String toDate, int page, int limit) {
        PastorStory pastorStory = new PastorStory();

        pastorStory.setVisitUserId(visitUserId);
        pastorStory.setPastorId(pastorId);
        pastorStory.setFromDate(fromDate);
        pastorStory.setToDate(toDate);
        pastorStory.setPage(page);
        pastorStory.setOffset((page-1)*limit);
        pastorStory.setLimit(limit);

        Map<Object, Object> resutMap = new HashMap<>();

        //데이터
        resutMap.put("rows", pastorStoryMapper.findStoryList(pastorStory));

        //전체 갯수
        int records = pastorStoryMapper.findStoryListCnt(pastorStory);
        resutMap.put("records", records);

        //페이지 수
        resutMap.put("total", (int)Math.ceil((double)records/(double)limit));

        return resutMap;
    }

    @Override
    //@PreAuthorize("hasRole('ROLE_PASTER')")
    public int createStory(String pastorId, String visitUserId, String visitDate, String visitPlace, String summary, String prayers, String etc) {
        PastorStory pastorStory = new PastorStory();

        pastorStory.setPastorId(pastorId);
        pastorStory.setVisitUserId(visitUserId);
        pastorStory.setVisitDate(visitDate);
        pastorStory.setVisitPlace(visitPlace);
        pastorStory.setSummary(summary);
        pastorStory.setPrayers(prayers);
        pastorStory.setEtc(etc);

        //로그인한 유저 아이디
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        pastorStory.setLoginUserId(principal.getUsername());

        //사역자 스토리 등록
        int result = pastorStoryMapper.createStory(pastorStory);

        //심방 예정자에서 삭제
        userReserveMapper.removeUserReserve(visitUserId);

        return result;
    }

    @Override
    public PastorStory getStory(int storyId) {
        return pastorStoryMapper.getStory(storyId);
    }

    @Override
    public int updateStory(int storyId, String pastorId, String visitUserId, String visitDate, String visitPlace, String summary, String prayers, String etc) {
        PastorStory pastorStory = new PastorStory();

        pastorStory.setStoryId(storyId);
        pastorStory.setPastorId(pastorId);
        pastorStory.setVisitUserId(visitUserId);
        pastorStory.setVisitDate(visitDate);
        pastorStory.setVisitPlace(visitPlace);
        pastorStory.setSummary(summary);
        pastorStory.setPrayers(prayers);
        pastorStory.setEtc(etc);

        //로그인한 유저 아이디
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        pastorStory.setLoginUserId(principal.getUsername());

        return pastorStoryMapper.updateStory(pastorStory);
    }

    @Override
    public void removeStory(int storyId) {
        pastorStoryMapper.removeStory(storyId);
    }

    @Override
    public Map<Object, Object> findUserStoryList(String userId, int groupId, int roleId, String userName, String visitDate, String pastorId, String isReserve, String isConfirm, int page, int limit) {
        PastorStory story = new PastorStory();

        story.setPage(page);
        story.setOffset((page-1)*limit);
        story.setLimit(limit);

        story.setUserId(userId);
        story.setGroupId(groupId);
        story.setRoleId(roleId);
        story.setUserName(userName);
        story.setVisitDate(visitDate);
        story.setPastorId(pastorId);
        story.setIsReserve(isReserve);
        story.setIsConfirm(isConfirm);

        Map<Object, Object> resutMap = new HashMap<>();

        //데이터
        resutMap.put("rows", pastorStoryMapper.findUserStoryList(story));

        //전체 갯수
        int records = pastorStoryMapper.findUserStoryListCnt(story);
        resutMap.put("records", records);

        //페이지 수
        resutMap.put("total", (int)Math.ceil((double)records/(double)limit));

        return resutMap;
    }
}
