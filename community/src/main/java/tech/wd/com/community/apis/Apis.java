package tech.wd.com.community.apis;

/**
 * Author: 范瑞旗
 * Date: 2019/3/1 15:38
 * Description: 社区接口类
 */
public class Apis {


    //社区列表
    public static final String URL_COMMUNITY_LIST="community/v1/findCommunityList";
    //发布帖子
    public static final String URL_RELEASE_POST="community/verify/v1/releasePost";
    //删除帖子
    public static final String URL_DELETE_POST="community/verify/v1/deletePost";
    //点赞
    public static final String URL_ADD_GREAT="community/verify/v1/addCommunityGreat";
    //取消点赞
    public static final String URL_CANCEL_GREAT="community/verify/v1/cancelCommunityGreat";
    //社区用户评论列表（bean方式返参）
    public static final String URL_USER_COMMENT="community/v1/findCommunityUserCommentList";
    //社区评论
    public static final String URL_ADD_COMMUNITY_COMMENT="community/verify/v1/addCommunityComment";
    //我的帖子
    public static final String URL_MY_POST="community/verify/v1/findMyPostById?page=1&count=5";
    //查询用户发布的帖子
    public static final String URL_FIND_USER_POST="community/verify/v1/findUserPostById?fromUid=%s&page=1&count=5";

}
