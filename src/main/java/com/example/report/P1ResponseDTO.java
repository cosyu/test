package com.example.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class P1ResponseDTO {

    private String data;
    private P1ResponseResultDTO result;
    private P1ResponseAuditDTO audit;
}
