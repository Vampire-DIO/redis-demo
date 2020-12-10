package com.redis;

import com.redis.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @ClassNameBitMapController
 * @Description TODO
 * @Author Snow_D
 * Date 2020/12/10 19:03
 * @Version 1.0
 */
@RestController("/bitmap")
public class BitMapController {
    private static final Logger logger = LoggerFactory.getLogger(BitMapController.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 打卡
     * @param id
     * @param day
     */
    @GetMapping("/sign/{id}")
    public void testBitMap(@PathVariable("id") String id, int day){
        stringRedisTemplate.opsForValue().setBit(id,day,true);
    }

    /**
     * 根据id统计此id的打卡总数
     * @param id
     * @return
     */
    @GetMapping("/count/sign/{id}")
    public long count(@PathVariable("id")String id){
        return redisUtil.bitCount(id);
    }

    /**
     * 文章点赞功能
     * @param articleId 文章id作为key
     * @param id    用户id作为index
     */
    @GetMapping("/articleLike/like")
    public void like(String articleId,String id){
        //将id作为index
        int index = Integer.parseInt(id);
        stringRedisTemplate.opsForValue().setBit(articleId,index,true);
    }

    /**
     * 统计文章总点赞数
     * @param articleId
     * @return
     */
    @GetMapping("/count/articleLike")
    public long articleLikeCount(String articleId){
        return redisUtil.bitCount(articleId);
    }

    /**
     * 返回用户对该文章的点赞状态
     * @param articledId
     * @param id
     * @return
     */
    @GetMapping("/article/isLiked")
    public boolean isArticleLiked(String articledId,String id){
        int index=Integer.valueOf(id);
        return stringRedisTemplate.opsForValue().getBit(articledId,index);
    }

    /**
     * 取消点赞
     * @param articleId
     * @param id
     */
    @GetMapping("/articleLike/dislike")
    public void dislike(String articleId,String id){
        //将id作为index
        int index = Integer.parseInt(id);
        stringRedisTemplate.opsForValue().setBit(articleId,index,false);
    }
}
