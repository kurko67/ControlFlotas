package com.kurko67.controlsalones.models.service;

import com.kurko67.controlsalones.models.entity.Rol;
import com.kurko67.controlsalones.models.entity.Usuario;

public interface IRolService {

    public void save(Usuario usuario);

    public Usuario FindByUser (Usuario usuario);

    public Rol FindByName(String rol);

}
