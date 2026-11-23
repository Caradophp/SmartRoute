package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.AppUserDto;
import com.faesa.smartRoute.dto.ChangePassDto;

/**
 * Interface para gerenciar o cadastro e a troca de senha
 * dos usuários do aplicativo mobile
 */
public interface Aplicativo {

    void cadastrarUsuario(AppUserDto appUserDto);

    void trocarSenha(ChangePassDto changePassDto);

}
