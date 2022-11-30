package io.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.blog.ResponseResult;
import io.blog.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-11-30 10:02:53
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);
}

