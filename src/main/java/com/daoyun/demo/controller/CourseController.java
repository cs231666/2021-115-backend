package com.daoyun.demo.controller;


import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.pojo.dto.CourseDto;
import com.daoyun.demo.service.ICourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MaYan
 * @since 2021-05-31
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private ICourseService iCourseService;

    @ApiOperation("实现创建班课")
    @PostMapping("/")
    public ReturnInfo createCourse(@RequestBody CourseDto courseDto) {
        try{
            //前端应传什么数据给后端
            return this.iCourseService.createCourse(courseDto);
        }catch (Exception e){
            return ReturnInfo.error("创建失败");
        }
    }
    @ApiOperation("删除班课")
    @DeleteMapping("/{courseId}")
    public ReturnInfo deleteCourseById(@PathVariable("courseId") Integer courseId){
        try {
            return this.iCourseService.deleteCourseById(courseId);
        }catch (Exception e){
            return ReturnInfo.error("删除失败");
        }
    }

    @ApiOperation("更新班课")
    @PutMapping("/{courseId}/{courseName}/{note}")
    public ReturnInfo updateCourse(@PathVariable("courseId") Integer courseId, @PathVariable("courseName") String courseName,
                                   @PathVariable("note") String note){
        try{
            return this.iCourseService.updateCourseById(courseId,courseName,note);
        }catch (Exception e){
            return ReturnInfo.error("更新失败");
        }
    }


    @ApiOperation("实现获取班课")
    @GetMapping("/")
    public ReturnInfo getCourse() {
        try {
            return this.iCourseService.getCourse();
        }catch (Exception e){
            return ReturnInfo.error("获取失败");
        }
    }

    @ApiOperation("获取该用户班课")
    @GetMapping("{userId}")
    public ReturnInfo getUCourse(@PathVariable("userId") Integer userId){
        try {
            return this.iCourseService.getUCourse(userId);
        }catch (Exception e){
            return ReturnInfo.error("获取失败");
        }
    }

    @ApiOperation("加入班课")
    @PostMapping({"{courseId}/{userId}"})
    public ReturnInfo participateInCourse(@PathVariable("courseId") Integer courseId, @PathVariable("userId") Integer userId) {
        return this.iCourseService.participateInCourse(courseId, userId);
    }

    @ApiOperation("获取班课成员列表")
    @GetMapping("/course-member/{courerCode}")
    public ReturnInfo getCourseMember(@PathVariable("courerCode") String courerCode){
        try {
            return this.iCourseService.getCourseMember(courerCode);
        }catch (Exception e){
            return ReturnInfo.error("获取失败");
        }
    }

}
