package com.thoughtful.demo.type;

import lombok.Data;

@Data
public class NoteGenieRequest {
    private String promptMessage;
    private String historyId;
}
