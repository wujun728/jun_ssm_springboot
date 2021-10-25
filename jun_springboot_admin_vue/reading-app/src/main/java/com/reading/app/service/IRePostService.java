package com.reading.app.service;

import java.util.List;
import com.reading.app.domain.RePost;

/**
 * 帖子Service接口
 * 
 * @author cj
 * @date 2021-03-24
 */
public interface IRePostService 
{
    /**
     * 查询帖子
     * 
     * @param id 帖子ID
     * @return 帖子
     */
    public RePost selectRePostById(Long id);

    /**
     * 查询帖子列表
     * 
     * @param rePost 帖子
     * @return 帖子集合
     */
    public List<RePost> selectRePostList(RePost rePost);

    /**
     * 新增帖子
     * 
     * @param rePost 帖子
     * @return 结果
     */
    public int insertRePost(RePost rePost);

    /**
     * 修改帖子
     * 
     * @param rePost 帖子
     * @return 结果
     */
    public int updateRePost(RePost rePost);

    /**
     * 批量删除帖子
     * 
     * @param ids 需要删除的帖子ID
     * @return 结果
     */
    public int deleteRePostByIds(Long[] ids);

    /**
     * 删除帖子信息
     * 
     * @param id 帖子ID
     * @return 结果
     */
    public int deleteRePostById(Long id);
}