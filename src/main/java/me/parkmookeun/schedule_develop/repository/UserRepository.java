package me.parkmookeun.schedule_develop.repository;

import me.parkmookeun.schedule_develop.entity.User;
import me.parkmookeun.schedule_develop.exception.UserNotFoundException;
import me.parkmookeun.schedule_develop.exception.WrongInputException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    default User findByEmailAndPasswordOrElseThrow(String email, String passsword){
        return findByEmailAndPassword(email, passsword).orElseThrow(() -> new WrongInputException("이메일과 패스워드가 일치하지 않습니다!"));
    }


    default User findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(() -> new UserNotFoundException("해당하는 유저가 없습니다!"));
    }
    Optional<User> findByEmailAndPassword(String email, String password);
}
