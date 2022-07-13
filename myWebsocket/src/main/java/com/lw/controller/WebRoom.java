package com.lw.controller;


import cn.hutool.Hutool;
import cn.hutool.json.JSONUtil;
import com.lw.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
@Slf4j
@ServerEndpoint(value = "/web/{userId}")
public class WebRoom {

    private static final Map<String, Session> clients = new ConcurrentHashMap<>();
    private static final Map<String, String> usMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId")String userId){
        //1. 查询未读消息

        log.info("用户 {} 上线: ", userId);
        clients.put(userId, session);
        usMap.put(session.getId(),userId);
        session.getAsyncRemote().sendText("用户: "+userId+" 已连接");
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId")String userId){
        log.info("用户 {} 下线", userId);
        clients.remove(userId);
        usMap.remove(session.getId());
    }

    @OnMessage
    public void onMessage(Session session,String message){
        Message messageBody = JSONUtil.toBean(message,Message.class);
        Session session1 = clients.get(messageBody.getDestId());
        if (session1 != null){
            send(messageBody, session1);
        }
        //save to db
    }

    private void send(Message message, Session session1) {
        session1.getAsyncRemote().sendText(JSONUtil.toJsonStr(message));
    }

}
