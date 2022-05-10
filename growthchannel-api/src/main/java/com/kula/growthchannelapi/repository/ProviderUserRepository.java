package com.kula.growthchannelapi.repository;

import com.kula.growthchannelapi.domain.ProviderUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProviderUserRepository extends JpaRepository<ProviderUser, Long> {

    Optional<ProviderUser> findByEmailAndProviderTypeAndIsDel(String email, Integer providerType, Boolean isDel);

}
