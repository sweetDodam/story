package net.healingchurch.story.services.user.reserve;

import net.healingchurch.story.domain.UserReserve;

import java.util.List;

public interface UserReserveService {
    List<UserReserve> findUserReserveList(int page, int limit);

    UserReserve getUserReserve(int reserveId);

    UserReserve getUserReserve(String userId, String inputDate);

    int createUserReserve(String userId, String inputDate);

    int updateUserReserve(int reserveId, String userId, String inputDate);

    int createUpdateUserReserve(String reserveList, String inputDate);

    void removeUserReserve(String reserveList);
}
