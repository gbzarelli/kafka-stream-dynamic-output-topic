import java.util.UUID;

public class Order {

  Long sequence;
  UUID orderUuid;

  public Order(final Long sequence, final UUID orderUuid) {
    this.sequence = sequence;
    this.orderUuid = orderUuid;
  }

  public Order() {
  }

  public Long getSequence() {
    return sequence;
  }

  public void setSequence(final Long sequence) {
    this.sequence = sequence;
  }

  public UUID getOrderUuid() {
    return orderUuid;
  }

  public void setOrderUuid(final UUID orderUuid) {
    this.orderUuid = orderUuid;
  }
}
