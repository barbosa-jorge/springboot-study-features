package com.study.springbootfeatures.service;

import com.study.springbootfeatures.dto.OperationStatusResponse;
import com.study.springbootfeatures.dto.UserRequestDTO;
import com.study.springbootfeatures.dto.UserResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserResponseDTO save(UserRequestDTO userRequestDTO);
    UserResponseDTO update(UserRequestDTO userRequestDTO, Long userId);
    OperationStatusResponse delete(Long userId);
    UserResponseDTO findById(Long userId);
    List<UserResponseDTO> findAll(Pageable pageable);
}
