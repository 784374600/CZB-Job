const   random=(minNum, maxNum)=> {
            return parseInt(Math.random() * ( maxNum - minNum + 1 ) + minNum, 10)
}

const  select=(pool,num)=>{
        let result=[];
        for (let i = 0; i < num; i++) { // 前 K 个元素直接放入数组中
           result[i] = pool[i];
        }
        let N=pool.length;
        for (let i = num; i < N; i++) { // K + 1 个元素开始进行概率采样
            let r = random(0,i);
            if (r < num) {
                result[r] = pool[i];
            }
        }

        return result;

}

module.exports={
    select:select
}


