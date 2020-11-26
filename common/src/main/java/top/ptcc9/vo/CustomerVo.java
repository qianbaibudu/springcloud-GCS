package top.ptcc9.vo;

import lombok.Data;

/**
 * @author: HE LONG CAN
 * @description: 返回给前端展示
 * @date: 2020-11-24 13:32
 */
@Data
public class CustomerVo {
    private String id;
    private String phone;
    private Double balance;
    private String createTime;
    private boolean isExpired;
    private Long vipExpiration;
    private String token;

    public CustomerVo() {
        this.isExpired = true;
    }
}
