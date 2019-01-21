package net.healingchurch.story.domain;

import java.util.Objects;

public class Code {
    private String codeId;
    private String parentCodeId;
    private String codeName;
    private String description;

    private int page;
    private int offset;
    private int limit;

    public Code() {
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getParentCodeId() {
        return parentCodeId;
    }

    public void setParentCodeId(String parentCodeId) {
        this.parentCodeId = parentCodeId;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Code code = (Code) o;
        return Objects.equals(codeId, code.codeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeId);
    }

    @Override
    public String toString() {
        return "Code{" +
                "codeId='" + codeId + '\'' +
                ", parentCodeId='" + parentCodeId + '\'' +
                ", codeName='" + codeName + '\'' +
                ", description='" + description + '\'' +
                ", page=" + page +
                ", offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}
