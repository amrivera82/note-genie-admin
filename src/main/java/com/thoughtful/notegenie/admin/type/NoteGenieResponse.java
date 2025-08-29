package com.thoughtful.demo.type;

import lombok.Data;

@Data
public class NoteGenieResponse {
    private String result;

    public NoteGenieResponse(String result) {
        this.result = result;
    }
}
