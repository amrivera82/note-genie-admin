package com.thoughtful.notegenie.admin.type;

import lombok.Data;

@Data
public class NoteGenieResponse {
    private String result;

    public NoteGenieResponse(String result) {
        this.result = result;
    }
}
