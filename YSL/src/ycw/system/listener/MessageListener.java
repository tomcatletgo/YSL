package ycw.system.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.comet4j.core.CometContext;

public class MessageListener implements ServletContextListener {

	@Override
    public void contextDestroyed(ServletContextEvent arg0) {

    }

	@Override
    public void contextInitialized(ServletContextEvent arg0) {
		CometContext context = CometContext.getInstance();
		context.registChannel("stChanne");
		
/*		//添加监听器 
		CometEngine engine = context.getEngine();  
		engine.addConnectListener(new StConnect());*/
    }

}
