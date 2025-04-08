package com.aklbeti.account.repository;

import com.aklbeti.account.entity.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /*
    @Query("""
    SELECT DISTINCT acc FROM Account acc
    JOIN FETCH acc.profile prof
    JOIN FETCH prof.addresses addr
    JOIN FETCH addr.city c
    WHERE acc.emailAddress = :emailAddress
    """)
     */
    @EntityGraph(attributePaths = {"profile", "profile.address", "profile.address.city"})
    Optional<Account> findByEmailAddress(String emailAddress);

    @EntityGraph(attributePaths = {"profile", "profile.address", "profile.address.city"})
    Optional<Account> findById(long id);
}
