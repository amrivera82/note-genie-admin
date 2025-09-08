package com.thoughtful.notegenie.admin.controller;

import com.thoughtful.notegenie.admin.service.NoteGenieSessionService;
import com.thoughtful.notegenie.admin.type.NoteGenieRequest;
import com.thoughtful.notegenie.admin.type.NoteGenieResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/session")
public class NoteGenieSessionController {
    private final NoteGenieSessionService noteGenieService;

    @PostMapping("/login")
    public ResponseEntity<NoteGenieResponse> logIn(@RequestBody NoteGenieRequest noteGenieRequest) {
        log.debug("Received request at /login");
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
