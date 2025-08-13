package com.maksymilianst.lightweights.todo.repository;

import com.maksymilianst.lightweights.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

    boolean existsByIdAndUserId(Integer id, Integer userId);

    Set<Todo> findAllByUserId(Integer userId);
}
