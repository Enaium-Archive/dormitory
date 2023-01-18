package cn.enaium.dormitory;

import cn.enaium.dormitory.mapper.AbsentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static cn.enaium.dormitory.util.PageUtil.page;
import static cn.enaium.dormitory.util.WrapperUtil.queryWrapper;

@SpringBootTest
class DormitoryApplicationTests {


    @Autowired
    private AbsentMapper absentMapper;

    @Test
    void contextLoads() {
        for (Map<String, Object> record : absentMapper.findAll(page(1, 10), queryWrapper(query -> {

        })).getRecords()) {
            System.out.println(record);
        }
    }
}
