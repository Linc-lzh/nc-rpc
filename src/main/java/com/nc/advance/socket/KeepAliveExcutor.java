package com.nc.advance.socket;

import java.time.LocalDate;

public class KeepAliveExcutor extends AbstractSocketExecutor {
    public KeepAliveExcutor(CSocket socketMember) {
        super(socketMember);
    }

    public void request() {
        String request = LocalDate.now().toString();
        super.request(request, "utf-8");
    }
}
