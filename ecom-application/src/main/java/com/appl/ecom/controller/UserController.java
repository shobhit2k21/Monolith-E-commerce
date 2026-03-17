package com.appl.ecom.controller;

import com.appl.ecom.service.UserService;
import com.appl.ecom.dto.UserRequest;
import com.appl.ecom.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users") // ese GetMapping , PutMapping baar baar /api/users nhi likhna padega

public class UserController {

    @Autowired
    private UserService userService; // AutoWired Service all functions are present in UserService class

    @GetMapping    // it maps with url  // getting all users
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")  // getting particular user by id
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return userService.fetchUser(id).map(ResponseEntity::ok)   // this line is another approch of above code using streams
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping  // creating or adding user using Postman
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
        userService.addUser(userRequest);
        return ResponseEntity.ok("User Added successfully");
    }

     @PutMapping("/{id}")
     public ResponseEntity<String> PutUser(@PathVariable Long id,  @RequestBody UserRequest userRequest) {
        boolean updated = userService.UpdateUser(id, userRequest);
          if(updated)
        return ResponseEntity.ok("User Updated Successfully");

          return ResponseEntity.notFound().build();
     }

}
