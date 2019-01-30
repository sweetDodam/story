package net.healingchurch.story.domain;

import java.util.Objects;

public class TownStory extends Story {
    private int townStroyId;
    private int masterId;
    private String leaderCareStory;
    private String pastureCareStory;
    private String eventId;
    private int userCnt;

    public TownStory() {
    }

    public int getTownStroyId() {
        return townStroyId;
    }

    public void setTownStroyId(int townStroyId) {
        this.townStroyId = townStroyId;
    }

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public String getLeaderCareStory() {
        return leaderCareStory;
    }

    public void setLeaderCareStory(String leaderCareStory) {
        this.leaderCareStory = leaderCareStory;
    }

    public String getPastureCareStory() {
        return pastureCareStory;
    }

    public void setPastureCareStory(String pastureCareStory) {
        this.pastureCareStory = pastureCareStory;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getUserCnt() {
        return userCnt;
    }

    public void setUserCnt(int userCnt) {
        this.userCnt = userCnt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TownStory townStory = (TownStory) o;
        return townStroyId == townStory.townStroyId &&
                masterId == townStory.masterId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), townStroyId, masterId);
    }

    @Override
    public String toString() {
        return "TownStory{" +
                "townStroyId=" + townStroyId +
                "masterId=" + masterId +
                ", leaderCareStory='" + leaderCareStory + '\'' +
                ", pastureCareStory='" + pastureCareStory + '\'' +
                ", eventId='" + eventId + '\'' +
                ", userCnt='" + userCnt + '\'' +
                '}';
    }
}
