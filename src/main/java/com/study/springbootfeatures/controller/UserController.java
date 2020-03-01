package com.study.springbootfeatures.controller;

import com.study.springbootfeatures.dto.OperationStatusResponse;
import com.study.springbootfeatures.dto.UserRequestDTO;
import com.study.springbootfeatures.dto.UserResponseDTO;
import com.study.springbootfeatures.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> save(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity(userService.save(userRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{user-id}")
    public ResponseEntity<UserResponseDTO> update(@Valid
                                                  @RequestBody UserRequestDTO userRequestDTO,
                                                  @PathVariable("user-id") Long userId) {
        return new ResponseEntity(userService.update(userRequestDTO, userId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{user-id}")
    public ResponseEntity<OperationStatusResponse> delete(@PathVariable("user-id") Long userId) {
        return new ResponseEntity<>(userService.delete(userId), HttpStatus.OK);
    }

    @GetMapping(path = "/{user-id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable("user-id") Long userId) {
        return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(Pageable pageable) {
        return new ResponseEntity(userService.findAll(pageable), HttpStatus.OK);
    }
}
