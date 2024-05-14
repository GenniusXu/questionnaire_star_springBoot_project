package mygroup.questionnaire_star.controller;

import mygroup.questionnaire_star.pojo.Questionnaire;
import mygroup.questionnaire_star.pojo.Result;
import mygroup.questionnaire_star.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    @Autowired
    QuestionnaireService questionnaireService;

    @PostMapping("/add")
    public Result add(@RequestBody Questionnaire questionnaire) {
        questionnaire = questionnaireService.add(questionnaire);
        return Result.success(questionnaire);
    }

    @GetMapping("/list_all")
    public Result listAll(@RequestParam String creatorId) {
        List<Questionnaire> questionnaires = questionnaireService.listAll(creatorId);
        return Result.success(questionnaires);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Questionnaire questionnaire) {
        questionnaire = questionnaireService.update(questionnaire);
        return Result.success(questionnaire);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        questionnaireService.delete(id);
        return Result.success();
    }
}
