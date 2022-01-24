package br.com.studies.producer;

import br.com.studies.producer.events.EventProducer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainApplication {
    public static void main(String[] args) {
        log.info("--- iniciando aplicação ---");
        EventProducer eventProducer = new EventProducer();
        eventProducer.send();
    }
}
