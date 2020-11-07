package ru.liga.java.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.java.socialnetwork.domains.Friendship;
import ru.liga.java.socialnetwork.domains.User;
import ru.liga.java.socialnetwork.repositories.FriendshipRepository;
import ru.liga.java.socialnetwork.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FriendshipService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    public boolean addFriend(Integer userId, Integer friendId){
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<User> optionalFriend = userRepository.findById(friendId);
        if(optionalUser.isPresent() & optionalFriend.isPresent()){
            User owner = optionalUser.get();
            User friend = optionalFriend.get();
            Friendship friendshipUF = new Friendship(owner, friend);
            Friendship friendshipFU = new Friendship(friend, owner);
            friendshipRepository.save(friendshipUF);
            friendshipRepository.save(friendshipFU);
            return true;
        }
        return false;
    }

    public boolean deleteFriend(Integer userId, Integer friendId){
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<User> optionalFriend = userRepository.findById(friendId);
        if(optionalUser.isPresent() & optionalFriend.isPresent()) {
            User owner = optionalUser.get();
            User friend = optionalFriend.get();
            friendshipRepository.deleteByOwnerAndFriend(owner, friend);
            friendshipRepository.deleteByOwnerAndFriend(friend, owner);
            return true;
        }
        return false;
    }

    public List<User> getFriendList(Integer userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            User owner = optionalUser.get();
            return friendshipRepository.findByOwner(owner);
        }
        return null;
    }
}
