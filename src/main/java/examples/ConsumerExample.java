package examples;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerExample {

  public static void main(final String[] args) throws Exception {
    if (args.length != 1) {
      System.out.println("Please provide the configuration file path as a command line argument");
      System.exit(1);
    }

    final String topic = "purchases";

    // Load consumer configuration settings from a local file
    final Properties props = ProducerExample.loadConfig(args[0]);

    // Add additional properties.
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-java-getting-started");
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
    //props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

    // Dans Kafka
    // topic __consumer_offsets (group.id kafka-java-getting-started, purchases, partition 0, 50)

    // auto.offset.reset.config
    // S'il y a au moins 1 message dans __consumer_offsets, on ignore le paramètre
    // Si il n'y a pas de message (= première connexion), dans ce cas :
    // si on est à latest, le consumer est positionné pour récupérer uniquement les messages qui arriveront APRES sa
    // connexion
    // si on est à earliest, le consumer est positionné pour récupérer TOUS les messages du topic
    // si on est à none, erreur

    //props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
    //props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    // Add additional required properties for this consumer app
    final Consumer<String, String> consumer = new KafkaConsumer<>(props);
    consumer.subscribe(Arrays.asList(topic));

    try {
      while (true) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
        for (ConsumerRecord<String, String> record : records) {
          String key = record.key();
          String value = record.value();
          System.out.println(
                String.format("Consumed event from topic %s: key = %-10s value = %s", topic, key, value));
        }
        consumer.commitSync();
      }
    } finally {
      consumer.close();
    }
  }
}
