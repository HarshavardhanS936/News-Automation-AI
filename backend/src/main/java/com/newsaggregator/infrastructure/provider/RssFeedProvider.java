package com.newsaggregator.infrastructure.provider;

import com.newsaggregator.application.service.NewsProvider;
import com.newsaggregator.domain.entity.NewsArticle;
import com.newsaggregator.domain.entity.Source;
import com.newsaggregator.infrastructure.repository.SourceRepository;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class RssFeedProvider implements NewsProvider {

    private final SourceRepository sourceRepository;
    private final String[] rssUrls = {
            "http://feeds.bbci.co.uk/news/rss.xml",
            "https://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml"
    };

    public RssFeedProvider(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    @Override
    public List<NewsArticle> fetchNews() {
        List<NewsArticle> articles = new ArrayList<>();
        
        for (String rssUrl : rssUrls) {
            try {
                URL feedUrl = new URL(rssUrl);
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(feedUrl));

                String sourceName = feed.getTitle() != null ? feed.getTitle() : "RSS Feed";
                Source apiSource = sourceRepository.findByName(sourceName)
                        .orElseGet(() -> sourceRepository.save(Source.builder().name(sourceName).url(feed.getLink()).build()));

                for (SyndEntry entry : feed.getEntries()) {
                    LocalDateTime publishedAt = entry.getPublishedDate() != null ?
                            entry.getPublishedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() :
                            LocalDateTime.now();
                            
                    NewsArticle article = NewsArticle.builder()
                            .title(entry.getTitle())
                            .url(entry.getLink())
                            .content(entry.getDescription() != null ? entry.getDescription().getValue() : entry.getTitle())
                            .publishedAt(publishedAt)
                            .source(apiSource)
                            .build();
                    articles.add(article);
                }
            } catch (Exception e) {
                System.err.println("Error fetching RSS feed " + rssUrl + ": " + e.getMessage());
            }
        }
        return articles;
    }

    @Override
    public String getProviderName() {
        return "RSSFeeds";
    }
}
