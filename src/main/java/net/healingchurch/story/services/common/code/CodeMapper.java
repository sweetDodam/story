package net.healingchurch.story.services.common.code;

import net.healingchurch.story.domain.Code;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodeMapper {
    List<Code> findCodeList(Code code);

    List<Code> findCodeChildList(String parentCodeId);

    Code getCode(String codeId);

    int createCode(Code code);

    int updateCode(Code code);

    void removeCode(String codeId);
}
