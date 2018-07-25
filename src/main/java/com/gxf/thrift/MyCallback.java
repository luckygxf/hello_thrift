package com.gxf.thrift;

import org.apache.thrift.async.AsyncMethodCallback;

public class MyCallback implements AsyncMethodCallback {
    public void onComplete(Object response) {
        System.out.println("onComplete");
        System.out.println(response);
    }

    public void onError(Exception exception) {
        System.out.println("onError");
    }
}
