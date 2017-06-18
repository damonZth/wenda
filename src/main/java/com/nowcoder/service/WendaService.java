package com.nowcoder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Damon on 2017/6/18.
 *
 */
@Service
public class WendaService {
    public String getMessage(int userId){
        return "hello Message:" + String.valueOf(userId);
    }
}
