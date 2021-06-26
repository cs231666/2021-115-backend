package com.daoyun.demo.service;

import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.pojo.dto.SignInDTO;
import com.daoyun.demo.pojo.dto.SignLogDTO;

public interface ISignInService {
    ReturnInfo startSignIn(SignInDTO signInDTO);

    ReturnInfo signIn(SignLogDTO signLogDTO);

    ReturnInfo getSignInfoForCourse(String course_code);

    ReturnInfo hasSignIn(String course_code);

    ReturnInfo allSignIn(String course_code);

    ReturnInfo startClickSignIn(SignInDTO signInDTO);

    ReturnInfo stopClickSignIn(String course_code);

    ReturnInfo getSignInInfoBySignInId(Integer sign_id);

}
