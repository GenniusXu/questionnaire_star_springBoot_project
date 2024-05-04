package mygroup.questionnaire_star.controller;

import mygroup.questionnaire_star.pojo.Questionnaire;
import mygroup.questionnaire_star.pojo.Result;
import mygroup.questionnaire_star.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    @Autowired
    QuestionnaireService questionnaireService;

    @PostMapping("/add")
    public Result add(@RequestBody Questionnaire questionnaire) {
        questionnaireService.add(questionnaire);
        return Result.success();
    }

    @GetMapping("/list_all")
    public Result listAll(Integer creatorId) {
        List<Questionnaire> questionnaires = questionnaireService.listAll(creatorId);
        return Result.success(questionnaires);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Questionnaire questionnaire) {
        questionnaireService.update(questionnaire);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete(Integer id) {
        questionnaireService.delete(id);
        return Result.success();
    }
}
