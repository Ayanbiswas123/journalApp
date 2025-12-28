package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){

        User existingUser = userService.findByUserName(user.getUserName());

        if(existingUser != null){
            return new ResponseEntity<>("User Already Exist", HttpStatus.CONFLICT);
        }
        userService.saveEntry(user);
        return new ResponseEntity<>("User Created Successfully",HttpStatus.OK);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName){
        try{
            User userInDb = userService.findByUserName(userName);
            if(userInDb != null){
                userInDb.setUserName(user.getUserName());
                userInDb.setPassword(user.getPassword());
                userService.saveEntry(userInDb);
            }
            return new ResponseEntity<>("User Updated Successfully",HttpStatus.NO_CONTENT);
        }catch (Exception e){
            System.out.println("Exception" + e);
        }
        return new ResponseEntity<>("User Not Exist",HttpStatus.NOT_FOUND);
    }
}
