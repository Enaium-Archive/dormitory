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
import cn.enaium.dormitory.model.entity.MoveOutEntity;
import cn.enaium.dormitory.model.request.MoveOutAllRequest;
import cn.enaium.dormitory.model.request.MoveOutRequest;
import cn.enaium.dormitory.model.result.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

import static cn.enaium.dormitory.util.PageUtil.page;
import static cn.enaium.dormitory.util.WrapperUtil.queryWrapper;

/**
 * @author Enaium
 */
@SaCheckRole("system")
@RestController
@RequestMapping("/moveOut")
public class MoveOutController {

    private final StudentMapper studentMapper;
    private final MoveOutMapper moveOutMapper;

    public MoveOutController(StudentMapper studentMapper, MoveOutMapper moveOutMapper) {
        this.studentMapper = studentMapper;
        this.moveOutMapper = moveOutMapper;
    }

    @PostMapping("/all")
    public Result<IPage<Map<String, Object>>> all(@RequestBody(required = false) MoveOutAllRequest moveOutAllRequest) {
        return Result.success(moveOutMapper.findAll(page(moveOutAllRequest), queryWrapper(query -> {
            if (moveOutAllRequest.getValue() != null) {
                switch (moveOutAllRequest.getField()) {
                    case MoveOutAllRequest.Type.STUDENT -> query.like("student_name", moveOutAllRequest.getValue());
                    case MoveOutAllRequest.Type.DORMITORY -> query.like("dormitory_name", moveOutAllRequest.getValue());
                }
            }
        })));
    }

    @PostMapping("/out")
    public Result<Object> moveOut(@RequestBody MoveOutRequest moveOutRequest) {
        final var studentEntity = studentMapper.selectById(moveOutRequest.getStudent());
        if (studentEntity == null) {
            return Result.fail(Result.Code.STUDENT_NOT_EXIST);
        }

        studentEntity.setState("迁出");

        final var moveOutEntity = new MoveOutEntity();
        moveOutEntity.setStudentId(moveOutRequest.getStudent());
        moveOutEntity.setDormitoryId(studentEntity.getDormitoryId());
        moveOutEntity.setReason(moveOutRequest.getReason());
        moveOutEntity.setCreateDate(new Date());

        moveOutMapper.insert(moveOutEntity);

        studentMapper.updateById(studentEntity);
        return Result.success();
    }
}
