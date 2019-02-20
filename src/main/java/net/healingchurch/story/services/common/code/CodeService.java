package net.healingchurch.story.services.common.code;

import net.healingchurch.story.domain.Code;

import java.util.List;

public interface CodeService {
    List<Code> findCodeList(int page, int limit);

    List<Code> findCodeChildList(String parentCodeId);

    Code getCode(String codeId);

    int createCode(String codeId, String parentCodeId, String codeName, String description, int sortIdx);

    int updateCode(String codeId, String parentCodeId, String codeName, String description, int sortIdx);

    void removeCode(String codeId);
}
