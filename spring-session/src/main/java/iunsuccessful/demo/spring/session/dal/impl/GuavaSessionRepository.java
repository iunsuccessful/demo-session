package iunsuccessful.demo.spring.session.dal.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import iunsuccessful.demo.spring.session.model.GuavaSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Guava Cache Session 管理类
 * Created by LiQZ on 2017/3/23.
 */
@Service
public class GuavaSessionRepository implements SessionRepository<ExpiringSession> {

    public static final Logger logger = LoggerFactory.getLogger(GuavaSessionRepository.class);

    /**
     * 这个问题多多，比如会重复问题
     */
    private AtomicInteger incrementId = new AtomicInteger();

    /**
     * Session 管理
     */

    Cache<Serializable, ExpiringSession> sessionCache = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .build();

    @Override
    public ExpiringSession createSession() {
        String id = String.valueOf(incrementId.incrementAndGet());
        GuavaSession session = new GuavaSession(id);
        logger.info("create session id is {}", id);
        return session;
    }

    @Override
    public void save(ExpiringSession session) {
        logger.info("save session id is {}", session.getId());
        sessionCache.put(session.getId(), session);
    }

    @Override
    public ExpiringSession getSession(String id) {
        logger.info("get session id is {}", id);
        return sessionCache.getIfPresent(id);
    }

    @Override
    public void delete(String id) {
        logger.info("delete session id is {}", id);
        sessionCache.asMap().remove(id);
    }
}
