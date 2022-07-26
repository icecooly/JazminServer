package jazmin.core.proxy;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

/**
 * 代理工厂
 */
public class ProxyFactory{

    /**
     * 获取代理对象
     * @param clz
     * @param <T>
     * @return
     */
    public static  <T> T createProxyObject(Class<T> clz, Callback callback) {
        // 创建Enhancer对象，类似于JDK动态代理的Proxy类，下一步就是设置几个参数
        Enhancer enhancer = new Enhancer();
        // 设置父类的字节码对象
        enhancer.setSuperclass(clz);
        // 设置回调函数
        enhancer.setCallback(callback);
        // 创建代理对象
        return (T) enhancer.create();
    }
}
