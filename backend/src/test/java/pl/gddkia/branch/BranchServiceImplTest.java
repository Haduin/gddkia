package pl.gddkia.branch;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gddkia.GenericPostgresTestContainer;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BranchServiceImplTest extends GenericPostgresTestContainer {

    @InjectMocks
    BranchServiceImpl branchService;

    @Mock
    BranchRepository branchRepository;

    @Test
    void getAll() {
        List<Branch> branches = List.of(new Branch());
        Mockito.when(branchRepository.findAll())
                .thenReturn(branches);

        List<BranchRest> result = branchService.getAll();
        assertEquals(branches.size(), result.size());
    }

    @Test
    void getById() {
        Branch oldBranch = new Branch(
                1L,
                "Białystok",
                "Augustów",
                "Augustów I",
                "16",
                "Augustów",
                "16-300 Augustów",
                "ul. Wojska Polskiego 54",
                "",
                68.002,
                "Tak",
                53.8371033031799,
                22.9713162030314,
                "",
                null
        );
        Mockito.when(branchRepository.findById(anyLong()))
                .thenReturn(Optional.of(oldBranch));

        Optional<BranchRest> result = branchService.getById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void testUpdateBranchDetails_Success() {
        Branch oldBranch = new Branch(
                1L,
                "Białystok",
                "Augustów",
                "Augustów I",
                "16",
                "Augustów",
                "16-300 Augustów",
                "ul. Wojska Polskiego 54",
                "",
                68.002,
                "Tak",
                53.8371033031799,
                22.9713162030314,
                "",
                null
        );
        UpdateBranchDto updateRequest = new UpdateBranchDto(
                "Białystok",
                "Augustów",
                "Augustów I",
                "16 TEST",
                "Augustów TEST",
                "16-300 Augustów TEST",
                "ul. Wojska Polskiego 54 TEST",
                "",
                68.002,
                "Tak",
                53.8371033031799,
                22.9713162030314,
                ""
        );

        Branch mappedBranch = BranchMapper.mapToEntity(oldBranch, updateRequest);

        Mockito.when(branchRepository.findByBranchAndRegionAndSection(
                updateRequest.branch(),
                updateRequest.region(),
                updateRequest.section())
        ).thenReturn(Optional.of(oldBranch));

        Mockito.when(branchRepository.save(any(Branch.class))).thenReturn(mappedBranch);

        UpdateBranchResult result = branchService.updateBranchDetails(oldBranch.getId(), updateRequest);

        assertInstanceOf(UpdateBranchResult.SuccessfullyUpdate.class, result);
        assertEquals("Branch [1] was updated", ((UpdateBranchResult.SuccessfullyUpdate) result).response());
        verify(branchRepository, times(1)).findByBranchAndRegionAndSection(updateRequest.branch(), updateRequest.region(), updateRequest.section());
        verify(branchRepository, times(1)).save(mappedBranch);
    }

    @Test
    void testUpdateBranchDetails_Failure() {
        Branch oldBranch = new Branch(
                1L,
                "Białystok",
                "Augustów",
                "Augustów I",
                "16",
                "Augustów",
                "16-300 Augustów",
                "ul. Wojska Polskiego 54",
                "",
                68.002,
                "Tak",
                53.8371033031799,
                22.9713162030314,
                "",
                null
        );
        UpdateBranchDto updateRequest = new UpdateBranchDto(
                "Białystok",
                "Augustów",
                "Augustów I",
                "16 TEST",
                "Augustów TEST",
                "16-300 Augustów TEST",
                "ul. Wojska Polskiego 54 TEST",
                "",
                68.002,
                "Tak",
                53.8371033031799,
                22.9713162030314,
                ""
        );

        Mockito.when(branchRepository.findByBranchAndRegionAndSection(
                updateRequest.branch(),
                updateRequest.region(),
                updateRequest.section())
        ).thenReturn(Optional.empty());

        UpdateBranchResult result = branchService.updateBranchDetails(oldBranch.getId(), updateRequest);

        assertInstanceOf(UpdateBranchResult.FailToUpdate.class, result);
        assertEquals("Branch [1] was not updated", ((UpdateBranchResult.FailToUpdate) result).response());
        verify(branchRepository, times(1)).findByBranchAndRegionAndSection(updateRequest.branch(), updateRequest.region(), updateRequest.section());
        verify(branchRepository, never()).save(any(Branch.class));
    }

}