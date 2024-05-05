package mygroup.questionnaire_star.controller;

import mygroup.questionnaire_star.pojo.Question;
import mygroup.questionnaire_star.pojo.Questionnaire;
import mygroup.questionnaire_star.pojo.Result;
import mygroup.questionnaire_star.service.QuestionService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("/list_all")
    Result listAll(@RequestParam Integer questionnaireId){
        List<Question> questions = questionService.listAll(questionnaireId);
        return Result.success(questions);
    }

    @PostMapping("/add")
    Result add(@RequestBody Question question){
        question = questionService.add(question);
        return Result.success(question);
    }

    @DeleteMapping("/delete")
    Result delete(@RequestParam Integer id){
        questionService.delete(id);
        return Result.success();
    }

    @PutMapping("/update")
    Result update(@RequestBody Question question) {
        question = questionService.update(question);
        return Result.success(question);
    }
}
