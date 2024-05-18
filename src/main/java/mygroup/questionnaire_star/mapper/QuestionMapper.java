package mygroup.questionnaire_star.mapper;

import mygroup.questionnaire_star.pojo.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Select("select * from question where questionnaire_id=#{questionnaireId}")
    @Results({
            @Result(property = "options", column = "options", typeHandler = mygroup.questionnaire_star.config.StringListTypeHandler.class)
    })
    public List<Question> listAll(Integer questionnaireId);

    @Insert("insert into question (number,questionnaire_id,type,title,options) " +
            "values (#{number},#{questionnaireId},#{type},#{title},#{options})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void add(Question question);

    @Delete("delete from question where id=#{id}")
    public void delete(Integer id);

    @Select("SELECT type FROM question WHERE id=#{id}")
    public String getType(Integer id);

    @Update("update question set number=#{number},type=#{type},title=#{title}," +
            "options=#{options} where id=#{id}")
    @Results({
            @Result(property = "options", column = "options", typeHandler = mygroup.questionnaire_star.config.StringListTypeHandler.class)
    })
    public void update(Question question);

    @Select("select * from question where id=#{questionId}")
    @Results({
            @Result(property = "options", column = "options", typeHandler = mygroup.questionnaire_star.config.StringListTypeHandler.class)
    })
    Question findQuestionById(Integer questionId);
}
