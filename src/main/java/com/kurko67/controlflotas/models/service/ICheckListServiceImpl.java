package com.kurko67.controlflotas.models.service;

import com.kurko67.controlflotas.models.dao.ICheckListDao;
import com.kurko67.controlflotas.models.entity.CheckList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ICheckListServiceImpl implements ICheckListService {

    @Autowired
    ICheckListDao checkListDao;

    @Override
    @Transactional(readOnly = true)
    public List<CheckList> findAll() {
        return (List<CheckList>) checkListDao.findAll();
    }

    @Override
    @Transactional
    public void save(CheckList checkList) {
        checkListDao.save(checkList);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean findOne(Long id) {
        return checkListDao.existsById(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        checkListDao.deleteById(id);
    }
}
