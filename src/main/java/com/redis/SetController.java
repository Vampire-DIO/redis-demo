package com.redis;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassNameSetController
 * @Description 利用set结构完成一些功能：抽奖、朋友圈点赞、微博关注、商品筛选
 * @Author Snow_D
 * Date 2020/12/10 19:42
 * @Version 1.0
 */
@RestController("/setController")
public class SetController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 参与抽奖
     * @param luckId 奖池id
     * @param userId 用户
     * @return 提示信息
     */
    @GetMapping("/luckDraw/join")
    private String join(String luckId,String userId){
        redisTemplate.opsForSet().add(luckId,userId);
        return userId+" 成功参与："+luckId+" 号奖池的抽奖";
    }

    /**
     * 确定中奖人数count，开奖并返回中奖者，会将中奖者从集合中删除
     * @param luckId 奖池id
     * @param count 中奖数量
     * @return 中奖者
     */
    @GetMapping("/luckDraw/start")
    private List<String> start(String luckId,int count){
        List<String> pop = redisTemplate.opsForSet().pop(luckId, count);
        return pop;
    }

    /**
     * 点赞
     * @param postId
     * @param userId
     */
    @GetMapping("/community/like")
    private void communityLike(String postId,String userId){
        redisTemplate.opsForSet().add(postId,userId);
    }

    /**
     * 取消点赞
     * @param postId
     * @param userId
     */
    @GetMapping("/community/cancelLike")
    private void communityCancelLike(String postId,String userId){
        redisTemplate.opsForSet().remove(postId,userId);
    }

    /**
     * 判断是否点赞
     * @param postId
     * @param userId
     * @return
     */
    @GetMapping("community/isLiked")
    private boolean isLiked(String postId,String userId){
        return redisTemplate.opsForSet().isMember(postId,userId);
    }

    /**
     * 统计点赞数
     * @param postId
     * @return
     */
    @GetMapping("community/likedCount")
    private long likedCount(String postId){
        return redisTemplate.opsForSet().size(postId);
    }

    /**
     * 获取朋友圈已点赞的列表
     * @param postId
     * @param userId
     * @return
     */
    @GetMapping("community/likedList")
    private Set<String> likedList(String postId,String postUserId,String userId){
        String postFriendsKey=postUserId+":friendsList";
        String userFriendsKey=userId+":friendsList";
        //
        Long aLong = redisTemplate.opsForSet().intersectAndStore(postFriendsKey, userFriendsKey,postUserId + userId + ":commonFollows");
        System.out.println(aLong);
        return redisTemplate.opsForSet().intersect(postUserId+userId+":commonFollows", postId);
    }

    /**
     * 加好友
     * @param postId
     * @param userId
     */
    @GetMapping("friends/follow")
    private void friendsFollow(String postId,String userId){
        String postFriendsKey=postId+":friendsList";
        String userFriendsKey=userId+":friendsList";
        redisTemplate.opsForSet().add(postFriendsKey,userId);
        redisTemplate.opsForSet().add(userFriendsKey,postId);
    }

    /**
     * 获取好友列表
     * @param userId
     * @return
     */
    @GetMapping("friends/followList")
    private Set<String> friendsList(String userId){
        String userFriendsKey=userId+":friendsList";
        return redisTemplate.opsForSet().members(userFriendsKey);
    }


}
