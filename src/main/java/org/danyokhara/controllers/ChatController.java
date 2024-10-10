package org.danyokhara.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danyokhara.dto.MessageDto;
import org.danyokhara.dto.UserDto;

import org.danyokhara.services.MessageCRUDService;
import org.danyokhara.services.UserCRUDService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
public class ChatController {
    private final UserCRUDService userCRUDService;
    private final MessageCRUDService messageCRUDService;

    @GetMapping("/init")
    public HashMap<String, Boolean> init(){
        HashMap<String, Boolean> response = new HashMap<>();
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        response.put("result", userCRUDService.init(sessionId));
        return response;
    }
    @PostMapping("/message")
    public HashMap<String, Boolean> sendMessage(@RequestParam String message){
        HashMap<String, Boolean> response = new HashMap<>();
        MessageDto dto = new MessageDto();
        dto.setText(message);
        if(message.isEmpty()){
            log.error("Message is empty");
            response.put("result", false);
            return response;
        }
        try {
            messageCRUDService.create(dto);
            response.put("result", true);
            return response;
        } catch (Exception e){
            log.error("Message error");
            response.put("result", false);
            return response;
        }
    }
    @PostMapping("/auth")
    public HashMap<String, Boolean> auth(@Valid @RequestParam String name){
        HashMap<String, Boolean> response = new HashMap<>();
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        UserDto dto = new UserDto();
        dto.setName(name);
        dto.setSessionId(sessionId);
        try {
            userCRUDService.create(dto);
        } catch (Exception ex){
            log.error(ex.getMessage());
            response.put("result", false);
            return response;
        }
        response.put("result", true);
        return response;
    }
    @GetMapping("/user")
    public Collection<UserDto> getUsersList(){
        return userCRUDService.getAll();
    }
    @GetMapping("/message")
    public List<MessageDto> getMessagesList(){
        return messageCRUDService.getAll();
    }
}
