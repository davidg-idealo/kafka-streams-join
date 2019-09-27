import java.util.Properties;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

public class StreamJoiner {
    private static final String customerTopic = "customer-v1";
    private static final String paymentTopic = "payment-v1";
    private static final String outputTopic = "output-topic";

    public static void main(String[] args) {
        //
        // Step 1: Configure and start the processor topology.
        //
        final Serde<String> stringSerde = Serdes.String();
        final Serde<Long> longSerde = Serdes.Long();

        final Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "stream-joiner");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        final StreamsBuilder builder = new StreamsBuilder();

        final KTable<String, String> customerTable = builder.table(customerTopic, Consumed.with(stringSerde, stringSerde));
        final KTable<String, String> paymentTable = builder.table(paymentTopic, Consumed.with(stringSerde, stringSerde));
        final KTable<String, String> customerPaymentTable = customerTable.join(paymentTable,
                (customerValue, paymentValue) -> customerValue + "-" + paymentValue);
        customerPaymentTable.toStream().to(outputTopic, Produced.with(stringSerde, stringSerde));

        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), streamsConfiguration);
        kafkaStreams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    }


}
