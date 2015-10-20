package com.cssru.companies.dao;

import com.cssru.companies.domain.Staff;


public interface StaffDao {
    void create(Staff staff);

    Staff get(Long id);

    void update(Staff staff);

    void delete(Long id);
}
