package pl.gddkia.branch;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByBranchAndRegionAndSection(String branch, String region, String section);
}
