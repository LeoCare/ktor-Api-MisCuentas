package com.miscuentas.repositories.usuarios

import com.miscuentas.models.Usuario
import com.miscuentas.repositories.base.CrudRepository

interface UsuarioRepository: CrudRepository<Usuario, Long> {
    //aqui crearemos metodos especificos para el usuario:
}