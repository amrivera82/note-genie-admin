package com.thoughtful.notegenie.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thoughtful.notegenie.admin.service.NoteGenieSignUpService;
import com.thoughtful.notegenie.admin.type.NoteGenieRequest;
import com.thoughtful.notegenie.admin.type.NoteGenieResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/note-genie/admin")
public class NoteGenieSignUpController {
    private final NoteGenieSignUpService noteGenieService;
    
    @PostMapping("/login")
    public ResponseEntity<NoteGenieResponse> logIn(@RequestBody NoteGenieRequest noteGenieRequest) {
        noteGenieService.call();
        return new ResponseEntity<>(HttpStatus.OK);
    }

        @PostMapping("/logout")
    public ResponseEntity<NoteGenieResponse> logOut(@RequestBody NoteGenieRequest noteGenieRequest) {
        noteGenieService.call();
        return new ResponseEntity<>(HttpStatus.OK);
    }

        @PostMapping("session/validate")
    public ResponseEntity<NoteGenieResponse> validateSession(@RequestBody NoteGenieRequest noteGenieRequest) {
        noteGenieService.call();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
