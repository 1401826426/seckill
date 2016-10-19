var seckill = {

    URL:{
        now :function(){return "/seckill/time/now"  ; } ,
        exposer:function(seckillId){return "/seckill/" + seckillId + "/exposer" ;} ,
        kill: function (seckillId , md5) {return "/seckill/" + seckillId + "/" + md5 + "/execution" ;}
    } ,

    validatePhone:function(phone){
        if(phone && phone.length == 11 && !isNaN(phone)){
            return true ;
        }else{
            return false ;
        }
    } ,


    handleSeckill:function(seckillId , node){

        node.hide()
            .html('<button class = "btn btn-primary btn-lg" id = "killBtn">开始秒杀</button>') ;
        $.get(seckill.URL.exposer(seckillId) , {} , function(result){
            if(result && result['success']){
                var exposer = result['data'] ;
                console.log(exposer) ;  //todo
                if(exposer['exposed']){
                    var md5 = exposer['md5'] ;
                    var killUrl = seckill.URL.kill(seckillId , md5) ;
                    console.log("killUrl:" + killUrl) ;  //todo
                    $("#killBtn").one('click' , function(){
                        $(this).addClass("disabled") ;
                        $.post(killUrl , {} , function (result) {
                            if(result && result.success){
                                var killResult = result['data'] ;
                                var state = killResult['state'] ;
                                var stateInfo = killResult['stateInfo'] ;
                                node.html('<span class = "label label-success">' + stateInfo + '</span>') ;
                            }
                        })
                    })
                    node.show() ;
                }else{
                    var now = exposer['now'] ;
                    var start = exposer['start'] ;
                    var end = exposer['end'] ;
                    seckill.countdown(seckillId , now , start , end) ;
                }
            }else{
                console.log("[exposer]result" + result) ;
            }
        })

    } ,


    countdown:function(seckillId , nowTime , startTime , endTime){
        var seckillBox = $("#seckill-box") ;
        if(nowTime > endTime){
            seckillBox.html("秒杀结束") ;
        }else if(nowTime < startTime){
            var killTime = new Date(startTime+1000) ;
            seckillBox.countdown(killTime , function (event) {
                var format = event.strftime("秒杀倒计时:  %D 天  %H 时  %M 分  %s 秒 ") ;
                seckillBox.html(format) ;
            }).on("finish.countdown" , function () {
                seckill.handleSeckill(seckillId , seckillBox) ;
            }) ;
        }else{
            seckill.handleSeckill(seckillId , seckillBox) ;
        }
    } ,



    detail:{
        init: function (params) {
            var killPhone = $.cookie('killPhone') ;
            var statTime = params['statTime'] ;
            var endTime = params["endTime"] ;
            var seckillId  = params["seckillId"] ;
            if(!seckill.validatePhone(killPhone)){
                var killPhoneModal = $("#killPhoneModal") ;
                killPhoneModal.modal({
                    show:true ,
                    backdrop:'static' ,
                    keyboard: false
                }) ;
                $("#killPhoneBtn").click(function(){
                    var inputPhone = $("#killPhoneKey").val() ;
                    console.log("inputPhone = " + inputPhone) ;  //Todo
                    if(seckill.validatePhone(inputPhone)){
                        $.cookie("killPhone" , inputPhone , {expires:7 , path:'/seckill'})
                        window.location.reload() ;
                    }else{
                        $("#killPhoneMessage").hide().html("<label class = 'label label-danger'>手机号错误</label>").show(300) ;
                    }
                })
            }


            $.get(seckill.URL.now() , {} , function(result){
                if(result && result['success']){
                    var timeNow = result['date'] ;
                    seckill.countdown(seckillId , timeNow , statTime , endTime) ;
                }else{
                    console.log("result:" + result) ;
                }
            }) ;

        }

    }


}