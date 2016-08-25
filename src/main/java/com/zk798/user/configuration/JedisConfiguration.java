package com.zk798.user.configuration;

import com.zk798.user.vo.HostPort;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;

/**
 * <br/>
 * Date: 2016/7/14<br/>
 *
 * @author ZRS
 * @since JDK7
 */
@Configuration
@Slf4j
@Setter
@Getter
@ConfigurationProperties(prefix="redis.client.host")
public class JedisConfiguration {

    private HostPort[] list;

    @Bean
    @ConfigurationProperties("redis.client.pool")
    public GenericObjectPoolConfig getPoolConfig(){
        return new GenericObjectPoolConfig();
    }

    @Bean
    public JedisCluster getJedisClusterInstance() {
        HashSet<HostAndPort> jedisClusterNodes = new HashSet<>();
        for (HostPort h : getList()) {
            jedisClusterNodes.add(new HostAndPort(h.getHost(), h.getPort()));
            log.info("--------------------Loading {} : {} -----------------------",h.getHost(),h.getPort());
        }
        GenericObjectPoolConfig poolConfig = getPoolConfig();
        JedisCluster jc = new JedisCluster(jedisClusterNodes,poolConfig);
        log.info("--------------------Load {} Host  successful-----------------------",getList().length);
        return jc;
    }

}
