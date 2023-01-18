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
import cn.enaium.dormitory.mapper.DormitoryMapper;
import cn.enaium.dormitory.model.entity.DormitoryEntity;
import cn.enaium.dormitory.model.request.DormitoryAllRequest;
import cn.enaium.dormitory.model.request.DormitoryPublishRequest;
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
@RequestMapping("/dormitory")
public class DormitoryController {
    private final DormitoryMapper dormitoryMapper;

    public DormitoryController(DormitoryMapper dormitoryMapper) {
        this.dormitoryMapper = dormitoryMapper;
    }

    @GetMapping("/get/{id}")
    public Result<DormitoryEntity> get(@PathVariable Integer id) {
        return Result.success(dormitoryMapper.selectById(id));
    }

    @SaCheckRole("system")
    @GetMapping("/delete/{id}")
    public Result<DormitoryEntity> delete(@PathVariable Integer id) {
        dormitoryMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping("/all")
    public Result<IPage<Map<String, Object>>> all(@RequestBody(required = false) DormitoryAllRequest dormitoryAllRequest) {
        return Result.success(dormitoryMapper.findAll(page(dormitoryAllRequest), queryWrapper(query -> {
            if (dormitoryAllRequest.getValue() != null) {
                switch (dormitoryAllRequest.getField()) {
                    case DormitoryAllRequest.Type.NAME -> query.like("name", dormitoryAllRequest.getValue());
                    case DormitoryAllRequest.Type.PHONE -> query.like("telephone", dormitoryAllRequest.getValue());
                }
            }
        })));
    }

    @SaCheckRole("system")
    @PostMapping("/publish")
    public Result<Object> publish(@RequestBody DormitoryPublishRequest dormitoryPublishRequest) {

        final var dormitoryEntity = new DormitoryEntity();

        dormitoryEntity.setId(dormitoryPublishRequest.getId());
        dormitoryEntity.setBuildingId(dormitoryPublishRequest.getBuilding());
        dormitoryEntity.setName(dormitoryPublishRequest.getName());
        dormitoryEntity.setType(dormitoryPublishRequest.getType());
        dormitoryEntity.setAvailable(dormitoryPublishRequest.getType());
        dormitoryEntity.setTelephone(dormitoryPublishRequest.getPhone());


        if (dormitoryPublishRequest.getId() == null) {
            if (!ObjectUtil.allNotNull(dormitoryPublishRequest)) {
                return Result.fail(Result.Code.PARAM_ERROR);
            }
            dormitoryMapper.insert(dormitoryEntity);
        } else {
            dormitoryMapper.updateById(dormitoryEntity);
        }
        return Result.success();
    }
}
