package top.ptcc9.controller;


import cn.hutool.crypto.SecureUtil;
import org.springframework.web.bind.annotation.*;
import top.ptcc9.commonresult.CommonResult;
import top.ptcc9.po.Customer;
import top.ptcc9.service.AccountService;
import top.ptcc9.utils.JwtUtil;
import top.ptcc9.vo.CustomerVo;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: HE LONG CAN
 * @description:
 * @date: 2020-11-24 12:38
 */
@RestController
public class AccountController {
    @Resource
    private AccountService accountService;

    @RequestMapping(value = "/doLogin",method = RequestMethod.GET)
    public CommonResult<CustomerVo> doLogin(String phone,String password) {
        Customer customerByPhone = accountService.getCustomerByPhone(phone);
        /**
         * 用户输入的密码进行加密
         */
        String md5Password = SecureUtil.md5().digestHex(password);
        if (customerByPhone != null && md5Password.equals(customerByPhone.getPassword())) {
            /**
             * po => vo
             */
            CustomerVo customerVo = accountService.customerToVo(customerByPhone);
            /**
             * 签发token
             */
            Map<String, String> map = new HashMap<>(1);
            map.put("id",customerVo.getId());
            String token = JwtUtil.create(map);
            customerVo.setToken(token);
            return new CommonResult<>(CommonResult.State.SUCCESS_LOGIN,customerVo);
        }else {
            return new CommonResult<>(CommonResult.State.ERROR_LOGIN);
        }
    }

    @RequestMapping(value = "/doSignUp",method = RequestMethod.POST)
    public CommonResult<String> doSignUp(@RequestBody Customer customer) {
        if (accountService.insertCustomer(customer) == 1) {
            return new CommonResult<>(CommonResult.State.SUCCESS_SIGN_UP);
        }else {
            return new CommonResult<>(CommonResult.State.ERROR_SIGN_UP);
        }
    }

    public CommonResult<CustomerVo> getCurrentCustomer() {
        return null;
    }
}