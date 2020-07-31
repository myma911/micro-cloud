package cn.aaron911.micro.gathering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 活动微服务
 * Spring Cache
 * Spring Cache使用方法与Spring对事务管理的配置相似。Spring Cache的核心就是对某
 * 个方法进行缓存，其实质就是缓存该方法的返回结果，并把方法参数和结果用键值对的
 * 方式存放到缓存中，当再次调用该方法使用相应的参数时，就会直接从缓存里面取出指
 * 定的结果进行返回。
 * 常用注解：
 * @Cacheable-------使用这个注解的方法在执行后会缓存其返回结果。
 * @CacheEvict--------使用这个注解的方法在其执行前或执行后移除Spring Cache中的某些元素。
 * 
 * Spring Cache 与 Redis 的区别
 * 
 * 1. 缓存级别不同
 * 
 *  Spring cache是代码级的缓存，他一般是使用一个ConcurrentMap。也就是说实际上还是是使用JVM的内存来缓存对象的，
 *  那么肯定会造成大量的内存消耗。但是使用方便。
 *  Redis 作为一个缓存服务器，是内存级的缓存。它是使用单纯的内存来进行缓存。
 *  
 * 2. sprirng cache+redis的好处
 * 
 * 那么Spring cache +redis的好处显而易见了。既可以很方便的缓存对象，同时用来缓存的内存的是使用redis的内存，不会消耗JVM的内存，提升了性能。当然这里Redis不是必须的，换成其他的缓存服务器一样可以，只要实现Spring的Cache类，并配置到XML里面就行了。
 * 
 * 3.集群环境下的springcache+redis
 * 
 * 集群环境下，每台服务器的spring cache是不同步的，这样会出问题的，spring cache只适合单机环境
 * redis是设置单独的缓存服务器，所有集群服务器统一访问redis，不会出现缓存不同步的情况
 * spring cache是很早就有的东西，现在+redis是为了顺应时代，更好的兼容集群环境，加强保留spring cache功能，不如直接使用redis
 * 
 * 4. spring cache基本原理
 * 
 * 和 spring 的事务管理类似，spring cache 的关键原理就是 spring AOP，通过 spring AOP，其实现了在方法调用前、调用后获取方法的入参和返回值，进而实现了缓存的逻辑。
 * 
 * 
 */
@SpringBootApplication
@EnableCaching
@EnableEurekaClient
public class GatheringApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatheringApplication.class, args);
    }
}
