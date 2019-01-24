package net.healingchurch.story.domain;

import java.util.Objects;

public class PastureStory extends Story {
    private int pastureStoryId;
    private int masterId;
    private String prayers;
    private String  worshipReason;
    private String leaderReason;
    private String  worshipDesc;
    private String leaderDesc;

    public PastureStory() {
    }

    public int getPastureStoryId() {
        return pastureStoryId;
    }

    public void setPastureStoryId(int pastureStoryId) {
        this.pastureStoryId = pastureStoryId;
    }

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public String getPrayers() {
        return prayers;
    }

    public void setPrayers(String prayers) {
        this.prayers = prayers;
    }

    public String getWorshipReason() {
        return worshipReason;
    }

    public void setWorshipReason(String worshipReason) {
        this.worshipReason = worshipReason;
    }

    public String getWorshipDesc() {
        return worshipDesc;
    }

    public void setWorshipDesc(String worshipDesc) {
        this.worshipDesc = worshipDesc;
    }

    public String getLeaderReason() {
        return leaderReason;
    }

    public void setLeaderReason(String leaderReason) {
        this.leaderReason = leaderReason;
    }

    public String getLeaderDesc() {
        return leaderDesc;
    }

    public void setLeaderDesc(String leaderDesc) {
        this.leaderDesc = leaderDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PastureStory that = (PastureStory) o;
        return pastureStoryId == that.pastureStoryId &&
                masterId == that.masterId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pastureStoryId, masterId);
    }

    @Override
    public String toString() {
        return "PastureStory{" +
                "pastureStoryId='" + pastureStoryId + '\'' +
                "masterId='" + masterId + '\'' +
                ", prayers='" + prayers + '\'' +
                ", worshipReason=" + worshipReason + '\'' +
                ", worshipDesc=" + worshipDesc + '\'' +
                ", leaderReason=" + leaderReason + '\'' +
                ", leaderDesc=" + leaderDesc + '\'' +
                '}';
    }
}
