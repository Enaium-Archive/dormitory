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

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.enaium.dormitory.mapper.AccountMapper;
import cn.enaium.dormitory.model.entity.AccountEntity;
import cn.enaium.dormitory.model.request.AccountAllRequest;
import cn.enaium.dormitory.model.request.AccountPublishRequest;
import cn.enaium.dormitory.model.result.Result;
import cn.enaium.dormitory.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import static cn.enaium.dormitory.util.PageUtil.page;
import static cn.enaium.dormitory.util.WrapperUtil.queryWrapper;

/**
 * @author Enaium
 */
@SaCheckRole("system")
@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountMapper accountMapper;

    public AccountController(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @GetMapping("/get/{id}")
    public Result<AccountEntity> get(@PathVariable Integer id) {
        return Result.success(accountMapper.selectById(id));
    }

    @GetMapping("/delete/{id}")
    public Result<AccountEntity> delete(@PathVariable Integer id) {
        accountMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping("/all")
    public Result<Page<AccountEntity>> all(@RequestBody(required = false) AccountAllRequest accountAllRequest) {
        return Result.success(accountMapper.selectPage(page(accountAllRequest), queryWrapper(query -> {
            query.eq("role", "admin");
            if (accountAllRequest.getValue() != null) {
                switch (accountAllRequest.getField()) {
                    case AccountAllRequest.Type.USERNAME -> query.like("username", accountAllRequest.getValue());
                    case AccountAllRequest.Type.NAME -> query.like("name", accountAllRequest.getValue());
                    case AccountAllRequest.Type.PHONE -> query.like("phone", accountAllRequest.getValue());
                }
            }
        })));
    }

    @PostMapping("/publish")
    public Result<Object> publish(@RequestBody AccountPublishRequest accountPublishRequest) {
        final var accountEntity = new AccountEntity();
        accountEntity.setId(accountPublishRequest.getId());
        accountEntity.setUsername(accountPublishRequest.getUsername());
        accountEntity.setPassword(accountPublishRequest.getPassword());
        accountEntity.setName(accountPublishRequest.getName());
        accountEntity.setGender(accountPublishRequest.getGender());
        accountEntity.setPhone(accountPublishRequest.getPhone());
        accountEntity.setRole("admin");

        if (accountPublishRequest.getId() == null) {
            if (!ObjectUtil.allNotNull(accountPublishRequest)) {
                return Result.fail(Result.Code.PARAM_ERROR);
            }
            accountMapper.insert(accountEntity);
        } else {
            accountMapper.updateById(accountEntity);
        }
        return Result.success();
    }
}
