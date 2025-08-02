package traffic.board.article.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import traffic.board.article.entity.Article;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ArticleRepositoryTest {
    @Autowired
    ArticleRepository articleRepository;

    @Test
    void findAllTest() {
        List<Article> articles = articleRepository.findAll(1L, 1499970L, 30L);
        log.info("article.size ={}", articles.size());
        for (Article article : articles) {
            log.info("articles ={}", article);
        }
    }

    @Test
    void countTest() {
        Long count = articleRepository.count(1L, 30000L);
        log.info("count ={}", count);
    }

    @Test
    void findInfiniteScrollTest() {
        List<Article> articles = articleRepository.findAllInfiniteScroll(1L, 30L);
        for (Article article : articles) {
            log.info("articleId ={}", article.getId());
        }

        Long lastArticleId = articles.getLast().getId();
        List<Article> articles2 = articleRepository.findAllInfiniteScroll(1L, 30L, lastArticleId);
        for (Article article : articles2) {
            System.out.println("article.getId() = " + article.getId());
        }
    }

}