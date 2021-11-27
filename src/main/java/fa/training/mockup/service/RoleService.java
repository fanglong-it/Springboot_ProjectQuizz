package fa.training.mockup.service;

import fa.training.mockup.entity.RoleEntity;
import fa.training.mockup.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;
    public RoleEntity getRoleById(long id){
        RoleEntity roleEntity = roleRepository.getRoleEntityById(id);
        return roleEntity;
    }
}
