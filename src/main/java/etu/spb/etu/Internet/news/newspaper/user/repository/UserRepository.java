package etu.spb.etu.Internet.news.newspaper.user.repository;

import etu.spb.etu.Internet.news.newspaper.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

}
