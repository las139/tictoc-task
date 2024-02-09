package com.lsm.task.repository.parent;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsm.task.domain.parent.Parent;

public interface ParentRepository extends JpaRepository<Parent, Long> {
}
