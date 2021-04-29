-- 设redis上标的信息的存储方式如下：
-- 类型：hash，key = 场次号
-- {
--     场次号:{
--         标的号:'',
--         最新价:'',
--         应价者:'',
--         倒计时:''
--     }
-- }
-- 脚本参数：1-场次号，2-倒计时字段名； 3-最新价字段名；4-出价
-- 脚本出参: 0-失败，1-成功
local lastPrice = redis.call('HGET',ARGV[1] ,ARGV[2])
if lastPrice == 0 then
    return 'failed'
else
    if lastPrice >  ARGV[3] or lastPrice ==  ARGV[3]  then
        return 'failed'
    else
       redis.call('HSET',ARGV[1],ARGV[3],ARGV[4])
       return 'succeed'
    end
end