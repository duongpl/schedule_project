package com.fpt.edu.schedule.dto;

import com.fpt.edu.schedule.ai.model.GeneticAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Getter
@Setter
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TimetableProcess {
    public Map<String, GeneticAlgorithm> map = new HashMap<>();
}
