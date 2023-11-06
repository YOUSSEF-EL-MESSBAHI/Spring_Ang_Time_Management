package com.example.miniprojetjee.services;

import com.example.miniprojetjee.entity.Work;

import java.util.List;

public interface IWorkService {
    List<Work> getWorks();

    void addWork(Work work);

    void updateWork(Work work);
}
