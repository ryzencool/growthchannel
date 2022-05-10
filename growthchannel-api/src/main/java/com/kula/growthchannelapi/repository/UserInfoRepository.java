package com.kula.growthchannelapi.repository;

import com.kula.growthchannelapi.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByEmailAndIsDel(String email, Boolean isDel);

}
