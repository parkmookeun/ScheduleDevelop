package me.parkmookeun.schedule_develop.repository;

import me.parkmookeun.schedule_develop.entity.User;
import me.parkmookeun.schedule_develop.exception.UserNotFoundException;
import me.parkmookeun.schedule_develop.exception.WrongInputException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    default User findByEmailAndPasswordOrElseThrow(String email, String passsword){
        return findByEmailAndPassword(email, passsword).orElseThrow(WrongInputException::new);
    }


    default User findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(UserNotFoundException::new);
    }
    Optional<User> findByEmailAndPassword(String email, String password);

}
