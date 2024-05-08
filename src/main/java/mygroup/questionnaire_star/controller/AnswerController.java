package mygroup.questionnaire_star.controller;

import mygroup.questionnaire_star.pojo.Answer;
import mygroup.questionnaire_star.pojo.Result;
import mygroup.questionnaire_star.service.AnswerService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    AnswerService answerService;

    @PostMapping("/add")
    Result add(@RequestBody Answer answer) {
        answer = answerService.add(answer);
        return Result.success(answer);
    }

    @GetMapping("/list_all_by_questionId")
    Result listAllByQuestionId(@RequestParam Integer questionId){
        List<Answer> answers = answerService.listAllByQuestionId(questionId);
        return Result.success(answers);
    }

    @GetMapping("/list_all_by_fillerId")
    Result listAllByFillerIdId(@RequestParam Integer fillerId){
        List<Answer> answers = answerService.listAllByFillerIdId(fillerId);
        return Result.success(answers);
    }

    @DeleteMapping("/delete_by_filler_id_and_question_id")
    Result deleteByFillerId(@RequestParam Integer fillerId, @RequestParam Integer questionnaireId){
        List<Answer> answers = answerService.deleteByFillerIdAndQustionnaireId(fillerId, questionnaireId);
        return Result.success(answers);
    }

}
