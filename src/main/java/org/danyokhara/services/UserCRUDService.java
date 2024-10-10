package org.danyokhara.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danyokhara.dto.UserDto;
import org.danyokhara.model.User;
import org.danyokhara.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserCRUDService implements CRUDService<UserDto> {
    private final UserRepository userRepository;
    @Override
    public UserDto getById(Integer id) {
        log.info("Get user " + id);
        return mapToDto(userRepository.findById(id).orElseThrow());
    }

    @Override
    public Collection<UserDto> getAll() {
        return userRepository.findAll().stream().map(UserCRUDService::mapToDto).toList();
    }

    @Override
    public void create(UserDto item) {
        User user = mapToEntity(item);
        userRepository.save(user);
        log.info("User " + item.getName() + " created");
        item.setId(user.getId());
    }

    @Override
    public void update(UserDto item) {
        User user = mapToEntity(item);
        userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public boolean init(String sessionId){
        return !userRepository.getAllBySessionId(sessionId).isEmpty();
    }

    public static UserDto mapToDto(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setSessionId(user.getSessionId());
        dto.setName(user.getName());
        return dto;
    }

    public static User mapToEntity(UserDto dto){
        User user = new User();
        user.setSessionId(dto.getSessionId());
        user.setName(dto.getName());
        return user;
    }
}
