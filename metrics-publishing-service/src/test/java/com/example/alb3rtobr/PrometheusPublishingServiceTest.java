package com.example.alb3rtobr;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.apache.geode.metrics.MetricsPublishingService;
import org.apache.geode.metrics.MetricsSession;

@ExtendWith(MockitoExtension.class)
class PrometheusPublishingServiceTest {

    @Mock
    private MetricsSession metricsSession;
    private MetricsPublishingService subject;
    private final int port=33334;
    private final String ENDPOINT_URL ="http://localhost:"+port+PrometheusPublishingService.ENDPOINT;

    @BeforeEach
    void setUp() {
        subject = new PrometheusPublishingService(port);
    }

    @Test
    void start_addsRegistryToMetricsSession() {
        subject.start(metricsSession);

        verify(metricsSession).addSubregistry(any(PrometheusMeterRegistry.class));

        subject.stop();
    }

    @Test
    void start_addsAnHttpEndpointThatReturnsStatusOK() throws IOException {
        subject.start(metricsSession);

        HttpGet request = new HttpGet(ENDPOINT_URL);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));

        subject.stop();
    }

    @Test
    void start_addsAnHttpEndpointThatContainsRegistryData() throws IOException {
        subject.start(metricsSession);

        HttpGet request = new HttpGet(ENDPOINT_URL);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        String responseBody = EntityUtils.toString(response.getEntity());
        assertThat(responseBody, equalTo(""));

        subject.stop();
    }

    @Test
    void stop_removesRegistryFromMetricsSession() {
        subject.start(metricsSession);
        subject.stop();

        verify(metricsSession).removeSubregistry(any(PrometheusMeterRegistry.class));
    }

    @Test
    void stop_hasNoHttpEndpointRunning() {
        subject.start(metricsSession);
        subject.stop();

        HttpGet request = new HttpGet(ENDPOINT_URL);

        assertThrows(HttpHostConnectException.class, () -> {
            HttpClientBuilder.create().build().execute(request);
        });
    }
}
