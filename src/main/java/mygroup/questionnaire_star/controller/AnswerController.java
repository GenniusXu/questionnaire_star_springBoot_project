package mygroup.questionnaire_star.controller;

import mygroup.questionnaire_star.pojo.*;
import mygroup.questionnaire_star.service.AnswerService;
import mygroup.questionnaire_star.service.QuestionService;
import mygroup.questionnaire_star.service.QuestionnaireService;
import mygroup.questionnaire_star.utils.AliOssUtil;
import mygroup.questionnaire_star.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    AnswerService answerService;
    @Autowired
    QuestionService questionService;
    @Autowired
    QuestionnaireService questionnaireService;



    //增加一条回答
    @PostMapping("/add")
    Result add(@RequestBody Answer answer) {
        LocalDateTime now = LocalDateTime.now();
        Question question = answerService.getQuestion(answer);
        Questionnaire questionnaire=questionService.getQuestionnaire(question);

        //如果超出最末时间
        if(questionnaire.getEndTime().isBefore(now)){
            return Result.error("问卷已经于"+questionnaire.getEndTime()+"结束");
        }
        //如果还未开始
        if(questionnaire.getStartTime().isAfter(now)){
            return Result.error("问卷还没开始,将于"+questionnaire.getStartTime()+"开始");
        }
        if(questionnaire.getStatus()!="已发布"){
            return Result.error("问卷已经停止收集");
        }

        answer = answerService.add(answer);
        return Result.success(answer);
    }

    //增加一条回答（回答中可能带有文件）（好像不太行）
    /*@PostMapping("/add")
    Result addAnswerWithFile(@RequestParam("file") MultipartFile file, @RequestParam String fileName, @RequestBody Answer answer) throws Exception {
        //如果前端没有有指定文件名，那就用文件自己本身的文件名
        if (fileName.isEmpty()) {
            //在原文件名的基础上加上UUID，保证文件的名字是唯一的,从而防止文件覆盖
            String originalFilename = file.getOriginalFilename();
            //fileName = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
            fileName = UUID.randomUUID().toString() + originalFilename;
        }
        //如果前端有指定文件名，那就按照前端指定的文件名fileName（但要加上扩展名）
        else {
            //获取文件扩展名
            String extension = file.getOriginalFilename().substring(fileName.lastIndexOf(".") + 1);
            fileName = fileName + "." + extension;
        }
        //将文件上传到阿里云，并获取URL
        String url = AliOssUtil.uploadFile(fileName, file.getInputStream());
        answer.setFileURL(url);
        answer = answerService.add(answer);
        return Result.success(answer);
    }*/


    //列出某个问题的所有回答
    @GetMapping("/list_all_by_question_id")
    Result listAllByQuestionId(@RequestParam Integer questionId) {
        List<Answer> answers = answerService.listAllByQuestionId(questionId);
        return Result.success(answers);
    }

    //列出某个用户的所有回答
    @GetMapping("/list_all_by_filler_id")
    Result listAllByFillerId(@RequestParam String fillerId) {
        List<Answer> answers = answerService.listAllByFillerId(fillerId);
        return Result.success(answers);
    }

    //列出某个用户在某份问卷中的所有回答
    @GetMapping("/list_all_by_filler_id_and_qustionnaire_id")
    Result listAllByFillerIdAndQuestionnaireId(@RequestParam String fillerId, @RequestParam Integer questionnaireId) {
        List<Answer> answers = answerService.listAllByFillerIdAndQuestionnaireId(fillerId, questionnaireId);
        return Result.success(answers);
    }

    //删除某个用户在某份问卷中的所有回答
    @DeleteMapping("/delete_by_filler_id_and_question_id")
    Result deleteByFillerId(@RequestParam String fillerId, @RequestParam Integer questionnaireId) {
        List<Answer> answers = answerService.deleteByFillerIdAndQustionnaireId(fillerId, questionnaireId);
        return Result.success(answers);
    }

    //更新一条回答
    @PutMapping("/update")
    Result update(@RequestBody Answer answer) {
        LocalDateTime now = LocalDateTime.now();
        Question question = answerService.getQuestion(answer);
        Questionnaire questionnaire=questionService.getQuestionnaire(question);

        //如果超出最末时间
        if(questionnaire.getEndTime().isBefore(now)){
            return Result.error("问卷已经于"+questionnaire.getEndTime()+"结束");
        }
        //如果还未开始
        if(questionnaire.getStartTime().isAfter(now)){
            return Result.error("问卷还没开始,将于"+questionnaire.getStartTime()+"开始");
        }
        if(questionnaire.getStatus()!="已发布"){
            return Result.error("问卷已经停止收集");
        }

        answer = answerService.update(answer);
        return Result.success(answer);
    }

    //获取选项计数
    @GetMapping("/calculateOptionStats")
    Result calculateOptionStats(Integer questionId) {

        String type = questionService.getType(questionId);
        //if(!(type.equals("单选题")||type.equals("多选题")))return Result.error("本题不是选择题");
        List<AnswerOption> stats = answerService.calculateOptionStats(questionId);
        System.out.println(stats);
        return Result.success(stats);
    }

    //获取文本题答案
    @GetMapping("/listAllText")
    Result listAllText(Integer questionId) {
        String type = questionService.getType(questionId);
        if (!type.equals("填空题")) return Result.error("本题不是填空题");
        List<AnswerText> texts = answerService.listAllText(questionId);
        return Result.success(texts);
    }

    @PostMapping("/upload_file")
    public Result uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String fileName) throws Exception {
        //如果前端没有有指定文件名，那就用文件自己本身的文件名
        if (fileName.isEmpty()) {
            //在原文件名的基础上加上UUID，保证文件的名字是唯一的,从而防止文件覆盖
            String originalFilename = file.getOriginalFilename();
            //fileName = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
            fileName = UUID.randomUUID().toString() + originalFilename;
        }
        //如果前端有指定文件名，那就按照前端指定的文件名fileName（但要加上扩展名）
        else {
            //获取文件扩展名
            String extension = file.getOriginalFilename().substring(fileName.lastIndexOf(".") + 1);
            fileName = fileName + "." + extension;
        }
        //将文件上传到阿里云，并获取URL
        String url = AliOssUtil.uploadFile(fileName, file.getInputStream());
        //String url = "http://模拟url";
        return Result.success(url);
    }
}
