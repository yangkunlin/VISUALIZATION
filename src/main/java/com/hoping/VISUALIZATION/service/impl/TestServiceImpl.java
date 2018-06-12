package com.hoping.VISUALIZATION.service.impl;

import com.hoping.VISUALIZATION.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @author YKL on 2018/5/9.
 * @version 1.0
 * spark:梦想开始的地方
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    public String test() {
        return "test";
    }
}
