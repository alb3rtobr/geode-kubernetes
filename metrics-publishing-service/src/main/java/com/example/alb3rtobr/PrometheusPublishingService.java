package com.example.alb3rtobr;

import io.micrometer.core.instrument.Counter;
import org.apache.geode.annotations.VisibleForTesting;
import org.apache.geode.metrics.MetricsPublishingService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import static java.lang.Integer.getInteger;
import static org.slf4j.LoggerFactory.getLogger;
import io.micrometer.prometheus.PrometheusMeterRegistry;

import org.apache.geode.metrics.MetricsSession;
import org.slf4j.Logger;
import static io.micrometer.prometheus.PrometheusConfig.DEFAULT;

public class PrometheusPublishingService implements MetricsPublishingService {
    private static final String PORT_PROPERTY = "prometheus.metrics.port";
    private static final int DEFAULT_PORT = 0;
    private static final String HOSTNAME = "localhost";
    protected static final String ENDPOINT = "/";
    private static final int PORT = getInteger(PORT_PROPERTY, DEFAULT_PORT);

    private static Logger LOG = getLogger(PrometheusPublishingService.class);

    private final int port;
    private MetricsSession session;
    private PrometheusMeterRegistry registry;
    private HttpServer server;

    public PrometheusPublishingService() {
        this(PORT);
    }

    public PrometheusPublishingService(int port) {
        this.port = port;
    }

    @Override
    public void start(MetricsSession session) {
        this.session = session;
        registry = new PrometheusMeterRegistry(DEFAULT);
        this.session.addSubregistry(registry);

        InetSocketAddress address = new InetSocketAddress(HOSTNAME, port);
        server = null;
        try {
            server = HttpServer.create(address, 0);
        } catch (IOException thrown) {
            LOG.error("Exception while starting " + getClass().getSimpleName(), thrown);
        }
        HttpContext context = server.createContext(ENDPOINT);
        context.setHandler(this::requestHandler);
        server.start();

        int boundPort = server.getAddress().getPort();
        LOG.info("Started {} http://{}:{}{}", getClass().getSimpleName(), HOSTNAME, boundPort,ENDPOINT);

    }

    private void requestHandler(HttpExchange httpExchange) throws IOException {
        final byte[] scrapeBytes = registry.scrape().getBytes();
        httpExchange.sendResponseHeaders(200, scrapeBytes.length);
        final OutputStream responseBody = httpExchange.getResponseBody();
        responseBody.write(scrapeBytes);
        responseBody.close();
    }

    @Override
    public void stop() {
        session.removeSubregistry(registry);
        registry = null;
        server.stop(0);
    }
}
