package com.basic.myspringboot.controller;

import com.basic.myspringboot.entity.User;
import com.basic.myspringboot.exception.BusinessException;
import com.basic.myspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
public class UserRestController {

    private final UserRepository userRepository;

    //Constructor Injection
//    public UserRestController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//        log.info("UserRepository {} ",userRepository.getClass().getName());
//    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
   public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));

    }

    @PatchMapping("/{email}/")
    public User updateUser(@PathVariable String email, @RequestBody User userDetail) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
        // setter 호출 -> name 수정
        user.setName(userDetail.getName());
        //@Transactional 사용하지 않으므로 반드시 save() 호출하여 DB에 반영됨
        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
        userRepository.delete(user);
        //return ResponseEntity.ok(user);
        String msg = String.format("Id = %d User가 삭제처리 되었습니다", id);
        return ResponseEntity.ok(msg);

        //결과값 : Id = 1 User가 삭제처리 되었습니다
    }
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

}