# Kafka Stream - Event Router

Route events by order topic dynamically by app header in own specifically topic 

# Troubleshooting

- WARN  [org.apa.kaf.str.int.met.ClientMetrics] (Quarkus Main Thread) Error while loading kafka-streams-version.properties: java.lang.NullPointerException: inStream parameter is null
  - https://issues.apache.org/jira/browse/KAFKA-14270

- task [0_0] Unable to records bytes produced to topic <topic> by sink node KSTREAM-SINK-0000000001 as the node is not recognized.
  - https://issues.apache.org/jira/browse/KAFKA-14282
  - https://github.com/apache/kafka/pull/12836