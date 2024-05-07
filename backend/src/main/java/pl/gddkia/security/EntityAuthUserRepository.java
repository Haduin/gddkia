package pl.gddkia.security;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntityAuthUserRepository extends JpaRepository<EntityAuthUser, Long> {
    Optional<EntityAuthUser> findMyUserByUsername(String username);
}
