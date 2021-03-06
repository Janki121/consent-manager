package in.projecteka.consentmanager.common.heartbeat;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import in.projecteka.consentmanager.DbOptions;
import in.projecteka.consentmanager.clients.model.Error;
import in.projecteka.consentmanager.clients.model.ErrorCode;
import in.projecteka.consentmanager.clients.properties.IdentityServiceProperties;
import in.projecteka.consentmanager.common.cache.RedisOptions;
import in.projecteka.consentmanager.common.heartbeat.model.HeartbeatResponse;
import in.projecteka.consentmanager.common.heartbeat.model.Status;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.SocketAddress;
import java.net.Socket;
import java.net.InetSocketAddress;

import java.time.Instant;
import java.util.concurrent.TimeoutException;

@AllArgsConstructor
public class Heartbeat {
    private final IdentityServiceProperties identityServiceProperties;
    private final DbOptions dbOptions;
    private final RabbitmqOptions rabbitmqOptions;
    private final RedisOptions redisOptions;

    public Mono<HeartbeatResponse> getStatus() {
        try {
            return isPostgresUp() && isKeycloakUp() && isRabbitMQUp() && isRedisUp()
                    ? Mono.just(HeartbeatResponse.builder()
                    .timeStamp(Instant.now().toString())
                    .status(Status.UP)
                    .build())
                    : Mono.just(HeartbeatResponse.builder()
                    .timeStamp(Instant.now().toString())
                    .status(Status.DOWN)
                    .error(Error.builder().code(ErrorCode.SERVICE_DOWN).message("Service down").build())
                    .build());
        } catch (IOException | TimeoutException e) {
            return Mono.just(HeartbeatResponse.builder()
                    .timeStamp(Instant.now().toString())
                    .status(Status.DOWN)
                    .error(Error.builder().code(ErrorCode.SERVICE_DOWN).message("Service down").build())
                    .build());
        }
    }

    private boolean isRedisUp() throws IOException {
        return checkConnection(redisOptions.getHost(), redisOptions.getPort());
    }

    private boolean isRabbitMQUp() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitmqOptions.getHost());
        factory.setPort(rabbitmqOptions.getPort());
        Connection connection = factory.newConnection();
        return connection.isOpen();
    }

    private boolean isKeycloakUp() throws IOException {
        URL siteUrl = new URL(identityServiceProperties.getBaseUrl());
        HttpURLConnection httpURLConnection = (HttpURLConnection) siteUrl.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        int responseCode = httpURLConnection.getResponseCode();
        return responseCode == 200;
    }

    private boolean isPostgresUp() throws IOException {
        return checkConnection(dbOptions.getHost(), dbOptions.getPort());
    }

    private boolean checkConnection(String host, int port) throws IOException {
        boolean isAlive;
        SocketAddress socketAddress = new InetSocketAddress(host, port);
        Socket socket = new Socket();
        socket.connect(socketAddress);
        isAlive = socket.isConnected();
        socket.close();
        return isAlive;
    }
}
