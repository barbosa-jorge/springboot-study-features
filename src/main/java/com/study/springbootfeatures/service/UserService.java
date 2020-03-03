package com.study.springbootfeatures.service;

import com.study.springbootfeatures.dto.OperationStatusResponse;
import com.study.springbootfeatures.dto.UserRequestDTO;
import com.study.springbootfeatures.dto.UserResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserResponseDTO save(UserRequestDTO userRequestDTO);
    UserResponseDTO update(UserRequestDTO userRequestDTO, Long userId);
    OperationStatusResponse delete(Long userId);
    UserResponseDTO findById(Long userId);
    List<UserResponseDTO> findAll(Pageable pageable);
    UserResponseDTO getUserByEmail(String email);
}
