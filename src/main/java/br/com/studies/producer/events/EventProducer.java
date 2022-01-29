package br.com.studies.producer.events;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.StringSerializer;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.UUID;

@Slf4j
public class EventProducer {

    private final KafkaProducer<String, String> producer;

    public EventProducer() {
        this.producer = new KafkaProducer<>(properties());
    }

    public void send() {
        log.info("Iniciando envio de mensagem");

        var formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        var date = formatDate.format(LocalDateTime.now());
        var key = date + "__" + UUID.randomUUID();
        var message = "Hello -- " + date;

        ProducerRecord<String, String> record = new ProducerRecord<>("mytopic", key, message);
        producer.send(record);
        producer.flush();
        producer.close();

        log.info("Mensagem enviada [{}]", message);
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9093");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }
}
