package mygroup.questionnaire_star.mapper;

import mygroup.questionnaire_star.pojo.Questionnaire;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionnaireMapper {
    @Insert("INSERT INTO questionnaire (creator_id, managers_id, status, create_time, update_time) VALUES (#{creatorId}, " +
            "#{managersId}, #{status}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void add(Questionnaire questionnaire);

    @Select("SELECT * FROM questionnaire where creator_id=#{creatorId}")
    List<Questionnaire> listAll(String creatorId);

    @Update("UPDATE questionnaire SET managers_id = #{managersId}, status = #{status}, update_time = #{updateTime} WHERE id = #{id}")
    void update(Questionnaire questionnaire);

    @Delete("DELETE FROM questionnaire WHERE id = #{id}")
    void delete(Integer id);

    @Select("SELECT * FROM questionnaire where id=#{questionnaireId}")
    Questionnaire getQuestionnaireById(Integer questionnaireId);
}
