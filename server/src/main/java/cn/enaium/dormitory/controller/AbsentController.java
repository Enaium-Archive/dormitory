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
import cn.dev33.satoken.stp.StpUtil;
import cn.enaium.dormitory.mapper.AbsentMapper;
import cn.enaium.dormitory.model.entity.AbsentEntity;
import cn.enaium.dormitory.model.request.AbsentAllRequest;
import cn.enaium.dormitory.model.request.AbsentInsertRequest;
import cn.enaium.dormitory.model.result.Result;
import cn.enaium.dormitory.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Date;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.enaium.dormitory.util.PageUtil.page;
import static cn.enaium.dormitory.util.WrapperUtil.queryWrapper;

/**
 * @author Enaium
 */
@RestController
@RequestMapping("/absent")
public class AbsentController {

    private final AbsentMapper absentMapper;

    public AbsentController(AbsentMapper absentMapper) {
        this.absentMapper = absentMapper;
    }

    @PostMapping("/all")
    public Result<IPage<Map<String, Object>>> all(@RequestBody AbsentAllRequest absentAllRequest) {
        return Result.success(absentMapper.findAll(page(absentAllRequest), queryWrapper(query -> {
            if (absentAllRequest.getValue() != null) {
                switch (absentAllRequest.getField()) {
                    case AbsentAllRequest.Type.BUILDING ->
                            query.like("building_name", absentAllRequest.getValue()).or().like("building_introduction", absentAllRequest.getValue());
                    case AbsentAllRequest.Type.DORMITORY -> query.like("dormitory_name", absentAllRequest.getValue());
                }
            }
        })));
    }

    @PostMapping("/insert")
    public Result<Object> insert(@RequestBody AbsentInsertRequest absentInsertRequest) {
        System.out.println(absentInsertRequest);
        if (!ObjectUtil.allNotNull(absentInsertRequest)) {
            return Result.fail(Result.Code.PARAM_ERROR);
        }

        final var entity = new AbsentEntity();
        entity.setBuildingId(absentInsertRequest.getBuilding());
        entity.setDormitoryId(absentInsertRequest.getDormitory());
        entity.setStudentId(absentInsertRequest.getStudent());
        entity.setDormitoryAdminId(StpUtil.getLoginIdAsInt());
        entity.setReason(absentInsertRequest.getReason());
        entity.setCreateDate(new Date(absentInsertRequest.getDate()));

        return Result.success(absentMapper.insert(entity));
    }
}
