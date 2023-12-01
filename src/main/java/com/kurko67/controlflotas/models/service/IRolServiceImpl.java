package com.kurko67.controlflotas.models.service;

import com.kurko67.controlflotas.models.entity.Rol;
import com.kurko67.controlflotas.models.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IRolServiceImpl implements IRolService{

    @Autowired
    private IRolService iRolService;

    @Override
    public void save(Usuario usuario) {
        iRolService.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario FindByUser(Usuario usuario) {
        return iRolService.FindByUser(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Rol FindByName(String rol) {
        return iRolService.FindByName(rol);
    }

}
