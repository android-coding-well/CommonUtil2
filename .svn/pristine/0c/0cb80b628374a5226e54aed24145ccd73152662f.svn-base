package com.gosuncn.cu.net;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *  retrofit异步回调代理类
 */
public class RetrofitCallbackProxy implements InvocationHandler{

    CallbackExtend callback;
    public CallbackExtend newProxyInstance(CallbackExtend callback){
        this.callback=callback;
        callback.onBefore();
       return (CallbackExtend)Proxy.newProxyInstance(callback.getClass().getClassLoader(),callback.getClass().getInterfaces(),this);
    }
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        //调用委托类方法
        Object object=method.invoke(callback,objects);

        callback.onAfter();
        return object;
    }
}
