import java.nio.charset.StandardCharsets;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.processor.TopicNameExtractor;

@ApplicationScoped
public class OrderEventRouterByHeader {

  @Produces
  public Topology buildTopology() {
    final var builder = new StreamsBuilder();

    final TopicNameExtractor<byte[], byte[]> topicNameExtractor = (key, value, recordContext) -> {
      for (Header header : recordContext.headers()) {
        if (Objects.equals(header.key(), "app")) {
          return "order-" + new String(header.value(), StandardCharsets.UTF_8);
        }
      }
      return "order-unknown-app";
    };

    builder.stream("order", Consumed.with(Serdes.ByteArray(), Serdes.ByteArray()))
        .to(topicNameExtractor);

    return builder.build();
  }
}
