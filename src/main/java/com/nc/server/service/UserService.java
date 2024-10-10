package com.nc.server.service;

import com.nc.server.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class UserService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public int addUser(User userinfo){
        logger.debug("create user success, uid=" + userinfo.getUid());
        return 0;
    }
}
