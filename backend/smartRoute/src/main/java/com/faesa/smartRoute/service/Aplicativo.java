package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.AppUserDto;
import com.faesa.smartRoute.dto.ChangePassDto;

public interface Aplicativo {

    void cadastrarUsuario(AppUserDto appUserDto);

    void trocarSenha(ChangePassDto changePassDto);

}
