package mygroup.questionnaire_star.mapper;

import mygroup.questionnaire_star.pojo.Answer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AnswerMapper {

    @Insert("insert into answer (question_id,filler_id,selected_options,fill_in_answer) " +
            "values (#{questionId},#{fillerId},#{selectedOptions},#{fillInAnswer})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void add(Answer answer);

    @Select("select * from answer where question_id=#{questionId}")
    List<Answer> listAllByQuestionId(Integer questionId);

    @Select("select * from answer where filler_id=#{fillerId}")
    List<Answer> listAllByFillerIdId(Integer fillerId);

    @Delete("delete from answer where id=#{answerId}")
    void deleteByAnswerId(Integer answerId);
}
