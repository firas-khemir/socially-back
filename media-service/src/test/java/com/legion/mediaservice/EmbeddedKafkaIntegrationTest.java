//package com.legion.mediaservice;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.legion.mediaservice.broker.listeners.dto.TestEvent;
//import com.legion.mediaservice.services.listeners.EventServiceImpl;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.ClassRule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listeners.ContainerProperties;
//import org.springframework.kafka.listeners.KafkaMessageListenerContainer;
//import org.springframework.kafka.listeners.MessageListener;
//import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
//import org.springframework.kafka.test.utils.ContainerTestUtils;
//import org.springframework.kafka.test.utils.KafkaTestUtils;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.TimeUnit;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.kafka.test.assertj.KafkaConditions.key;
//
//@RunWith(SpringRunner.class)
//@DirtiesContext
//@SpringBootTest()
//public class  EmbeddedKafkaIntegrationTest {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedKafkaIntegrationTest.class);
//
//    private static String TOPIC_NAME = "testTopic2";
//
//    @Autowired
//    private EventServiceImpl kafkaMessageProducerService;
//
//    private KafkaMessageListenerContainer<String, TestEvent> container;
//
//    private BlockingQueue<ConsumerRecord<String, String>> consumerRecords;
//
//    @ClassRule
//    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, TOPIC_NAME);
//
//    @Before
//    public void setUp() {
//        consumerRecords = new LinkedBlockingQueue<>();
//
//        ContainerProperties containerProperties = new ContainerProperties(TOPIC_NAME);
//
//        Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps(
//            "participateTopic2", "false", embeddedKafka.getEmbeddedKafka());
//
//        DefaultKafkaConsumerFactory<String, TestEvent> consumer = new DefaultKafkaConsumerFactory<>(consumerProperties);
//
//        container = new KafkaMessageListenerContainer<>(consumer, containerProperties);
//        container.setupMessageListener((MessageListener<String, String>) record -> {
//            LOGGER.debug("Listened message='{}'", record.toString());
//            consumerRecords.add(record);
//        });
//        container.start();
//
//        ContainerTestUtils.waitForAssignment(container, embeddedKafka.getEmbeddedKafka().getPartitionsPerTopic());
//    }
//
//    @After
//    public void tearDown() {
//        container.stop();
//    }
//
//    @Test
//    public void it_should_send_updated_brand_event() throws InterruptedException, IOException {
//        TestEvent updatedBrandEvent = new TestEvent("BrandMapCreatedEvent");
//        kafkaMessageProducerService.testProducer(updatedBrandEvent);
//
//        ConsumerRecord<String, String> received = consumerRecords.poll(10, TimeUnit.SECONDS);
//
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString( updatedBrandEvent );
//
//        assertThat(json).isIn(received);
//
//        assertThat(received).has(key(null));
//    }
//
//}
