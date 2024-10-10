package org.danyokhara.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danyokhara.dto.MessageDto;
import org.danyokhara.model.Message;
import org.danyokhara.repositories.MessageRepository;
import org.danyokhara.repositories.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageCRUDService implements CRUDService<MessageDto>{

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    @Override
    public MessageDto getById(Integer id) {
        log.info("Show message");
        return mapToDto(messageRepository.findById(id).orElseThrow());
    }

    @Override
    public List<MessageDto> getAll() {
        return messageRepository.findAll(Sort.by(Sort.Direction.ASC, "dateTime"))
                .stream().map(MessageCRUDService::mapToDto).toList();
    }

    @Override
    public void create(MessageDto item) {
        log.info("Messaging...");
        Message message = mapToEntity(item);
        message.setUser(
                userRepository.findBySessionId(
                        RequestContextHolder.currentRequestAttributes().getSessionId()).orElseThrow());
        messageRepository.save(message);
    }

    @Override
    public void update(MessageDto item) {
        log.info("Refresh");
        Message message = mapToEntity(item);
        message.setUser(
                userRepository.findBySessionId(
                        RequestContextHolder.currentRequestAttributes().getSessionId()).orElseThrow());
        messageRepository.save(message);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public static MessageDto mapToDto(Message message){
        MessageDto dto = new MessageDto();
        dto.setId(message.getId());
        dto.setText(message.getMessage());
        dto.setDateTime(message.getDateTime());
        dto.setUserName(message.getUser().getName());
        return dto;
    }
    public static Message mapToEntity(MessageDto dto){
        Message message = new Message();
        message.setMessage(dto.getText());
        message.setDateTime(LocalDateTime.now());
        return message;
    }
}
