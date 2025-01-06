package com.tghtechnology.posweb.service;

import com.tghtechnology.posweb.data.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}