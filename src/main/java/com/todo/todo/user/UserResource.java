package com.todo.todo.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserResource {

    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userDaoService.findAll();
    }

    @GetMapping("/users/{uid}")
    public EntityModel<User> retrieveUser(@PathVariable int uid){
        User user =  userDaoService.findOne(uid);
        if(user == null){
            throw new UserNotFoundException("uid - " + uid);
        }
        EntityModel<User> resource = EntityModel.of(user);
        WebMvcLinkBuilder linkTo =
                linkTo(methodOn(this.getClass()).retrieveAllUsers());

        resource.add(linkTo.withRel("all-users"));
        return resource;
    }

    //HATEOS - Hyper media as the engine of application state
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
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
