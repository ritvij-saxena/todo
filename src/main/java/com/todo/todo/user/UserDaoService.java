package com.todo.todo.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDaoService {
    //@Repository works as well.

    private static List<User> users = new ArrayList<>();
    private static int userCount = 3;
    static {
        users.add(new User(1,"Adam",new Date()));
        users.add(new User(2,"Eve",new Date()));
        users.add(new User(3,"Jack",new Date()));
    }

    public List<User> findAll(){
        return users;
    }

    public User save(User user){
        if(user.getId() == null){
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }

    public User findOne(int uid){
        for(User user : users){
            if(user.getId()==uid){
                return user;
            }
        }
        return null;
    }

    public User deleteUserById (int uid){
//        users.removeIf(user -> user.getId() == uid);
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()){
            User user = iterator.next();
            if(user.getId() == uid){
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
