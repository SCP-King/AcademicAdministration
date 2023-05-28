$(document).ready(function (){
    let flag;
    $("#getflag").click(function (){
        $.ajax({
            url: "/student/getFlag",
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
            $("#flag").css("value","")
            return;
        }
       let data=new FormData()
        let f=$("#myForm").serializeArray()
        for (let i in f) {
            data.append(f[i].name,f[i].value)
        }
        data.append("photo",$("#stuphoto")[0].files[0])
        $.ajax({
            url: "/student/register",
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
    $("#stucollege").change(function () {
        $("stumajor").append()
        let college = $("#stucollege").val()
        if (college === "信息科学与技术学院") {
            $("#stumajor").empty()
            $("#stumajor").append($("<option>").val("无").text("专业"))
            $("#stumajor").append($("<option>").val("计算机科学与技术").text("计算机科学与技术"))
            $("#stumajor").append($("<option>").val("软件工程").text("软件工程"))
            $("#stumajor").append($("<option>").val("数字媒体技术").text("数字媒体技术"))
            $("#stumajor").append($("<option>").val("信息工程").text("信息工程"))
            $("#stumajor").append($("<option>").val("人工智能").text("人工智能"))
            $("#stumajor").append($("<option>").val("网络工程").text("网络工程"))
        } else if (college === "土木工程学院"){
            $("#stumajor").empty()
            $("#stumajor").append($("<option>").val("无").text("专业"))
            $("#stumajor").append($("<option>").val("土木工程").text("土木工程"))
            $("#stumajor").append($("<option>").val("测绘工程").text("测绘工程"))
            $("#stumajor").append($("<option>").val("勘察技术与工程").text("勘察技术与工程"))
            $("#stumajor").append($("<option>").val("城市地下空间工程").text("城市地下空间工程"))
            $("#stumajor").append($("<option>").val("铁道工程").text("铁道工程"))
             $("#stumajor").append($("<option>").val("智能建造").text("智能建造"))

        }
        else if (college === "机械工程学院"){
            $("#stumajor").empty()
            $("#stumajor").append($("<option>").val("无").text("专业"))
            $("#stumajor").append($("<option>").val("机械设计制造及其自动化").text("机械设计制造及其自动化"))
            $("#stumajor").append($("<option>").val("机械电子工程").text("机械电子工程"))
            $("#stumajor").append($("<option>").val("建筑环境与能源应用工程").text("建筑环境与能源应用工程"))
            $("#stumajor").append($("<option>").val("测控技术与仪器").text("测控技术与仪器"))
            $("#stumajor").append($("<option>").val("车辆工程").text("车辆工程"))
            $("#stumajor").append($("<option>").val("工业设计专业").text("工业设计专业"))
        }
        else {
            $("#stumajor").empty()
            $("#stumajor").append($("<option>").val("无").text("专业"))
        }
    })
})