package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.entity.GPS;


public interface GPSRepository extends CrudRepository<GPS, Long>, PagingAndSortingRepository<GPS, Long> {
}
