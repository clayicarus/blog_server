package io.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * 文章表(Article)表实体类
 *
 * @author makejava
 * @since 2022-11-20 21:47:45
 */
@SuppressWarnings("serial")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    
    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //文章摘要
    @TableField("abstract")
    private String articleAbstract;
    //所属分类id
    private Long categoryId;
    //缩略图
    private String thumbnail;
    //是否置顶（0否，1是）
    private Integer isTop;
    //状态（0已发布，1草稿）
    private Integer status;
    //访问量
    private Long visits;
    //是否允许评论 1是，0否
    private Integer allowComment;
    
    private Long createBy;
    
    private Date createTime;
    
    private Long updateBy;
    
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer deleted;
}

