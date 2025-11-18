package org.isetn;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassMatRepository extends JpaRepository<ClassMat, Long> {
    List<ClassMat> findByClasseCodClass(Long codClass);
}