package com.example.db_proj2_test1.controller;

import cn.yueshutong.springbootstartercurrentlimiting.annotation.CurrentLimiter;
import cn.yueshutong.springbootstartercurrentlimiting.handler.CurrentAspectHandler;

import com.example.db_proj2_test1.entity.ResponseModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Component
public class MyCurrentLimitHandler implements CurrentAspectHandler {
    @Override
    public ResponseModel around(ProceedingJoinPoint pjp, CurrentLimiter rateLimiter)  {
        return new ResponseModel(500,"system busy");
    }
}
