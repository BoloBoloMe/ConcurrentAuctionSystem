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