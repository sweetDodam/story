package net.healingchurch.story.services.story.pastor;

import net.healingchurch.story.domain.PastorStory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PastorStoryServiceImpl implements PastorStoryService {
    @Autowired
    private PastorStoryMapper pastorStoryMapper;

    @Override
    public List<PastorStory> findStoryList(String pastorId, String fromDate, String toDate, int page, int limit) {
        PastorStory pastorStory = new PastorStory();
        pastorStory.setFromDate(fromDate);
        pastorStory.setToDate(toDate);
        pastorStory.setPastorId(pastorId);
        pastorStory.setPage(page);
        pastorStory.setOffset((page-1)*limit);
        pastorStory.setLimit(limit);
        return pastorStoryMapper.findStoryList(pastorStory);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_PASTER')")
    public int createStory(String pastorId, String visitUserId, String visitDate, String summary, String prayers, String etc) {
        PastorStory pastorStory = new PastorStory();
        pastorStory.setPastorId(pastorId);
        pastorStory.setVisitUserId(visitUserId);
        pastorStory.setVisitDate(visitDate);
        pastorStory.setSummary(summary);
        pastorStory.setPrayers(prayers);
        pastorStory.setEtc(etc);
        return pastorStoryMapper.createStory(pastorStory);
    }

    @Override
    public PastorStory getStory(int storyId) {
        return pastorStoryMapper.getStory(storyId);
    }

    @Override
    public int updateStory(int storyId, String pastorId, String visitUserId, String visitDate, String summary, String prayers, String etc) {
        PastorStory pastorStory = new PastorStory();
        pastorStory.setStoryId(storyId);
        pastorStory.setPastorId(pastorId);
        pastorStory.setVisitUserId(visitUserId);
        pastorStory.setVisitDate(visitDate);
        pastorStory.setSummary(summary);
        pastorStory.setPrayers(prayers);
        pastorStory.setEtc(etc);
        return pastorStoryMapper.updateStory(pastorStory);
    }

    @Override
    public void removeStory(int storyId) {
        pastorStoryMapper.removeStory(storyId);
    }

}
