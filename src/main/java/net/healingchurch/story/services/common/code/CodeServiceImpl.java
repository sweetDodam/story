package net.healingchurch.story.services.common.code;

import net.healingchurch.story.domain.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeServiceImpl implements CodeService {
    @Autowired
    private CodeMapper codeMapper;

    @Override
    public List<Code> findCodeList(int page, int limit) {
        Code code = new Code();
        code.setPage(page);
        code.setOffset((page-1)*limit);
        code.setLimit(limit);
        return codeMapper.findCodeList(code);
    }

    @Override
    public List<Code> findCodeChildList(String parentCodeId) {
        return codeMapper.findCodeChildList(parentCodeId);
    }

    @Override
    public Code getCode(String codeId) {
        return codeMapper.getCode(codeId);
    }

    @Override
    public int createCode(String codeId, String parentCodeId, String codeName, String description, int sortIdx) {
        Code code = new Code();
        code.setCodeId(codeId);
        code.setParentCodeId(parentCodeId);
        code.setCodeName(codeName);
        code.setDescription(description);
        code.setSortIdx(sortIdx);
        return codeMapper.createCode(code);
    }

    @Override
    public int updateCode(String codeId, String parentCodeId, String codeName, String description, int sortIdx) {
        Code code = new Code();
        code.setCodeId(codeId);
        code.setParentCodeId(parentCodeId);
        code.setCodeName(codeName);
        code.setDescription(description);
        code.setSortIdx(sortIdx);
        return codeMapper.updateCode(code);
    }

    @Override
    public void removeCode(String codeId) {
        codeMapper.removeCode(codeId);
    }
}
