package com.gxf.thrift;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class HelloWordServiceImpl implements HelloWordService.Iface {

    // 实现这个方法完成具体的逻辑。
    public String doAction(Request request)
            throws RequestException, org.apache.thrift.TException {
        System.out.println("Get request: " + request);
        if (StringUtils.isBlank(request.getName()) || request.getType() == null) {
            throw new com.gxf.thrift.RequestException();
        }
        String result = "Hello, " + request.getName();
        if (request.getType() == com.gxf.thrift.RequestType.SAY_HELLO) {
            result += ", Welcome!";
        } else {
            result += ", Now is " + new Date().toLocaleString();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;

    }
}
