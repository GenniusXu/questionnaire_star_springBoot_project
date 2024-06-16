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
import java.util.ArrayList;
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

    @GetMapping("/getAnswerById")
    Result getAnswerById(@RequestParam Integer answerId) {
        Answer answer = answerService.getAnswerById(answerId);
        return Result.success(answer);
    }

    //增加一条回答
    @PostMapping("/add")
    Result add(@RequestBody Answer answer) {
        LocalDateTime now = LocalDateTime.now();
        Question question = answerService.getQuestion(answer);

        if (question == null) {
            return Result.error("无效的问题id");
        }
        Questionnaire questionnaire = questionService.getQuestionnaire(question);
        if (questionnaire == null) {
            return Result.error("无效的问卷id");
        }
        if (questionnaire.getStartTime() == null) {
            return Result.error("未设置开始时间");
        }
        if (questionnaire.getEndTime() == null) {
            return Result.error("未设置结束时间");
        }
        //如果超出最末时间
        if (questionnaire.getEndTime().isBefore(now)) {
            return Result.error("问卷已经于" + questionnaire.getEndTime() + "结束");
        }
        //如果还未开始
        if (questionnaire.getStartTime().isAfter(now)) {
            return Result.error("问卷还没开始,将于" + questionnaire.getStartTime() + "开始");
        }
        if (!questionnaire.getStatus().equals("已发布")) {
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

        if (question == null) {
            return Result.error("无效的问题id");
        }
        Questionnaire questionnaire = questionService.getQuestionnaire(question);

        if (questionnaire == null) {
            return Result.error("无效的问卷id");
        }

        if (questionnaire.getStartTime() == null) {
            return Result.error("未设置开始时间");
        }

        if (questionnaire.getEndTime() == null) {
            return Result.error("未设置结束时间");
        }

        //如果超出最末时间
        if (questionnaire.getEndTime().isBefore(now)) {
            return Result.error("问卷已经于" + questionnaire.getEndTime() + "结束");
        }
        //如果还未开始
        if (questionnaire.getStartTime().isAfter(now)) {
            return Result.error("问卷还没开始,将于" + questionnaire.getStartTime() + "开始");
        }
        if (!questionnaire.getStatus().equals("已发布")) {
            return Result.error("问卷已经停止收集");
        }

        //修改文件题回答的特殊处理（需要先查找并删除旧的文件（如果有旧的文件，且旧文件跟新文件不一样的话）
        // （必须是不一样的Url，否则调两次接口且没有上传新文件的话，就会删了旧的（也是新的），Url就会指向一个不存在的文件）
        Answer originAnswer = answerService.getAnswerById(answer.getId());
        String originUrl = originAnswer.getFileURL();
        String newUrl = answer.getFileURL();
        if (newUrl != null && originUrl != null && !newUrl.isEmpty() && !originUrl.isEmpty() && !newUrl.equals(originUrl)) {
            AliOssUtil.deleteFile(originUrl);
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
        //System.out.println(stats);
        return Result.success(stats);
    }

    //分析整个问卷的选择题
    @GetMapping("/calculateAllOptionStats")
    Result calculateAllOptionStats(Integer questionnaireId) {

        Questionnaire questionnaire = questionnaireService.getById(questionnaireId);
        List<Question> questions = questionService.listAll(questionnaireId);
        //out.println(questions);
        List<List<AnswerOption>> allstats = new ArrayList<>();
        for (Question question : questions) {
            String type = question.getType();
            Integer questionId = question.getId();
            if (type.equals("单选题") || type.equals("多选题")) {
                List<AnswerOption> stats = answerService.calculateOptionStats(questionId);
                //非空才加进结果里，避免因为有些问题没有人回答而导致最后返回的结果有一堆的空[]
                if (!stats.isEmpty()) {
                    allstats.add(stats);
                }
            }

        }
        //System.out.println(allstats);
        return Result.success(allstats);
    }

    //获取文本题答案
    @GetMapping("/listAllText")
    Result listAllText(Integer questionId) {
        String type = questionService.getType(questionId);
        if (!type.equals("填空题")) return Result.error("本题不是填空题");
        List<AnswerText> texts = answerService.listAllText(questionId);
        return Result.success(texts);
    }

    //获取所有类型回答的数据分析（包括单选、多选、填空）
    @GetMapping("/getAllTypeResultStats")
    Result getAllTypeResultStats(@RequestParam Integer questionnaireId) {
        Questionnaire questionnaire = questionnaireService.getById(questionnaireId);
        List<Question> questions = questionService.listAll(questionnaireId);
        //System.out.println(questions);
        List<List<AnswerOption>> allOptionStats = new ArrayList<>();
        List<List<AnswerText>> allTextStats = new ArrayList<>();
        List<List<AnswerFile>> allFileStats = new ArrayList<>();
        // 创建一个类型为 Object 的 ArrayList 来保存合并后的结果
        ArrayList<Object> AllTypeResultStats = new ArrayList<Object>();
        for (Question question : questions) {
            String type = question.getType();
            Integer questionId = question.getId();
            if (type.equals("单选题") || type.equals("多选题")) {
                List<AnswerOption> stats = answerService.calculateOptionStats(questionId);
                //非空才加进结果里，避免因为有些问题没有人回答而导致最后返回的结果有一堆的空[]
                if (!stats.isEmpty()) {
                    allOptionStats.add(stats);
                }
            } else if (type.equals("填空题")) {
                List<AnswerText> texts = answerService.listAllText(questionId);
                //非空才加进结果里，避免因为有些问题没有人回答而导致最后返回的结果有一堆的空[]
                if (!texts.isEmpty()) {
                    allTextStats.add(texts);
                }
            } else if (type.equals("文件上传题")) {
                List<AnswerFile> fileURLs = answerService.listAllFileURL(questionId);
                //非空才加进结果里，避免因为有些问题没有人回答而导致最后返回的结果有一堆的空[]
                if (fileURLs != null && !fileURLs.isEmpty()) {
                    allFileStats.add(fileURLs);
                }
            }
        }
        AllTypeResultStats.addAll(allOptionStats);
        AllTypeResultStats.addAll(allTextStats);
        AllTypeResultStats.addAll(allFileStats);
        return Result.success(AllTypeResultStats);
    }

    @PostMapping("/upload_file")
    public Result uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String fileName) throws Exception {
        String url = answerService.uploadFile(file, fileName);
        return Result.success(url);
    }

    @PutMapping("/updateFile")
    public Result updateFile(@RequestParam String originUrl, @RequestParam("newFile") MultipartFile newFile,
                             @RequestParam String NewFileName) throws Exception {
        String NewUrl = answerService.updateFile(originUrl, newFile, NewFileName);
        return Result.success(NewUrl);
    }

    //仅仅筛选填空题，将包含garbageString的回答筛掉，将长度小于minimumLength的回答筛掉
    @GetMapping("/fillter")
    public Result fillter(@RequestParam Integer questionId, @RequestParam String garbageString, @RequestParam Integer minimumLength) {
        Question question = questionService.getQuestionById(questionId);
        if (question == null) {
            return Result.error("无效的questionId");
        } else if (!question.getType().equals("填空题")) {
            return Result.error("该问题不是填空题！");
        }
        List<Answer> answers = answerService.listAllByQuestionId(questionId);
        //所有有效回答
        List<AnswerText> effectiveAnswers = new ArrayList<>();
        //如果前端没传，则将garbageString和minimumLength设置一个默认值
        if (garbageString == null || garbageString.isEmpty()) {
            garbageString = "傻逼!";
        }
        if (minimumLength == null || minimumLength < 0) {
            minimumLength = 0;
        }
        for (Answer answer : answers) {
            //如果是填空题
            if (answer.getFillInAnswer() != null && !answer.getFillInAnswer().isEmpty()) {
                //不包含garbageString的、长度大于等于minimumLength的，才加入有效回答effectiveAnswers中
                if (!answer.getFillInAnswer().contains(garbageString) && answer.getFillInAnswer().length() >= minimumLength) {
                    effectiveAnswers.add(new AnswerText(questionId, question.getTitle(), answer.getFillInAnswer()));
                }
            }
        }
        return Result.success(effectiveAnswers);
    }

}
