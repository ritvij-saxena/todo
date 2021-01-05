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
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJPAResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/jpa/users/{uid}")
    public EntityModel<Optional<User>> retrieveUser(@PathVariable int uid){
        Optional<User> user =  userRepository.findById(uid);
        if(!user.isPresent()){
            throw new UserNotFoundException("uid - " + uid);
        }
        EntityModel<Optional<User>> resource = EntityModel.of(user);
        WebMvcLinkBuilder linkTo =
                linkTo(methodOn(this.getClass()).retrieveAllUsers());

        resource.add(linkTo.withRel("all-users"));
        return resource;
    }

    //HATEOS - Hyper media as the engine of application state
    @PostMapping("/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{uid}")
    public void deleteUser(@PathVariable int uid){
        userRepository.deleteById(uid);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrieveAllUsersPosts(@PathVariable int id){
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()){
            throw new UserNotFoundException("id - " + id);
        }
        return userOptional.get().getPosts();
    }

    @PostMapping("/jpa/users/{id}/post")
    public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post ){
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()){
            throw new UserNotFoundException("id - " + id);
        }

        User user = userOptional.get();
        post.setUser(user);
        postRepository.save(post);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
