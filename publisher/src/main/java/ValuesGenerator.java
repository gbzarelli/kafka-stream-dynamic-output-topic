import io.smallrye.mutiny.Multi;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Random;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ValuesGenerator {
  private static final Logger LOG = Logger.getLogger(ValuesGenerator.class);
  private static final Random RANDOM = new Random();

  private enum Application {
    SAFIRA, AMETISTA, RUBI;

    public static Application getRandom() {
      return values()[RANDOM.nextInt(values().length)];
    }
  }

  @Outgoing("order")
  public Multi<Message<Order>> generate() {
    return Multi.createFrom().ticks().every(Duration.ofMillis(500)).onOverflow().drop().map(tick -> {
      final var order = new Order(tick, UUID.randomUUID());
      final var app = Application.getRandom().name().toLowerCase();
      LOG.infov("sequence: {0}, app: {1}, order: {2}", tick, app, order.orderUuid);
      return Message.of(order, getMetadata(String.valueOf(tick), app));
    });
  }

  private Metadata getMetadata(String key, String headerApp) {
    return Metadata.of(OutgoingKafkaRecordMetadata.<String>builder().withKey(key)
        .withHeaders(new RecordHeaders().add("app", headerApp.getBytes(StandardCharsets.UTF_8))).build());
  }

}