package com.gmail.kijevigombooc.jottinshoppin.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.kijevigombooc.jottinshoppin.server.common.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}