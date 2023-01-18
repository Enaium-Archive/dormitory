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
import cn.enaium.dormitory.mapper.BuildingMapper;
import cn.enaium.dormitory.model.entity.BuildingEntity;
import cn.enaium.dormitory.model.request.BuildPublishRequest;
import cn.enaium.dormitory.model.request.BuildingAllRequest;
import cn.enaium.dormitory.model.result.Result;
import cn.enaium.dormitory.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static cn.enaium.dormitory.util.PageUtil.page;
import static cn.enaium.dormitory.util.WrapperUtil.queryWrapper;

/**
 * @author Enaium
 */
@RestController
@RequestMapping("/building")
public class BuildingController {

    private final BuildingMapper buildingMapper;

    public BuildingController(BuildingMapper buildingMapper) {
        this.buildingMapper = buildingMapper;
    }

    @GetMapping("/get/{id}")
    public Result<BuildingEntity> get(@PathVariable Integer id) {
        return Result.success(buildingMapper.selectById(id));
    }

    @SaCheckRole("system")
    @GetMapping("/delete/{id}")
    public Result<BuildingEntity> delete(@PathVariable Integer id) {
        buildingMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping("/all")
    public Result<IPage<Map<String, Object>>> all(@RequestBody(required = false) BuildingAllRequest buildingAllRequest) {
        return Result.success(buildingMapper.findAll(page(buildingAllRequest), queryWrapper(query -> {
            if (buildingAllRequest.getValue() != null) {
                switch (buildingAllRequest.getField()) {
                    case BuildingAllRequest.Type.NAME -> query.like("name", buildingAllRequest.getValue());
                    case BuildingAllRequest.Type.INTRODUCTION ->
                            query.like("introduction", buildingAllRequest.getValue());
                }
            }
        })));
    }

    @SaCheckRole("system")
    @PostMapping("/publish")
    public Result<Object> publish(@RequestBody BuildPublishRequest buildPublishRequest) {
        final var entity = new BuildingEntity();
        entity.setId(buildPublishRequest.getId());
        entity.setName(buildPublishRequest.getName());
        entity.setIntroduction(buildPublishRequest.getIntroduction());
        entity.setAdminId(buildPublishRequest.getAdmin());

        if (buildPublishRequest.getId() == null) {
            if (!ObjectUtil.allNotNull(buildPublishRequest)) {
                return Result.fail(Result.Code.PARAM_ERROR);
            }

            buildingMapper.insert(entity);
        } else {
            buildingMapper.updateById(entity);
        }
        return Result.success();
    }
}
