package com.fpt.edu.schedule.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fpt.edu.schedule.model.Slot;
import com.fpt.edu.schedule.model.Subject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpectedDTO {
    private List<String> subjects;
    private List<String> slots;
    private String note = "";
    private int maxClass;
    private String userId;
}
