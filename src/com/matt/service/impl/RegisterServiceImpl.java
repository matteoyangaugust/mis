package com.matt.service.impl;

import com.matt.service.BaseService;
import com.matt.service.RegisterService;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl extends BaseService implements RegisterService{

//    @Autowired
//    private CourseUserRepository courseUserRepository;
//    @Autowired
//    private CourseUserTeacherRepository courseUserTeacherRepository;
//
//    @Override
//    @Transactional(rollbackFor = Exception.class, transactionManager = "course")
//    public ResultBean insertSystemUser(System_user user) {
//        if(courseUserRepository.findUserByUsername(user.getPerson_id()) != null){
//            return new ResultBean(false, "此帳號已註冊");
//        }
//        Integer courseSn = courseUserRepository.insertSystemUser(user);
//        if(courseSn != null && courseSn != 0){
//            if(System_user.ROLE_SUPERVISOR.equals(user.getRole()) ||
//                    System_user.ROLE_TEACHER.equals(user.getRole())){
//                Course_user_teacher teacher = new Course_user_teacher();
//                teacher.setName(user.getName());
//                teacher.setCourse_user_sn(courseSn);
//                teacher.setType(user.getRole().equals(System_user.ROLE_TEACHER) ? 1 : 2);
//                Integer courseUserTeacherSn = courseUserTeacherRepository.insertCourseUserTeacher(teacher,
//                        "course_user_sn", "name", "type");
//                if(courseUserTeacherSn != null && courseUserTeacherSn != 0){
//                    return new ResultBean(true, "註冊成功");
//                }else{
//                    return new ResultBean(false, "註冊失敗");
//                }
//            }else{
//                return new ResultBean(true, "註冊成功");
//            }
//        }else{
//            return new ResultBean(false, "註冊失敗");
//        }
//    }
}
