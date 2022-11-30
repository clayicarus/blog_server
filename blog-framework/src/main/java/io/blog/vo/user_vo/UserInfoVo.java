package io.blog.vo.user_vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserInfoVo {
    private String avatar;
    private String email;
    private Long id;
    private String nickname;
    // TODO: adjust front interface
    private String gender;
}
