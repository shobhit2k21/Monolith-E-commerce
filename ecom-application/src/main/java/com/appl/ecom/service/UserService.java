package com.appl.ecom.service;

import com.appl.ecom.dto.AddressDTO;
import com.appl.ecom.dto.UserRequest;
import com.appl.ecom.dto.UserResponse;
import com.appl.ecom.model.Address;
import com.appl.ecom.model.User;
import com.appl.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {                       //here all the request are handeled send by controller
    private final UserRepository userRepository;
   // private List<User> userList = new ArrayList<>();
    //private Long nextId = 1L;                   // variable for generating id not created by user


    public void addUser(UserRequest userRequest) {     // gives service to createUser
       User user = new User();
       updateUserFromRequest(user, userRequest);
       userRepository.save(user);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if(userRequest.getAddress() != null) {
            Address address= new Address();
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setStreet(userRequest.getAddress().getStreet());
            address.setZipcode(userRequest.getAddress().getZipcode());

            user.setAddress(address);
        }
    }

    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());                 // gives service to getAllUsers
    }


     public Optional<UserResponse> fetchUser(Long id) { // Using java stream
        return userRepository.findById(id).map(this::mapToUserResponse);
     }

     public boolean UpdateUser(Long id, UserRequest updateUserRequest) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, updateUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
     }

     private UserResponse mapToUserResponse(User user){
        UserResponse response = new UserResponse();
         response.setId(String.valueOf(user.getId()));
         response.setFirstName(user.getFirstName());
         response.setLastName(user.getLastName());
         response.setEmail(user.getEmail());
         response.setPhone(user.getPhone());
         response.setRole(user.getRole());

         if(user.getAddress() != null) {
             AddressDTO address = new AddressDTO();
             address.setStreet(user.getAddress().getStreet());
             address.setCity(user.getAddress().getCity());
             address.setState(user.getAddress().getState());
             address.setCountry(user.getAddress().getCountry());
             address.setZipcode(user.getAddress().getZipcode());
             response.setAddress(address);
         }

         return response;
     }
}
