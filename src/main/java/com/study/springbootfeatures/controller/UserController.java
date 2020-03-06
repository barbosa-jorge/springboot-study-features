package com.study.springbootfeatures.controller;

import com.study.springbootfeatures.dto.OperationStatusResponse;
import com.study.springbootfeatures.dto.UserRequestDTO;
import com.study.springbootfeatures.dto.UserResponseDTO;
import com.study.springbootfeatures.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity(userService.save(userRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{user-id}")
    public ResponseEntity<UserResponseDTO> update(@Valid
                                                      @RequestBody UserRequestDTO userRequestDTO,
                                                      @PathVariable("user-id") String userId) {
        return new ResponseEntity(userService.update(userRequestDTO, userId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
    //@PreAuthorize("hasAuthority('DELETE_AUTHORITY')")
    //@Secured("ROLE_ADMIN")
    @DeleteMapping(path = "/{user-id}")
    public ResponseEntity<OperationStatusResponse> delete(@PathVariable("user-id") String userId) {
        return new ResponseEntity<>(userService.delete(userId), HttpStatus.OK);
    }

    @ApiOperation(value="The Get User Details Web Service Endpoint",
            notes="${userController.findUserById.ApiOperation.Notes}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization",
                    value="${userController.authorizationHeader.description}", paramType="header")
    })
    @GetMapping(path = "/{user-id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable("user-id") String userId) {
        return new ResponseEntity<>(userService.findByUserId(userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(Pageable pageable) {
        return new ResponseEntity(userService.findAll(pageable), HttpStatus.OK);
    }
}
