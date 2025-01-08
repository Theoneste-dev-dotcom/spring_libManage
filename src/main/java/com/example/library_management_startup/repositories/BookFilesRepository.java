package com.example.library_management_startup.repositories;

import com.example.library_management_startup.entities.BookFiles;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookFilesRepository extends ListCrudRepository<BookFiles, Long> {
}
