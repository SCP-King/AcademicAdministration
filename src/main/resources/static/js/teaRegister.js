$(document).ready(function (){
    let flag;
    $("#getflag").click(function (){
        $.ajax({
            url: "/teacher/getFlag",
            type:"post",
            data: {"phone":$("#phone").val()},
            success:function (res) {
                flag = res;
            },
            error:function (){
                alert("出错了!")
            }

        })
    })
    $("#myForm").bind("submit",function (event){
        event.preventDefault()
        if(flag!=$("#flag").val()){
            alert("验证码错误")
            return;
        }
        let data=new FormData()
        let f=$("#myForm").serializeArray()
        for (let i in f) {
            data.append(f[i].name,f[i].value)
        }
        data.append("photo",$("#teaphoto")[0].files[0])
        $.ajax({
            url: "/teacher/register",
            method: "post",
            data: data,
            cache:false,
            contentType:false,
            processData:false,
            success: function (res) {
                alert(res)
                if (res === "注册成功") {
                    location.replace("/")
                }
            },
            error: function () {
                alert('出错了!')
            },
        })

    })
})