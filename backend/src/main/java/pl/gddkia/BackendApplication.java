package pl.gddkia;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.gddkia.security.EntityAuthUser;
import pl.gddkia.security.EntityAuthUserRepository;

@SpringBootApplication
@RequiredArgsConstructor
public class BackendApplication implements ApplicationRunner {
    private final EntityAuthUserRepository entityAuthUserRepository;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        entityAuthUserRepository.save(new EntityAuthUser(1L, "malgo@wp.pl", "$2a$12$/6wOhkZScK/4el54TuWrguE/l.C2YLPp.tRJuLujhPp.b9XG0P/2q"));
    }
}
