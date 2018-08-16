package top.laumgjyu.core;

import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.json.JFinalJsonFactory;
import com.jfinal.template.Engine;
import top.laumgjyu.core.interceptors.ParamValidateInterceptor;

/**

 * API引导式配置
 */
public class Config extends JFinalConfig {
	
	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 * 
	 * 使用本方法启动过第一次以后，会在开发工具的 debug、run config 中自动生成
	 * 一条启动配置，可对该自动生成的配置再添加额外的配置项，例如 VM argument 可配置为：
	 * -XX:PermSize=64M -XX:MaxPermSize=256M
	 */
	public static void main(String[] args) {
		/**
		 * 特别注意：IDEA 之下建议的启动方式，仅比 eclipse 之下少了最后一个参数
		 */
		 JFinal.start("src/main/webapp", 80, "/");
	}
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		me.setDevMode(true);
        me.setJsonFactory(new JFinalJsonFactory());
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
    }
	
	public void configEngine(Engine me) {
	    me.setDevMode(true);
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.addGlobalActionInterceptor(new ParamValidateInterceptor());  // 使用hibernate-validator参数校验
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		
	}
}
