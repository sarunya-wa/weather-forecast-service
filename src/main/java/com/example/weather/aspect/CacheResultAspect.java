package com.example.weather.aspect;

import com.example.weather.common.Constant;
import com.example.weather.model.CacheResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheResultAspect {
    @Autowired
    private CacheManager cacheManager;

    @Around("execution(* *(..)) && @annotation(CacheCounter)")
    public Object checkCacheResult(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        Cache cache = cacheManager.getCache(Constant.CACHE_NAME_FORECAST_INFO);
        Object cachedValue = cache.get(generateKey(args), Object.class);

        Object result = joinPoint.proceed();

        if (cachedValue != null && result.equals(cachedValue)) {
            if (result instanceof CacheResult) {
                CacheResult cachedResult = (CacheResult) result;
                return new CacheResult<>(cachedResult.getData(), true);
            }
        }
        return result;
    }

    private Object generateKey(Object[] args) {
        return args[0];
    }
}
