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
        users.add(new User("user1", "123", LocalDate.now(), List.of(UserType.EMPLOYEE)));
        users.add(new User("user2", "123", LocalDate.now(), List.of(UserType.AFFILIATE)));
        users.add(new User("user2", "123", LocalDate.now().minusYears(3), List.of(UserType.CUSTOMER)));
        userRepository.saveAll(users);
    }

    @RollbackExecution
    public void rollback() {
        userRepository.deleteAll();
    }

}
