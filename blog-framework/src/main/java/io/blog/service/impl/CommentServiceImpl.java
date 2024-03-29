package io.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.blog.ResponseResult;
import io.blog.entity.Comment;
import io.blog.entity.User;
import io.blog.enums.AppHttpCodeEnum;
import io.blog.exception.BlogException;
import io.blog.mapper.CommentMapper;
import io.blog.mapper.UserMapper;
import io.blog.service.CommentService;
import io.blog.utils.BeanCopyUtils;
import io.blog.utils.SecurityUtils;
import io.blog.vo.CommentVo;
import io.blog.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-11-30 10:04:14
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        // query root comment
        // root_id == -1 && articleId == articleId
        // page
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getArticleId, articleId);
        wrapper.eq(Comment::getRootId, -1); // TODO: constant class
        wrapper.orderByAsc(Comment::getCreateTime);
        Page<Comment> page = new Page<> (pageNum, pageSize);
        page(page, wrapper);
        List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(page.getRecords(), CommentVo.class); // TODO: use lambda to for_each
        // get username by id in table sys_user
        for (CommentVo commentVo : commentVoList) {
            Long createBy = commentVo.getCreateBy();
            commentVo.setUsername(getNameById(createBy));
            commentVo.setChildren(getChildCommentVoList(commentVo.getId()));
        }

        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        if(comment.getContent() == null) {
            throw(new BlogException(AppHttpCodeEnum.CONTENT_IS_NULL));
        }
        save(comment);      // mybatis plus method
        return ResponseResult.okResult();
    }

    private List<CommentVo> getChildCommentVoList(Long id)
    {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getRootId, id);
        wrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(wrapper);
        List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(comments, CommentVo.class);
        for(CommentVo i : commentVoList) {
            Long createBy = i.getCreateBy();
            Long userId = i.getToCommentUserId();
            i.setUsername(getNameById(createBy));
            i.setToCommentUserName(getNameById(userId));
        }
        return commentVoList;
    }
    private String getNameById(Long id)
    {
        return userMapper.selectById(id).getNickname();
    }
    @Autowired
    UserMapper userMapper;
}

