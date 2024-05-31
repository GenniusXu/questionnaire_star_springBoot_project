package mygroup.questionnaire_star.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface MsmService {
    Boolean send(Map<String, Object> param, String phone);
}
