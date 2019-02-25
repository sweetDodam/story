package net.healingchurch.story.services.user.reserve;

import net.healingchurch.story.domain.UserReserve;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserReserveMapper {
    List<UserReserve> findUserReserveList(UserReserve userReserve);

    UserReserve getUserReserve(UserReserve userReserve);

    int createUserReserve(UserReserve userReserve);

    int updateUserReserve(UserReserve userReserve);

    void removeUserReserve(String userId);
}
