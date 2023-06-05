package io.blog.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户表(User)表实体类
 *
 * @author makejava
 * @since 2022-11-26 11:37:05
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User  {
    //主键@TableId
    private Long id;

    //用户名
    @TableField("username")
    private String username;
    //昵称    
    private String nickname;
    //密码    
    private String password;
    //用户类型：0代表普通用户，1代表管理员    
    private Integer type;
    //账号状态（0正常 1停用）    
    private Integer status;
    //邮箱    
    private String email;
    //手机号    
    private String phone;
    //用户性别（0 unknown, 1男，2女）
    private Integer gender;
    //头像    
    private String avatar;
    //创建人的用户id    
    private Long createBy;
    //创建时间    
    private Date createTime;
    //更新人    
    private Long updateBy;
    //更新时间    
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）    
    private Integer deleted;
}

