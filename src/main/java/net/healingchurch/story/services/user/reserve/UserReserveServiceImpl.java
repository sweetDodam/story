package net.healingchurch.story.services.user.reserve;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.healingchurch.story.domain.UserReserve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Service
public class UserReserveServiceImpl implements UserReserveService {
    @Autowired
    private UserReserveMapper userReserveMapper;

    @Override
    public List<UserReserve> findUserReserveList(int page, int limit) {
        UserReserve userReserve = new UserReserve();
        userReserve.setPage(page);
        userReserve.setOffset((page-1)*limit);
        userReserve.setLimit(limit);
        return userReserveMapper.findUserReserveList(userReserve);
    }

    @Override
    public UserReserve getUserReserve(int reserveId) {
        UserReserve userReserve = new UserReserve();

        userReserve.setReserveId(reserveId);

        return userReserveMapper.getUserReserve(userReserve);
    }

    public UserReserve getUserReserve(String userId, String inputDate) {
        UserReserve userReserve = new UserReserve();

        userReserve.setUserId(userId);
        userReserve.setInputDate(inputDate);

        return userReserveMapper.getUserReserve(userReserve);
    }

    @Override
    public int createUserReserve(String userId, String inputDate) {
        UserReserve userReserve = new UserReserve();

        userReserve.setUserId(userId);
        userReserve.setInputDate(inputDate);

        //로그인한 유저 아이디
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userReserve.setLoginUserId(principal.getUsername());

        return userReserveMapper.createUserReserve(userReserve);
    }

    @Override
    public int updateUserReserve(int reserveId, String userId, String inputDate) {
        UserReserve userReserve = new UserReserve();

        userReserve.setReserveId(reserveId);
        userReserve.setUserId(userId);
        userReserve.setInputDate(inputDate);

        //로그인한 유저 아이디
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userReserve.setLoginUserId(principal.getUsername());

        return userReserveMapper.updateUserReserve(userReserve);
    }

    @Override
    public int createUpdateUserReserve(String reserveList, String inputDate) {
        Gson gson = new Gson();
        Type resultType = new TypeToken<List<Map<String, Object>>>(){}.getType();
        List<Map<String, Object>> result = gson.fromJson(reserveList, resultType);

        for(int i = 0;i < result.size();i++){
            Map<String, Object> data = result.get(i);

            UserReserve userReserve = new UserReserve();

            userReserve.setUserId(data.get("userId") != null ? data.get("userId").toString() : "");

            UserReserve userReserve2 = userReserveMapper.getUserReserve(userReserve);

            userReserve.setInputDate(inputDate);

            //로그인한 유저 아이디
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userReserve.setLoginUserId(principal.getUsername());

            if(userReserve2 == null){
                userReserveMapper.createUserReserve(userReserve);
            }else{
                userReserve.setReserveId(userReserve2.getReserveId());
                userReserveMapper.updateUserReserve(userReserve);
            }
        }

        return 1;
    }

    @Override
    public void removeUserReserve(String reserveList) {
        Gson gson = new Gson();
        Type resultType = new TypeToken<List<Map<String, Object>>>(){}.getType();
        List<Map<String, Object>> result = gson.fromJson(reserveList, resultType);

        for(int i = 0;i < result.size();i++) {
            Map<String, Object> data = result.get(i);

            userReserveMapper.removeUserReserve(data.get("userId") != null ? data.get("userId").toString() : "");
        }
    }
}
