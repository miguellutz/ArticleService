package com.obi.articleservice.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class NationalArticleDataStoreTest {
    @Test
    public void testArticleInstantiation() {
        String id = UUID.randomUUID().toString();
        ImmutableArticle immutableArticle = new ImmutableArticle(id);
        assertThat(immutableArticle.getId()).isEqualTo(id);
    }

    @Test
    public void testMutableArticleInstantiation() {
        String id = UUID.randomUUID().toString();
        MutableArticle mutableArticle = new MutableArticle(id);
        mutableArticle.setId(UUID.randomUUID().toString());
        
        assertThat(mutableArticle.getId()).isEqualTo(id);

    }
}