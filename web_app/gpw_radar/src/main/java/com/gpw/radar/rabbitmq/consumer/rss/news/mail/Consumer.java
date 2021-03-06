package com.gpw.radar.rabbitmq.consumer.rss.news.mail;

import com.gpw.radar.config.Constants;
import com.gpw.radar.domain.rss.NewsMessage;
import com.gpw.radar.rabbitmq.MessageTransformer;
import com.gpw.radar.service.mail.MailService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service("rssMailConsumer")
@Profile("!" + Constants.SPRING_PROFILE_DEVELOPMENT)
public class Consumer {

    private final MailService mailService;
    private final String newsTypeHeader;
    private final MessageTransformer messageTransformer;


    @Autowired
    public Consumer(MailService mailService, @Value("${rss_reader_news_type_header}") String newsTypeHeader,
                    MessageTransformer messageTransformer) {
        this.mailService = mailService;
        this.newsTypeHeader = newsTypeHeader;
        this.messageTransformer = messageTransformer;
    }

    @RabbitListener(queues = "${rss_reader_mail_queue}")
    public void consumeMessage(Message message) throws InterruptedException, IOException {
        List<NewsMessage> newsMessages = messageTransformer.getNewsMessages(message, newsTypeHeader);
        newsMessages.forEach(e -> e.setMessage(messageTransformer.transformMessage(e.getLink(), e.getMessage())));
        newsMessages.stream().filter(e -> e.getStock() != null).forEach(e -> mailService.informUserAboutStockNewsByEmail(e));
    }

}
