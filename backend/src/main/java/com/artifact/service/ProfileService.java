package com.artifact.service;

import com.artifact.dao.UserDetailsDao;
import com.artifact.dao.UserSubscriptionDao;
import com.artifact.domain.User;
import com.artifact.domain.UserSubscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private final UserDetailsDao userDetailsDao;
    private final UserSubscriptionDao userSubscriptionDao;

    @Autowired
    public ProfileService(
            UserDetailsDao userDetailsDao,
            UserSubscriptionDao userSubscriptionDao
    ) {
        this.userDetailsDao = userDetailsDao;
        this.userSubscriptionDao = userSubscriptionDao;
    }

    public List<User> getList() {
        return userDetailsDao.findAll();
    }

    public User changeSubscription(User channel, User subscriber) {
        List<UserSubscription> subcriptions = channel.getSubscribers()
                .stream()
                .filter(subscription ->
                        subscription.getSubscriber().equals(subscriber)
                )
                .collect(Collectors.toList());

        if (subcriptions.isEmpty()) {
            UserSubscription subscription = new UserSubscription(channel, subscriber);
            channel.getSubscribers().add(subscription);
        } else {
            channel.getSubscribers().removeAll(subcriptions);
        }

        return userDetailsDao.save(channel);
    }

    public List<UserSubscription> getSubscribers(User channel) {
        return userSubscriptionDao.findByChannel(channel);
    }

    public UserSubscription changeSubscriptionStatus(User channel, User subscriber) {
        UserSubscription subscription = userSubscriptionDao.findByChannelAndSubscriber(channel, subscriber);
        subscription.setActive(!subscription.isActive());

        return userSubscriptionDao.save(subscription);
    }
}