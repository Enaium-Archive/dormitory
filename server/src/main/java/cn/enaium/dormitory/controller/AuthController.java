/*
 * Copyright (c) 2023 Enaium
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cn.enaium.dormitory.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.enaium.dormitory.mapper.AccountMapper;
import cn.enaium.dormitory.model.request.LoginRequest;
import cn.enaium.dormitory.model.result.Result;
import org.springframework.web.bind.annotation.*;

import static cn.enaium.dormitory.util.WrapperUtil.queryWrapper;

/**
 * @author Enaium
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AccountMapper accountMapper;

    public AuthController(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @PostMapping("/login")
    public Result<Object> login(@RequestBody LoginRequest loginRequest) {
        final var accountEntity = accountMapper.selectOne(queryWrapper(query -> query.eq("username", loginRequest.getUsername())));

        if (accountEntity == null) {
            return Result.fail(Result.Code.USER_NOT_EXIST);
        }

        if (!accountEntity.getPassword().equals(loginRequest.getPassword())) {
            return Result.fail(Result.Code.PASSWORD_NOT_MATCH);
        }

        return Result.success(StpUtil.createLoginSession(accountEntity.getId()));
    }

    @GetMapping("/logout")
    public Result<Object> logout() {
        StpUtil.logout();
        return Result.success();
    }
}
