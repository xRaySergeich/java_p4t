package ru.p4t.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class IssueResponse {
    @JsonProperty("issues")
    List<Issue> issues;
}
