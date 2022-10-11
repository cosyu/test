package com.example.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class P1ResponseResultDTO {

    private int code;
    private String message;
    private String codeDateTime;
}
