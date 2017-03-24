package iunsuccessful.demo.spring.session.model;

import org.springframework.session.ExpiringSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Guava Session 管理
 * 因为我们通过 guava 管理 Session 生命周期，就不用去实现 ExpiringSession
 * 这里必须要实现 ExpiringSession, 要不然无法注入到 SpringHttpSessionConfiguration.springSessionRepositoryFilter 里面
 * Created by LiQZ on 2017/3/23.
 */
public class GuavaSession implements ExpiringSession {

    public GuavaSession(String id) {
        this.id = id;
    }

    private String id;

    private Map<String, Object> sessionAttrs = new HashMap<String, Object>();

    @Override
    public String getId() {
        return id;
    }

    @Override
    public <T> T getAttribute(String attributeName) {
        return (T) this.sessionAttrs.get(attributeName);
    }

    @Override
    public Set<String> getAttributeNames() {
        return this.sessionAttrs.keySet();
    }

    @Override
    public void setAttribute(String attributeName, Object attributeValue) {
        if (attributeValue == null) {
            removeAttribute(attributeName);
        }
        else {
            this.sessionAttrs.put(attributeName, attributeValue);
        }
    }

    @Override
    public void removeAttribute(String attributeName) {
        this.sessionAttrs.remove(attributeName);
    }

    @Override
    public long getCreationTime() {
        return 0;
    }

    @Override
    public void setLastAccessedTime(long lastAccessedTime) {

    }

    @Override
    public long getLastAccessedTime() {
        return 0;
    }

    @Override
    public void setMaxInactiveIntervalInSeconds(int interval) {

    }

    @Override
    public int getMaxInactiveIntervalInSeconds() {
        return 0;
    }

    @Override
    public boolean isExpired() {
        return false;
    }
}
