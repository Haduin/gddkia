package pl.gddkia.group;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface GroupRepository extends ListCrudRepository<Group, Long> {
    List<Group> findAllByEstimateId(Long id);
}
