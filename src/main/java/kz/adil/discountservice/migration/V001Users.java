package kz.adil.discountservice.migration;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import kz.adil.discountservice.model.user.User;
import kz.adil.discountservice.model.user.UserType;
import kz.adil.discountservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@ChangeUnit(id = "users", order = "001", author = "a.rakhimbekov")
public class V001Users {

    private final UserRepository userRepository;

    @Execution
    public void execute() {
        final List<User> users = new ArrayList<>();
        users.add(User.builder()
                .name("user1")
                .password("user1")
                .createdDate(LocalDate.now())
                .types(List.of(UserType.EMPLOYEE))
                .build());
        users.add(User.builder()
                .name("user2")
                .password("user2")
                .createdDate(LocalDate.now())
                .types(List.of(UserType.AFFILIATE))
                .build());
        users.add(User.builder()
                .name("user3")
                .password("user3")
                .createdDate(LocalDate.now().minusYears(3))
                .types(List.of(UserType.CUSTOMER))
                .build());
        userRepository.saveAll(users);
    }

    @RollbackExecution
    public void rollback() {
        userRepository.deleteAll();
    }

}
