package com.thoughtful.notegenie.admin.type;

import lombok.Data;

@Data
public class NoteGenieRequest {
    private String promptMessage;
    private String historyId;
}
