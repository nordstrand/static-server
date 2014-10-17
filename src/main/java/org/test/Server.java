package org.test;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;

import ch.qos.logback.access.jetty.RequestLogImpl;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port;
    private List<String> pathList = new ArrayList<String>();
    private List<String> urlList = new ArrayList<String>();


    public static Server server() {
        return new Server();
    }

    public MappingBuilder mapping(String path) {
        Server.this.pathList.add(path);
        return new MappingBuilder();
    }


    public Server onPort(int port) throws Exception {
        this.port = port;

        return this;
    }


    public void listen() throws Exception {
        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(this.port);
        server.addConnector(connector);

        List<Handler> handlerList = createResourceHandlers(this.pathList, this.urlList);
        handlerList.add(new DefaultHandler());
        handlerList.add(createRequestLogHandler());

        server.setHandler(asHandlerCollection(handlerList));


        server.start();
        System.out.println(server.dump());
        server.join();
    }

    private HandlerCollection asHandlerCollection(List<Handler> handlerList) {
        HandlerCollection handlers = new HandlerCollection();
        Handler[] hArray = new Handler[handlerList.size()];
        handlers.setHandlers(handlerList.toArray(hArray));
        return handlers;
    }

    private RequestLogHandler createRequestLogHandler() {
        RequestLogHandler requestLogHandler = new RequestLogHandler();
        requestLogHandler.setRequestLog(logbackRequestLog());
        return requestLogHandler;
    }

    private List<Handler> createResourceHandlers(List<String> paths, List<String> urls) {
        List<Handler> handlerList = new ArrayList<Handler>();
        for (int i = 0; i < pathList.size(); i++){
            handlerList.add(createContextHandler(paths.get(i), urls.get(i)));
        }
        return handlerList;
    }

    private ContextHandler createContextHandler(String path, String url) {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setResourceBase(path);


        ContextHandler contextHandler = new ContextHandler(url);
        contextHandler.setHandler(resourceHandler);
        return contextHandler;
    }


    class MappingBuilder {
        public Server to(String url) {
            Server.this.urlList.add(url);
            return Server.this;
        }
    }

    
    public static void main(String[] args) throws Exception {
        server()
                .mapping("/tmp")
                .to("/personal")
                .mapping("/tmp/a")
                .to("/personal/resources")
                .onPort(8080)
                .listen();
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
