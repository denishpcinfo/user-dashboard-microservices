package com.d3n15.user_info_service.service;

import com.d3n15.user_info_service.dto.UserInfoDTO;
import com.d3n15.user_info_service.entity.UserEntity;
import com.d3n15.user_info_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService {

    private final UserRepository userRepository;

    public UserInfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInfoDTO getUserInfoByUserId(String userId) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado: " + userId);
        }

        UserEntity user = optionalUser.get();
        return new UserInfoDTO(user.getName(), user.getCpf(), user.getPhone());
    }
}
