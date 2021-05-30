package com.debug.pmp.server.shiro;

import com.debug.pmp.model.entity.SysUserEntity;
import com.debug.pmp.model.mapper.SysUserDao;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author baoguangyu
 * @Date 2021/5/30 15:28
 * @Version 1.0
 */
@Component
public class UserRealm extends AuthorizingRealm {

    private static final Logger log= LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private SysUserDao sysUserDao;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        final String username = token.getUsername();
        final String passeord = String.valueOf(token.getPassword());
        log.info("用户名: {} 密码：{}", username, passeord);

        SysUserEntity userEntity = sysUserDao.selectByUserName(username);
        if (userEntity == null) {
            throw new UnknownAccountException("账号不存在");
        }

        if (userEntity.getStatus() == 0) {
            throw new DisabledAccountException("账户已被禁用,请联系管理员!");
        }

//        if (userEntity.getPassword().equals(passeord)) {
//            throw new IncorrectCredentialsException("账户名密码不匹配");
//        }
//        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userEntity, passeord, getName());



        //通过前端提交的明文密码和数据库里的用户的盐重新生成密码
//        String realPassword = ShiroUtil.sha256(passeord, userEntity.getSalt());
//        if (!realPassword.equals(userEntity.getPassword()) || StringUtils.isBlank(realPassword)) {
//            throw new IncorrectCredentialsException("账户名密码不匹配");
//        }
//        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userEntity, passeord, getName());

        // 验证逻辑-交给shiro的密钥匹配器去实现
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userEntity, userEntity.getPassword(), ByteSource.Util.bytes(userEntity.getSalt()), getName());

        return info;
    }

    /**
     * 密码验证器~匹配逻辑 ~ 第二种验证逻辑
     * @param credentialsMatcher
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtil.hashAlgorithmName);
        shaCredentialsMatcher.setHashIterations(ShiroUtil.hashIterations);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }
}
