package com.gxf.thrift;

import org.apache.thrift.TException;

public class SayHiServiceImpl implements SayHiService.Iface {
    @Override
    public String sayHi(Request request) throws RequestException, TException {
        return "hi , " + request.getName();
    }
}
