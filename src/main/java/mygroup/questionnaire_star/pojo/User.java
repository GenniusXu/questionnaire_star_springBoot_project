package mygroup.questionnaire_star.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    @NotNull(message = "用户名不能为空！", groups = {onAdd.class, onUpdate.class})
    @NotEmpty(message = "用户名不能为空！", groups = {onAdd.class, onUpdate.class})
    String name;

    @NotNull(message = "密码不能为空！", groups = {onAdd.class, onUpdate.class})
    @NotEmpty(message = "密码不能为空！", groups = {onAdd.class, onUpdate.class})
    String password;

    String openid;

    //如果是直接传具体成员参数的，好像直接放到Controller的参数那里验证会好一点，
    // 但如果Controller里是直接传的User，那就只能在这里进行分组验证了

    @Pattern(regexp = "^[1][3-9][0-9]{9}$", message = "手机号格式不正确！", groups = onUpdate.class)
    String phoneNumber;

    @Email(message = "邮箱格式不正确！", groups = onUpdate.class)//用于校验，表示必须满足邮箱格式
    String Email;

    //List<Integer> questionnaires_created;
    //List<Integer> questionnaires_managed;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updateTime;//更新时间

    //用于分组验证的接口，没什么用的，就是一个分组的标记（记得要extends Default，不然SpringBoot识别不到）
    public interface onAdd extends Default {
    }

    ;

    public interface onUpdate extends Default{
    }

    ;
}
