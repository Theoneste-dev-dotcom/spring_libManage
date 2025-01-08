package com.example.library_management_startup.repositories;

import com.example.library_management_startup.entities.UserProfiles;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFilesRepository extends ListCrudRepository<UserProfiles, Long> {
}
