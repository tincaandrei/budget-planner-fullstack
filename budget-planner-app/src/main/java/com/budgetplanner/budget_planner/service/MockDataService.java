package com.budgetplanner.budget_planner.service;

import com.budgetplanner.budget_planner.model.User;
import com.budgetplanner.budget_planner.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MockDataService {
    private final Faker faker = new Faker();
    private final UserRepository userRepository;

    public MockDataService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        generateMockData();
    }

    public void generateMockData() {
        List<User> users = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setId((long) i);
            user.setName(faker.name().fullName());
            user.setEmail(faker.internet().emailAddress());
            users.add(user);
        }

        userRepository.saveAll(users);
    }
}