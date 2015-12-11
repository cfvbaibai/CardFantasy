package cfvbaibai.cardfantasy.jettyserver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class CardFantasyJettyServer {

    public static void main(String[] args) throws Exception {
        int port = 7878;
        if (args.length > 1) {
            if ("-port".equalsIgnoreCase(args[0])) {
                try {
                    port = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid port number " + args[1]);
                }
            }
        }
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMaxThreads(128);
        threadPool.setMinThreads(32);
        Server server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);

        HandlerList handlers = new HandlerList();

        handlers.addHandler(new AbstractHandler() {
            @Override
            public void handle(String s, Request jettyRequest,
                    HttpServletRequest servletRequest, HttpServletResponse servletResponse)
                    throws IOException, ServletException {
                servletResponse.setCharacterEncoding("UTF-8");
                jettyRequest.setHandled(false); 
            }
        });

        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(BossGameJettyServlet.class, "/PlayBossMassiveGame");
        servletHandler.addServletWithMapping(ArenaGameJettyServlet.class, "/PlayAutoMassiveGame");
        servletHandler.addServletWithMapping(MapGameJettyServlet.class, "/PlayMapMassiveGame");
        servletHandler.addServletWithMapping(LilithGameJettyServlet.class, "/PlayLilithMassiveGame");
        servletHandler.addServletWithMapping(PingJettyServlet.class, "/Ping");
        handlers.addHandler(servletHandler);

        server.setHandler(handlers);
        server.start();
        server.join();
    }
}
