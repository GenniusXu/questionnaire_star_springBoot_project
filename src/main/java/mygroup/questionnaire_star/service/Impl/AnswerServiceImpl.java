package mygroup.questionnaire_star.service.Impl;

import mygroup.questionnaire_star.mapper.AnswerMapper;
import mygroup.questionnaire_star.mapper.QuestionMapper;
import mygroup.questionnaire_star.mapper.QuestionnaireMapper;
import mygroup.questionnaire_star.pojo.*;
import mygroup.questionnaire_star.service.AnswerService;
import mygroup.questionnaire_star.service.QuestionService;
import mygroup.questionnaire_star.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    AnswerMapper answerMapper;
    @Autowired
    QuestionnaireMapper questionnaireMapper;
    @Autowired
    QuestionMapper questionMapper;

    @Override
    public Answer add(Answer answer) {
        answerMapper.add(answer);
        return answer;
    }

    @Override
    public List<Answer> listAllByQuestionId(Integer questionId) {
        List<Answer> answers = answerMapper.listAllByQuestionId(questionId);
        return answers;
    }

    @Override
    public List<Answer> listAllByFillerId(String fillerId) {
        List<Answer> answers = answerMapper.listAllByFillerId(fillerId);
        return answers;
    }

    @Override
    public List<Answer> deleteByFillerIdAndQustionnaireId(String fillerId, Integer questionnaireId) {
        List<Answer> answers = answerMapper.listAllByFillerId(fillerId);
        List<Answer> deletedAnswers = new ArrayList<>();
        for (Answer answer : answers) {
            Integer answerId = answer.getId();
            Integer questionId = answer.getQuestionId();
            Question question = questionMapper.findQuestionById(questionId);
            if (question != null) {
                if (question.getQuestionnaireId().equals(questionId)) {
                    deletedAnswers.add(answer);
                    answerMapper.deleteByAnswerId(answerId);
                }
            }
        }
        return deletedAnswers;
    }

    @Override
    public Answer update(Answer answer) {
        answerMapper.update(answer);
        return answer;
    }

    @Override
    public List<Answer> listAllByFillerIdAndQuestionnaireId(String fillerId, Integer questionnaireId) {
        List<Answer> answers = answerMapper.listAllByFillerId(fillerId);
        List<Answer> resAnswers = new ArrayList<>();
        for (Answer answer : answers) {
            Integer questionId = answer.getQuestionId();
            Question question = questionMapper.findQuestionById(questionId);
            if (question != null) {
                if (question.getQuestionnaireId().equals(questionnaireId)) {
                    resAnswers.add(answer);
                }
            }
        }
        return resAnswers;
    }


    @Override
    public List<AnswerOption> calculateOptionStats(Integer questionId) {
        // 查询数据库，获取该问题下的所有回答
        List<Answer> answers = answerMapper.listAllByQuestionId(questionId);
        int answerTotalCount = answers.size(); // 计算回答的总数

        // 使用Map来统计每个选项的出现次数
        Map<String, Integer> optionCountMap = new HashMap<>();

        // 遍历所有回答，如果是选择题的回答，统计每个单独选项的出现次数
        for (Answer answer : answers) {
            List<String> selectedOptions = answer.getSelectedOptions();
            //选择的选项为空，说明不是选择题的答案，跳过
            if (selectedOptions == null || selectedOptions.isEmpty()) {
                continue;
            }
            for (String option : selectedOptions) {
                optionCountMap.put(option, optionCountMap.getOrDefault(option, 0) + 1);//如果选项不存在，默认值设为0，然后加1
            }
        }
        Question question = questionMapper.findQuestionById(questionId);
        // 将统计结果转换为AnswerOption列表
        List<AnswerOption> stats = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : optionCountMap.entrySet()) {
            double rate = (double) entry.getValue() / answerTotalCount; // 计算选择比例
            // 直接使用 String.format()格式化为2位小数的字符串
            String rateStr = String.format("%.2f", rate);
            // 将这个字符串转换回 double 类型
            rate = Double.parseDouble(rateStr);
            if (question != null) {
                stats.add(new AnswerOption(questionId, question.getTitle(), entry.getKey(), entry.getValue(), rate));
            }
        }
        // 返回统计结果
        return stats;
    }

    //返回文本题所有文本
    @Override
    public List<AnswerText> listAllText(Integer questionId) {
        List<Answer> answers = answerMapper.listAllByQuestionId(questionId);
        List<AnswerText> texts = new ArrayList<>();
        for (Answer answer : answers) {
            Question question = questionMapper.findQuestionById(questionId);
            //填空题内的回答不为空，说明是填空题，才往texts里塞
            if (answer != null && !answer.getFillInAnswer().isEmpty() && question != null) {
                texts.add(new AnswerText(questionId, question.getTitle(), answer.getFillInAnswer()));
            }
        }
        return texts;
    }

    public List<AnswerFile> listAllFileURL(Integer questionId) {
        List<Answer> answers = answerMapper.listAllByQuestionId(questionId);
        List<AnswerFile> fileURLs = new ArrayList<>();
        for (Answer answer : answers) {
            Question question = questionMapper.findQuestionById(questionId);
            //FileURL不为空，说明是文件上传题，才往fileURLs里塞
            if (answer != null && !answer.getFileURL().isEmpty() && question != null) {
                fileURLs.add(new AnswerFile(questionId, question.getTitle(), answer.getFileURL()));
            }
        }
        return fileURLs;
    }

    @Override
    public Question getQuestion(Answer answer) {
        return questionMapper.findQuestionById(answer.getQuestionId());
    }

    @Override
    public Answer getAnswerById(Integer answerId) {
        Answer answer = answerMapper.getAnswerById(answerId);
        return answer;
    }

    public String uploadFile(MultipartFile file, String fileName) throws Exception {
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
        return url;
    }

    public String updateFile(String originUrl, MultipartFile newFile, String NewFileName) throws Exception {
        //删除原文件
        boolean issuccess = AliOssUtil.deleteFile(originUrl);
        if (!issuccess) {
            return null;
        }
        //上传新文件
        String NewUrl = this.uploadFile(newFile, NewFileName);
        return NewUrl;
    }


}
