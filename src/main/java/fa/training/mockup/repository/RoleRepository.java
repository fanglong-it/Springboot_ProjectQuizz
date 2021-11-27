package fa.training.mockup.repository;

import fa.training.mockup.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity getRoleEntityById(long id);
}
