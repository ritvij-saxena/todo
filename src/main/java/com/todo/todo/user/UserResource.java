package com.todo.todo.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userDaoService.findAll();
    }

    @GetMapping("/users/{uid}")
    public User retrieveUser(@PathVariable int uid){
        User user =  userDaoService.findOne(uid);
        if(user == null){
            throw new UserNotFoundException("uid - " + uid);
        }
        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@RequestBody User user){
        User savedUser = userDaoService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
       return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{uid}")
    public void deleteUser(@PathVariable int uid){
        User user = userDaoService.deleteUserById(uid);
        if(user == null){
            throw new UserNotFoundException("id - "+uid);
        }
    }
}
