package net.healingchurch.story.services.user.reserve;

import net.healingchurch.story.domain.UserReserve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/services/user/reserve")
public class UserReserveController {

    private static final Logger logger = LoggerFactory.getLogger(UserReserveController.class);

    @Autowired
    private UserReserveService userReserveService;

    @GetMapping("list")
    public List<UserReserve> findUserReserveList(@RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "limit", defaultValue = "20") int limit) {
        return userReserveService.findUserReserveList(page, limit);
    }

    @GetMapping("get")
    public UserReserve getUserReserve(@RequestParam(value = "reserveId", defaultValue = "") int reserveId) {
        return userReserveService.getUserReserve(reserveId);
    }

    @PostMapping("create")
    public int createUserReserve(@RequestParam(value = "userId", defaultValue = "") String userId,
                                  @RequestParam(value = "inputDate", defaultValue = "") String inputDate){
        return userReserveService.createUserReserve(userId, inputDate);
    }

    @PostMapping("update")
    public int updateUserReserve(@RequestParam(value = "reserveId", defaultValue = "0") int reserveId,
                                  @RequestParam(value = "userId", defaultValue = "") String userId,
                                  @RequestParam(value = "inputDate", defaultValue = "") String inputDate){
        return userReserveService.updateUserReserve(reserveId, userId, inputDate);
    }

    @PostMapping("createUpdate")
    public int updateStory(@RequestParam(value = "reserveList", defaultValue = "") String reserveList,
                            @RequestParam(value = "inputDate", defaultValue = "") String inputDate) throws Exception {
        return userReserveService.createUpdateUserReserve(reserveList, inputDate);
    }

    @PostMapping("remove")
    public int removeUserReserve(
            @RequestParam(value = "reserveList", defaultValue = "") String reserveList) {
        userReserveService.removeUserReserve(reserveList);
        return 1;
    }
}
