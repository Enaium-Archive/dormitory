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
import cn.enaium.dormitory.mapper.MoveOutMapper;
import cn.enaium.dormitory.mapper.StudentMapper;
import cn.enaium.dormitory.model.entity.StudentEntity;
import cn.enaium.dormitory.model.request.StudentAllRequest;
import cn.enaium.dormitory.model.request.StudentPublishRequest;
import cn.enaium.dormitory.model.result.Result;
import cn.enaium.dormitory.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

import static cn.enaium.dormitory.util.PageUtil.page;
import static cn.enaium.dormitory.util.WrapperUtil.queryWrapper;

/**
 * @author Enaium
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentMapper studentMapper;

    public StudentController(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @GetMapping("/get/{id}")
    public Result<StudentEntity> get(@PathVariable Integer id) {
        return Result.success(studentMapper.selectById(id));
    }

    @SaCheckRole("system")
    @GetMapping("/delete/{id}")
    public Result<StudentEntity> delete(@PathVariable Integer id) {
        studentMapper.deleteById(id);
        return Result.success();
    }

    @PostMapping("/all")
    public Result<IPage<Map<String, Object>>> all(@RequestBody(required = false) StudentAllRequest studentAllRequest) {
        return Result.success(studentMapper.findAll(page(studentAllRequest), queryWrapper(query -> {
            if (studentAllRequest.getValue() != null) {
                switch (studentAllRequest.getField()) {
                    case StudentAllRequest.Type.NUMBER -> query.like("number", studentAllRequest.getValue());
                    case StudentAllRequest.Type.NAME -> query.like("name", studentAllRequest.getValue());
                }
            }
        })));
    }

    @SaCheckRole("system")
    @PostMapping("/publish")
    public Result<Object> publish(@RequestBody StudentPublishRequest studentPublishRequest) {

        final var studentEntity = new StudentEntity();

        studentEntity.setId(studentPublishRequest.getId());
        studentEntity.setNumber(studentPublishRequest.getNumber());
        studentEntity.setName(studentPublishRequest.getName());
        studentEntity.setGender(studentPublishRequest.getGender());
        studentEntity.setDormitoryId(studentPublishRequest.getDormitory());
        studentEntity.setState("入住");

        if (studentPublishRequest.getId() == null) {
            if (!ObjectUtil.allNotNull(studentPublishRequest)) {
                return Result.fail(Result.Code.PARAM_ERROR);
            }
            studentEntity.setCreateDate(new Date());
            studentMapper.insert(studentEntity);
        } else {
            studentMapper.updateById(studentEntity);
        }

        return Result.success();
    }
}
