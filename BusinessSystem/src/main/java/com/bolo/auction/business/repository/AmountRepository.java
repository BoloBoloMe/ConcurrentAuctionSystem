package com.bolo.auction.business.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.bolo.auction.business.repository.entity.AccountAmount;

public interface AmountRepository {

    /**
     * 冻结金额
     * <p>
     * where 字句说明：
     * balance - ?2 >=0 —— 出价不得超过余额
     * freeze <= ?2 —— 拍卖规则是价高者得，且金额会在倒计时结束时释放，故用户每次发起应价时出价都不应该低于已经冻结过的金额
     *
     * @param id    主键
     * @param price 出价
     */
    @Update("update account_amount set amount = amount - #{price},freeze = freeze + #{price} where account_id = #{id} and amount - #{price} >=0 and freeze <= #{price}")
    int freeze(@Param("id") Long id, @Param("price") Long price);


    @Insert("insert into account_amount (amount, freeze, create_date) value (#{op.amount}, #{op.freeze}, sysdate())")
    int insert(@Param("op") AccountAmount op);

    @Delete("delete from account_amount where 1=1")
    int deleteAll();
}
