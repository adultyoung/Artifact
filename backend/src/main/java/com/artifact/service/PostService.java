package com.artifact.service;

import com.artifact.dao.PostDao;
import com.artifact.dao.UserSubscriptionDao;
import com.artifact.domain.Post;
import com.artifact.domain.User;
import com.artifact.domain.UserSubscription;
import com.artifact.domain.Views;
import com.artifact.dto.EventType;
import com.artifact.dto.MetaDto;
import com.artifact.dto.ObjectType;
import com.artifact.dto.PostPageDto;
import com.artifact.utils.WsSender;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PostService {
    private static String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private static String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    private static Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static Pattern IMG_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);

    private final PostDao postDao;
    private final UserSubscriptionDao userSubscriptionDao;
    private final BiConsumer<EventType, Post> wsSender;

    @Autowired
    public PostService(
            PostDao postDao,
            UserSubscriptionDao userSubscriptionDao,
            WsSender wsSender
    ) {
        this.postDao = postDao;
        this.userSubscriptionDao = userSubscriptionDao;
        this.wsSender = wsSender.getSender(ObjectType.POST, Views.IdName.class);
    }


    private void fillMeta(Post post) throws IOException {
        String text = post.getText();
        Matcher matcher = URL_REGEX.matcher(text);

        if (matcher.find()) {
            String url = text.substring(matcher.start(), matcher.end());

            matcher = IMG_REGEX.matcher(url);

            post.setLink(url);

            if (matcher.find()) {
                post.setLinkCover(url);
            } else if (!url.contains("youtu")) {
                MetaDto meta = getMeta(url);

                post.setLinkCover(meta.getCover());
                post.setLinkTitle(meta.getTitle());
                post.setLinkDescription(meta.getDescription());
            }
        }
    }

    private MetaDto getMeta(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        Elements title = doc.select("meta[name$=title],meta[property$=title]");
        Elements description = doc.select("meta[name$=description],meta[property$=description]");
        Elements cover = doc.select("meta[name$=image],meta[property$=image]");

        return new MetaDto(
                getContent(title.first()),
                getContent(description.first()),
                getContent(cover.first())
        );
    }

    private String getContent(Element element) {
        return element == null ? "" : element.attr("content");
    }

    public void delete(Post post) {
        postDao.delete(post);
        wsSender.accept(EventType.REMOVE, post);
    }

    public Post update(Post postFromDb, Post post) throws IOException {
        BeanUtils.copyProperties(post, postFromDb, "id");
        fillMeta(postFromDb);
        Post updatedPost = postDao.save(postFromDb);

        wsSender.accept(EventType.UPDATE, updatedPost);

        return updatedPost;
    }

    public Post create(Post post, User user) throws IOException {
        post.setCreationDate(LocalDateTime.now());
        fillMeta(post);
        post.setAuthor(user);
        Post updatedPost = postDao.save(post);

        wsSender.accept(EventType.CREATE, updatedPost);

        return updatedPost;
    }

    public PostPageDto findForUser(Pageable pageable, User user) {
        List<User> channels = userSubscriptionDao.findBySubscriber(user)
                .stream()
                .filter(UserSubscription::isActive)
                .map(UserSubscription::getChannel)
                .collect(Collectors.toList());

        channels.add(user);

        Page<Post> page = postDao.findByAuthorIn(channels, pageable);

        return new PostPageDto(
                page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages()
        );
    }
}
