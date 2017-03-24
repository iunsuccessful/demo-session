package iunsuccessful.demo.shiro.session.dal.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Redis 管理 Session
 * Created by LiQZ on 2017/3/23.
 */
public class RedisSessionDaoImpl extends AbstractSessionDAO {

    public static final Logger logger = LoggerFactory.getLogger(RedisSessionDaoImpl.class);

    /**
     * Session 管理
     */

    Cache<Serializable, Session> sessionCache = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build();
    /**
     * 创建 Session
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        logger.info("do create session id is {}", sessionId);
        this.assignSessionId(session, sessionId);
        // memory 中这里是要保存的，但从日志来，它会自动调用 update
//        this.update(session);
        return sessionId;
    }

    /**
     * 14:29:07.892 [http-nio-8080-exec-5] DEBUG o.a.s.w.s.m.DefaultWebSessionManager - Referenced session was invalid.  Removing session ID cookie.
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        // 每次读取的时候，要延长一下有效期
        // 如果读不到是不是把 cookie 删除掉，要不然每次都报错
        Session session = sessionCache.getIfPresent(sessionId);
        logger.info("do read session id is {} result is {}", sessionId, session != null);
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if(session == null || session.getId() == null){
            return;
        }
        logger.info("do update session, id is {}", session.getId());
        sessionCache.put(session.getId(), session);
    }

    @Override
    public void delete(Session session) {
        if(session == null || session.getId() == null){
            return;
        }
        logger.info("do delete session, id is {}", session.getId());
        sessionCache.asMap().remove(session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        logger.info("get active sessions.");
        return sessionCache.asMap().values();
    }
}
