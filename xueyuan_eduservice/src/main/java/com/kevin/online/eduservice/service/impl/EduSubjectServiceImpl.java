package com.kevin.online.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kevin.online.eduservice.dto.OneSubjectDto;
import com.kevin.online.eduservice.dto.TwoSubjectDto;
import com.kevin.online.eduservice.entity.EduSubject;
import com.kevin.online.eduservice.handler.EduException;
import com.kevin.online.eduservice.mapper.EduSubjectMapper;
import com.kevin.online.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kevin
 * @since 2020-05-02
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public List<String> importSubject(MultipartFile file) {

        try {
            //1.获取文件输入流
            InputStream inputStream = file.getInputStream();
            //2.创建workbook
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            // 3.workbook获取sheet
            XSSFSheet sheet = workbook.getSheetAt(0);
            //创建集合，用于存储错误信息并返回
            List<String> msg = new ArrayList<>();
            // 4.sheet获取row，从第二行开始循环获取，第一行是表头
            //获取表中最后一行的索引
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                //得到每一行
                XSSFRow row = sheet.getRow(i);
                //如果行为空，提示错误信息
                if (row == null) {
                    String str = "第" + i + "行数据为空";
                    msg.add(str);
                    continue;
                }
                //行中不为空
                // 5.row获取第一列
                XSSFCell cellOne = row.getCell(0);
                //判断cell是否为空
                if (cellOne == null) {
                    String str = "第" + i + "行数据为空";
                    msg.add(str);
                    // 调出改行，往下继续执行
                    continue;
                }
                //列不为空，获取第一列值
                //一级分类的值
                String cellOneValue = cellOne.getStringCellValue();
                //添加一级分类
                // Excel中有很多重复的一级分类
                //判断添加的一级分类是否已经存在，存在则不加
                EduSubject existOneSubject = existOneEduSubjectByTitle(cellOneValue);
                //存储一级分类id
                String id_parent = null;
                if (existOneSubject == null) {
                    //不存在一级分类，添加
                    EduSubject eduSubject = new EduSubject();
                    eduSubject.setTitle(cellOneValue);
                    eduSubject.setParentId("0");
                    eduSubject.setSort(0);
                    baseMapper.insert(eduSubject);
                    //新添加的一级分类id赋值给parentId
                    id_parent = eduSubject.getId();
                } else {
                    //存在 不添加 把一级分类的id赋值给parentId
                    id_parent = existOneSubject.getId();
                }
                //获取第二列
                XSSFCell cellTwo = row.getCell(1);
                if (cellTwo == null) {
                    String str = "第" + i + "行数据为空";
                    msg.add(str);
                    continue;
                }
                //不为空 获取值
                String cellTwoValue = cellTwo.getStringCellValue();
                //添加二级分类
                //判断是否存在二级分类，存在不添加否则相反
                EduSubject twoEduSubject = existTwoEduSubjectByTitle(cellTwoValue, id_parent);
                if (twoEduSubject == null) {
                    EduSubject eduSubject = new EduSubject();
                    eduSubject.setTitle(cellTwoValue);
                    eduSubject.setParentId(id_parent);
                    eduSubject.setSort(0);
                    baseMapper.insert(eduSubject);
                }
            }
            return msg;
        } catch (IOException e) {
            e.printStackTrace();
            throw new EduException(20001, "导入失败");
        }
    }

    @Override
    public EduSubject existOneEduSubjectByTitle(String title) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title).eq("parent_id", "0");
        return baseMapper.selectOne(wrapper);
    }


    @Override
    public EduSubject existTwoEduSubjectByTitle(String title, String parentId) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", title).eq("parent_id", parentId);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Boolean deleteSubjectById(String id) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(wrapper);
        //判断是否有二级分类
        if (count > 0) {
            return false;
        } else {
            //没有二级分类,进行删除
            int result = baseMapper.deleteById(id);
            return result > 0;
        }

    }

    @Override
    public List<OneSubjectDto> getSubjectList() {
        //查询所有一级分类
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("parent_id", "0");
        List<EduSubject> allOneSubjects = baseMapper.selectList(wrapper1);
        //查询所有二级分类
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id", "0");
        List<EduSubject> allTwoSubject = baseMapper.selectList(wrapper2);
        //创建集合用于存储所有一级分类
        List<OneSubjectDto> dtos = new ArrayList<>();
        //构建一级分类，遍历所有一级分类，得到每个EduSubject对象，把每个EduSubject对象转换OneSubjectDto
        for (int i = 0; i < allOneSubjects.size(); i++) {
            //获取每个EduSubject对象
            EduSubject eduOneSubject = allOneSubjects.get(i);
            OneSubjectDto oneSubjectDto = new OneSubjectDto();
            BeanUtils.copyProperties(eduOneSubject, oneSubjectDto);
            dtos.add(oneSubjectDto);
            //获取所有一级分类的二级分类
            //创建集合用于存储二级分类
            List<TwoSubjectDto> twoSubjectDtoList = new ArrayList<>();
            //遍历所有二级分类，得到每个二级分类
            for (int m = 0; m < allTwoSubject.size(); m++) {
                EduSubject eduTwoSubject = allTwoSubject.get(m);
                if (eduTwoSubject.getParentId().equals(eduOneSubject.getId())) {
                    TwoSubjectDto twoSubjectDto = new TwoSubjectDto();
                    BeanUtils.copyProperties(eduTwoSubject, twoSubjectDto);
                    twoSubjectDtoList.add(twoSubjectDto);
                }
            }
            oneSubjectDto.setChildren(twoSubjectDtoList);
        }
        return dtos;
    }

    @Override
    public Boolean saveOneLevel(EduSubject eduSubject) {
        //判断一级分类是否存在，存在则不加
        EduSubject existOne = existOneEduSubjectByTitle(eduSubject.getTitle());
        if (existOne == null) {
            //一级分类parentId为0
            eduSubject.setParentId("0");
            int result = baseMapper.insert(eduSubject);
            return result>0;
        }else {
            return false;
        }
    }

    @Override
    public Boolean saveTwoLevel(EduSubject eduSubject) {
        //判断二级分类是否存在
        EduSubject twoEduSubject = existTwoEduSubjectByTitle(eduSubject.getTitle(),eduSubject.getParentId());
        if (twoEduSubject == null) {
            int result = baseMapper.insert(eduSubject);
            return result>0;
        }else {
            return false;
        }
    }
}
