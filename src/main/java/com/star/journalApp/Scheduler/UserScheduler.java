package com.star.journalApp.Scheduler;

import com.star.journalApp.cache.AppCache;
import com.star.journalApp.enms.Sentiment;
import com.star.journalApp.entity.JournalEntry;
import com.star.journalApp.entity.User;
import com.star.journalApp.model.SentimentData;
import com.star.journalApp.repository.UserRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private UserRepositoryImp userRepository;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "*/10 * * * * *")// kb bhejna hai in cron form
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepository.getUsersForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for(Sentiment sentiment :sentiments){
                if(sentiments !=null){
                    sentimentCounts.put(sentiment ,sentimentCounts.getOrDefault(sentiment,0)+1);
                }
            }
            Sentiment mostFrequnetlySewntiment =null;
            int maxCount =0;
            for(Map.Entry<Sentiment,Integer> entry : sentimentCounts.entrySet()){
                if(entry.getValue() >maxCount){
                    maxCount = entry.getValue();
                    mostFrequnetlySewntiment = entry.getKey();
                }
            }
            if(mostFrequnetlySewntiment != null){
               SentimentData sentimentData =  SentimentData.builder().email(user.getEmail()).sentiment( "Sentiment for last 7 days "+ mostFrequnetlySewntiment).build();
               kafkaTemplate.send("weekly_sentiments" ,sentimentData.getEmail() ,sentimentData);
            }
        }
    }
    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        appCache.init();
    }
}