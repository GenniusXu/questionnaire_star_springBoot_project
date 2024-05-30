package mygroup.questionnaire_star.mapper;

import mygroup.questionnaire_star.pojo.Answer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AnswerMapper {

    @Insert("insert into answer (question_id,filler_id,selected_options,fill_in_answer,fileURL) " +
            "values (#{questionId},#{fillerId},#{selectedOptions},#{fillInAnswer},#{fileURL})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void add(Answer answer);

    //因为现在有两个TypeHandler，所以需要用@Result注解来指定为某个字段使用哪个TypeHandler，否则会报错
    @Select("select * from answer where question_id=#{questionId}")
    @Results({
            @Result(property = "selectedOptions", column = "selected_options", typeHandler = mygroup.questionnaire_star.config.StringListTypeHandler.class)
    })
    List<Answer> listAllByQuestionId(Integer questionId);

    @Results({
            @Result(property = "selectedOptions", column = "selected_options", typeHandler = mygroup.questionnaire_star.config.StringListTypeHandler.class)
    })
    @Select("select * from answer where filler_id=#{fillerId}")
    List<Answer> listAllByFillerId(String fillerId);

    @Delete("delete from answer where id=#{answerId}")
    void deleteByAnswerId(Integer answerId);

    @Results({
            @Result(property = "selectedOptions", column = "selected_options", typeHandler = mygroup.questionnaire_star.config.StringListTypeHandler.class)
    })
    @Update("update answer set question_id=#{questionId},filler_id=#{fillerId},selected_options=#{selectedOptions}," +
            "fill_in_answer=#{fillInAnswer},fileURL=#{fileURL} where id=#{id}")
    void update(Answer answer);

    @Results({
            @Result(property = "selectedOptions", column = "selected_options", typeHandler = mygroup.questionnaire_star.config.StringListTypeHandler.class)
    })
    @Select("select * from answer where id=#{answerId};")
    Answer getAnswerById(Integer answerId);
}
