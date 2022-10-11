package com.example.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class P1ResponseAuditDTO {

    private String documentId;
    private String applicantName;
    private String organizationName;
}
