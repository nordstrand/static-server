package org.test;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;

import ch.qos.logback.access.jetty.RequestLogImpl;

public class Server {
    
    public static void main(String[] args) throws Exception {
        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server();
        ServerConnector connector = new ServerConnector(server);

        connector.setPort(8080);
        server.addConnector(connector);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase(".");
        
        HandlerCollection handlers = new HandlerCollection();
        
        ContextHandler contextHandler = new ContextHandler("/");
        contextHandler.setHandler(resourceHandler);
        //andlerList handlers = new HandlerList();
        //handlers.setHandlers(new Handler[] { resource_handler, new DefaultHandler() });
        RequestLogHandler requestLogHandler = new RequestLogHandler();
        requestLogHandler.setRequestLog(logbackRequestLog());
        handlers.setHandlers(new Handler[]{contextHandler, new DefaultHandler(), requestLogHandler});
        server.setHandler(handlers);

        server.start();
        System.out.println(server.dump());
        server.join();
    }

    private static RequestLog logbackRequestLog() {
        RequestLogImpl requestLogImpl = new RequestLogImpl();
        requestLogImpl.setResource("/logback-access.xml");
        
        return requestLogImpl;
    }
    
    private static void doh() {
        
//        ContextHandler context0 = new ContextHandler();
//        context0.setContextPath("/");
//        ResourceHandler rh0 = new ResourceHandler();
//        rh0.setBaseResource(Resource.newResource(MavenTestingUtils.getTestResourceDir("dir0")));
//        context0.setHandler(rh0);
//        
//        ContextHandler context1 = new ContextHandler();
//        context1.setContextPath("/");
//        ResourceHandler rh1 = new ResourceHandler();
//        rh1.setBaseResource(Resource.newResource(MavenTestingUtils.getTestResourceDir("dir1")));
//        context1.setHandler(rh1);
//        
//        ContextHandlerCollection contexts = new ContextHandlerCollection();
//        contexts.setHandlers(new Handler[] { context0, context1 });
//        server.setHandler(contexts);
//        server.start();
//        System.err.println(server.dump());
//        server.join();
    }

}
