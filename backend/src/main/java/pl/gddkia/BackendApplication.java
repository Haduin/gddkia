package pl.gddkia;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gddkia.branch.Branch;
import pl.gddkia.branch.BranchRepository;
import pl.gddkia.estimate.EstimateRepository;
import pl.gddkia.group.GroupRepository;
import pl.gddkia.job.JobRepository;
import pl.gddkia.region.Region;
import pl.gddkia.region.RegionRepository;
import pl.gddkia.security.EntityAuthUser;
import pl.gddkia.security.EntityAuthUserRepository;

@SpringBootApplication
@RequiredArgsConstructor
public class BackendApplication implements ApplicationRunner {
	private final JobRepository jobRepository;
	private final GroupRepository groupRepository;
	private final EstimateRepository estimateRepository;
	private final BranchRepository branchRepository;
	private final RegionRepository regionRepository;
	private final EntityAuthUserRepository entityAuthUserRepository;
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Branch branch = new Branch(null,"Warszawa",null);
branchRepository.save(branch);
		Region Siedlce = new Region(null,"Siedlce",branch,null);
		regionRepository.save(Siedlce);
		entityAuthUserRepository.save(new EntityAuthUser(1L,"user","$2a$12$/6wOhkZScK/4el54TuWrguE/l.C2YLPp.tRJuLujhPp.b9XG0P/2q"));
	}

	@RestController
	static
	class TestController {
		@GetMapping
		public String hello(){
			return "Hello World!";
		}
	}
}
