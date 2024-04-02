package rocha.andre.api.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.user.DTO.*;
import rocha.andre.api.domain.user.UseCase.*;
import rocha.andre.api.infra.security.TokenJwtDto;
import rocha.andre.api.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private CreateUse createUse;
    @Autowired
    private GetUserByID getUserByID;
    @Autowired
    private GetUserIdByJWT getUserIdByJWT;
    @Autowired
    private ForgotPassword forgotPassword;
    @Autowired
    private PerformLogin performLogin;
    @Autowired
    private UpdateUser updateUser;
    @Autowired
    private ResetPassword resetPassword;

    @Override
    public TokenJwtDto performLogin(UserLoginDTO data) {
        var tokenJwt = performLogin.performLogin(data);
        return tokenJwt;
    }

    @Override
    public UserReturnDTO createUser(UserDTO data) {
        var user = createUse.createUser(data);
        return user;
    }

    @Override
    public UserReturnDTO getUserByID(String id) {
        var user = getUserByID.getUserByID(id);
        return user;
    }

    @Override
    public UserForgotDTO forgotPassword(UserOnlyLoginDTO data) {
        var retorno = forgotPassword.forgotPassword(data);
        return retorno;
    }

    @Override
    public String resetPassword(UserResetPassDTO data) {
        resetPassword.resetPassword(data);
        return "Password successfully updated!";
    }

    @Override
    public UserIdDTO getUserIdByJWT(String token) {
        var userId = getUserIdByJWT.getUserByJWT(token);
        return userId;
    }

    @Override
    public UserReturnDTO updateUserInfo(UserUpdateDTO data, String uuidString) {
        var updatedUser = updateUser.updateUserInfo(data, uuidString);
        return updatedUser;
    }
}
