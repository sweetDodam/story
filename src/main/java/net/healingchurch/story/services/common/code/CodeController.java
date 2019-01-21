package net.healingchurch.story.services.common.code;

import net.healingchurch.story.domain.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/services/common/code")
public class CodeController {

    @Autowired
    private CodeService codeService;

    @GetMapping("list")
    public List<Code> findCodeList(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "20") int limit) {
        return codeService.findCodeList(page, limit);
    }

    @GetMapping("childList")
    public List<Code> findCodeChildList(@RequestParam(value = "parentCodeId", required = true, defaultValue = "") String parentCodeId) {
        return codeService.findCodeChildList(parentCodeId);
    }

    @GetMapping("get")
    public Code getCode(@RequestParam(value = "storyId", required = true, defaultValue = "0") String codeId) {
        return codeService.getCode(codeId);
    }

    @PostMapping("create")
    public int createCode(
            @RequestParam(value = "codeId", defaultValue = "") String codeId,
            @RequestParam(value = "parentCodeId", defaultValue = "") String parentCodeId,
            @RequestParam(value = "codeName", defaultValue = "") String codeName,
            @RequestParam(value = "description", defaultValue = "") String description
    ) throws Exception {
        return codeService.createCode(codeId, parentCodeId, codeName, description);
    }

    @PostMapping("update")
    public int updateCode(
            @RequestParam(value = "codeId", defaultValue = "") String codeId,
            @RequestParam(value = "parentCodeId", defaultValue = "") String parentCodeId,
            @RequestParam(value = "codeName", defaultValue = "") String codeName,
            @RequestParam(value = "description", defaultValue = "") String description
    ) throws Exception {
        return codeService.updateCode(codeId, parentCodeId, codeName, description);
    }

    @PostMapping("remove")
    public int removeCode(
            @RequestParam(value = "codeId", required = true, defaultValue = "") String codeId) {
        codeService.removeCode(codeId);
        return 1;
    }
}
