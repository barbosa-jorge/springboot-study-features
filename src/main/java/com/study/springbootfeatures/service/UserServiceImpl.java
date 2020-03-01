package com.study.springbootfeatures.service;

import com.study.springbootfeatures.dto.*;
import com.study.springbootfeatures.entity.UserEntity;
import com.study.springbootfeatures.exception.UserNotFoundException;
import com.study.springbootfeatures.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserResponseDTO save(UserRequestDTO userRequestDTO) {
        UserEntity user = modelMapper.map(userRequestDTO, UserEntity.class);

        UserEntity savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    public UserResponseDTO update(UserRequestDTO userRequestDTO, Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User of id: %s was not found", userId)));

        UserEntity user = modelMapper.map(userRequestDTO, UserEntity.class);
        user.setId(userId);

        UserEntity savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserResponseDTO.class);

    }

    public OperationStatusResponse delete(Long userId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User of id: %s was not found", userId)));

        userRepository.delete(user);

        OperationStatusResponse response = new OperationStatusResponse();
        response.setOperationName(RequestOperationName.DELETE.name());
        response.setOperationStatus(RequestOperationStatus.SUCCESS.name());

        return response;

    }

    public UserResponseDTO findById(Long userId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User of id: %s was not found", userId)));

        return modelMapper.map(user, UserResponseDTO.class);

    }

    @Override
    public List<UserResponseDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).get()
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }
}
