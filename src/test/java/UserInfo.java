import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @author lmy
 * @description UserInfo
 * @date 2018/8/21
 */
public class UserInfo {

    //默认分组 Default.class
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11)
    private String phone;

    //默认分组 Default.class
    @NotBlank(message = "用户名不能为空")
    @Length(min = 6, max = 32)
    private String username;

    //默认分组 Default.class
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 32)
    private String password;

    //RegisterGroup分组 RegisterGroup.class
    @Range(min = 18, max = 120, message = "年龄应该在18到120周岁之间", groups = {RegisterGroup.class})
    private int age;

    //RegisterGroup分组 RegisterGroup.class
    @NotBlank(message = "生日不能为空")
    @Past(message = "生日必须大于今天", groups = {RegisterGroup.class})
    private Date birthday;
}
