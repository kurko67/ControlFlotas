package com.kurko67.controlflotas.models.service;

import com.kurko67.controlflotas.models.dao.IUsuarioDao;
import com.kurko67.controlflotas.models.entity.Rol;
import com.kurko67.controlflotas.models.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Optional;

@Service("userDetailsService")
public class UsuarioService implements UserDetailsService {

    @Autowired
    private IUsuarioDao usuarioDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioDao.findByUsername(username);

        if(usuario == null){
            throw new UsernameNotFoundException(username);
        }

        var roles = new ArrayList<GrantedAuthority>();

        for(Rol rol: usuario.getRoles()){
            roles.add(new SimpleGrantedAuthority(rol.getNombre()));
        }

        return new User(usuario.getUsername(), usuario.getPassword(), roles);

    }

    public void UpdatePassword(String password, Long idsuario){
        usuarioDao.UpdatePassword(password, idsuario);
    }

    public void ResetPassword(Long idsuario){
        usuarioDao.ResetPassword(idsuario);
    }

    public void InsertRol(Long idUsuario){
        usuarioDao.InsertRol(idUsuario);
    }

    public void save(Usuario usuario) { usuarioDao.save(usuario);}

    public void DeleteRol(Long id){
        usuarioDao.DeleteRol(id);
    }

    public void HabilitarDeshabilitarUser(boolean habilitado, Long id){
        usuarioDao.HabilitarDeshabilitarUser(habilitado,id);
    }

    public void habilitarUsuario(Long idUsuario){
        Optional<Usuario> optionalUsuario = usuarioDao.findById(idUsuario);
        if(optionalUsuario.isPresent()){
            Usuario usuario = optionalUsuario.get();
            usuario.setHabilitado(true);
            usuarioDao.save(usuario);
        }
        else{
            System.out.println("Usuario no encontrado");
        }
    }

    public void DeshabilitarUsuario(Long idUsuario){
        Optional<Usuario> optionalUsuario = usuarioDao.findById(idUsuario);
        if(optionalUsuario.isPresent()){
            Usuario usuario = optionalUsuario.get();
            usuario.setHabilitado(false);
            usuarioDao.save(usuario);
        }
        else{
            System.out.println("Usuario no encontrado");
        }
    }

}
